package com.roscopeco.jasm.intellij.editor

import com.intellij.formatting.FormattingContext
import com.intellij.formatting.FormattingModel
import com.intellij.formatting.FormattingModelBuilder
import com.intellij.formatting.FormattingModelProvider
import com.intellij.formatting.SpacingBuilder
import com.intellij.formatting.Wrap
import com.intellij.formatting.WrapType
import com.roscopeco.jasm.intellij.JasmLanguage
import com.roscopeco.jasm.intellij.editor.blocks.JasmBlock

class JasmFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val codeStyleSettings = formattingContext.codeStyleSettings
        return FormattingModelProvider.createFormattingModelForPsiFile(
            formattingContext.containingFile,
            JasmBlock(
                formattingContext.node,
                Wrap.createWrap(WrapType.NONE, false),
                SpacingBuilder(codeStyleSettings, JasmLanguage.INSTANCE)
            ),
            codeStyleSettings
        )
    }
}