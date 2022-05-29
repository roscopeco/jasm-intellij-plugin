package com.roscopeco.jasm.intellij.filetypes

import com.intellij.icons.AllIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import com.roscopeco.jasm.intellij.JasmLanguage
import javax.swing.Icon

class JasmFileType : LanguageFileType(JasmLanguage.INSTANCE) {
    companion object {
        @JvmStatic
        val INSTANCE = JasmFileType()
        val ICON = AllIcons.Ide.Pipette
    }

    override fun getName() = "JASM File"

    override fun getDescription() = "JASM Source code file"

    override fun getDefaultExtension() = "jasm"

    override fun getIcon(): Icon = ICON
}