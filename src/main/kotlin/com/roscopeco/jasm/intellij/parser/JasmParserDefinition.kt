package com.roscopeco.jasm.intellij.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.roscopeco.jasm.antlr.JasmLexer
import com.roscopeco.jasm.antlr.JasmParser
import com.roscopeco.jasm.intellij.JasmLanguage
import com.roscopeco.jasm.intellij.psi.JasmPSIFileRoot
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.lexer.RuleIElementType
import org.antlr.intellij.adaptor.lexer.TokenIElementType
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode
import org.antlr.v4.runtime.Parser


class JasmParserDefinition : ParserDefinition {
    companion object {
        val TOKEN_ELEMENT_TYPES: List<TokenIElementType>
        val RULE_ELEMENT_TYPES: List<RuleIElementType>

        // N.B. This must be done before the createTokenSet stuff below!
        init {
            PSIElementTypeFactory.defineLanguageIElementTypes(
                JasmLanguage.INSTANCE,
                JasmParser.tokenNames,
                JasmParser.ruleNames
            )

            TOKEN_ELEMENT_TYPES = PSIElementTypeFactory.getTokenIElementTypes(JasmLanguage.INSTANCE)
            RULE_ELEMENT_TYPES = PSIElementTypeFactory.getRuleIElementTypes(JasmLanguage.INSTANCE)
        }

        val FILE = IFileElementType(JasmLanguage.INSTANCE)

        val COMMENTS = PSIElementTypeFactory.createTokenSet(
            JasmLanguage.INSTANCE,
            JasmLexer.COMMENT,
            JasmLexer.BLOCK_COMMENT
        )

        val WHITESPACE = PSIElementTypeFactory.createTokenSet(
            JasmLanguage.INSTANCE,
            JasmLexer.SPACE
        )

        val STRINGS = PSIElementTypeFactory.createTokenSet(
            JasmLanguage.INSTANCE,
            JasmLexer.STRING
        )
    }

    override fun createLexer(project: Project) = ANTLRLexerAdaptor(JasmLanguage.INSTANCE, JasmLexer(null))

    override fun createParser(project: Project) = object : ANTLRParserAdaptor(JasmLanguage.INSTANCE, JasmParser(null)) {
        override fun parse(parser: Parser?, root: IElementType?) = (parser as JasmParser).class_()
    }

    override fun getFileNodeType(): IFileElementType = FILE

    override fun createElement(node: ASTNode): PsiElement = ANTLRPsiNode(node)

    override fun createFile(viewProvider: FileViewProvider): PsiFile = JasmPSIFileRoot(viewProvider)

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements
        = ParserDefinition.SpaceRequirements.MAY

    override fun getCommentTokens(): TokenSet = COMMENTS

    override fun getStringLiteralElements(): TokenSet = STRINGS

    override fun getWhitespaceTokens(): TokenSet = WHITESPACE
}