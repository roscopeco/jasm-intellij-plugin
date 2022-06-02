package com.roscopeco.jasm.intellij.filetypes

import com.intellij.icons.AllIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import com.roscopeco.jasm.intellij.JasmIcons
import com.roscopeco.jasm.intellij.JasmLanguage
import javax.swing.Icon

class JasmFileType : LanguageFileType(JasmLanguage.INSTANCE) {
    companion object {
        val INSTANCE = JasmFileType()
    }

    override fun getName() = "JASM File"

    override fun getDescription() = "JASM Source code file"

    override fun getDefaultExtension() = "jasm"

    override fun getIcon(): Icon = JasmIcons.classIcon
}