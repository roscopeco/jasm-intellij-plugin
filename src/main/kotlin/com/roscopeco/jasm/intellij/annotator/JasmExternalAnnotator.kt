package com.roscopeco.jasm.intellij.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile
import com.roscopeco.jasm.JasmAssemblingVisitor
import com.roscopeco.jasm.antlr.JasmLexer
import com.roscopeco.jasm.antlr.JasmParser
import com.roscopeco.jasm.errors.BaseError
import com.roscopeco.jasm.errors.CodeError
import com.roscopeco.jasm.errors.CollectingErrorListener
import com.roscopeco.jasm.errors.ErrorCollector
import com.roscopeco.jasm.errors.StandardErrorCollector
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import java.util.Objects

class JasmExternalAnnotator : ExternalAnnotator<JasmExternalAnnotator.ParsedUnit, List<BaseError>>() {
    companion object {
        private val EMPTY_ERROR_COLLECTOR = object : ErrorCollector {
            override fun addError(error: BaseError) {
            }
            override fun getErrors(): List<BaseError> = emptyList()
            override fun hasErrors(): Boolean  = false
        }

        private val EMPTY_CLASS_VISITOR = EmptyClassVisitor()

    }
    override fun collectInformation(file: PsiFile): ParsedUnit {
        val parser = buildParser(
            CommonTokenStream(
                buildLexer(
                    Objects.requireNonNull(
                        CharStreams.fromStream(file.text.byteInputStream()),
                        "Failed to open stream for ${file.name}"),
                )
            )
        )

        return ParsedUnit(file.name, parser.class_())
    }

    override fun doAnnotate(collectedInfo: ParsedUnit): List<BaseError> {
        val collector = StandardErrorCollector()
        val assembler = JasmAssemblingVisitor(EMPTY_CLASS_VISITOR, collectedInfo.unitName, collector)

        return try {
            assembler.visit(collectedInfo.tree)
            collector.getErrors()
        } catch (e: Exception) {
            // prevent this leaking out to the IDE
            emptyList()
        }
    }

    override fun apply(file: PsiFile, annotationResult: List<BaseError>, holder: AnnotationHolder) {
        annotationResult.forEach {
            if (it is CodeError) {
                holder.newAnnotation(HighlightSeverity.ERROR, it.message)
                    .range(TextRange.create(it.start.startIndex, it.stop.stopIndex + 1))
                    .create()
            }
        }
    }

    private fun buildLexer(input: CharStream): JasmLexer {
        val lexer = JasmLexer(input)
        lexer.removeErrorListeners()
        lexer.addErrorListener(CollectingErrorListener("ignored", EMPTY_ERROR_COLLECTOR))
        return lexer
    }

    private fun buildParser(tokens: TokenStream): JasmParser {
        val parser = JasmParser(tokens)
        parser.removeErrorListeners()
        parser.addErrorListener(CollectingErrorListener("ignored", EMPTY_ERROR_COLLECTOR))
        return parser
    }

    data class ParsedUnit(val unitName: String, val tree: JasmParser.ClassContext)
}