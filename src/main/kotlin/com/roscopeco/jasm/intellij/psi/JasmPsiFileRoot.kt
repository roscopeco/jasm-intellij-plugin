package com.roscopeco.jasm.intellij.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.roscopeco.jasm.intellij.JasmLanguage
import com.roscopeco.jasm.intellij.filetypes.JasmFileType
import org.antlr.intellij.adaptor.SymtabUtils
import org.antlr.intellij.adaptor.psi.ScopeNode
import javax.swing.Icon

class JasmPsiFileRoot(theViewProvider: FileViewProvider) : PsiFileBase(theViewProvider, JasmLanguage.INSTANCE), ScopeNode {
    override fun getContext(): ScopeNode? = null

    override fun resolve(element: PsiNamedElement?): PsiElement?
        = SymtabUtils.resolve(this, JasmLanguage.INSTANCE, element, "class/classbody/member/*/membername")

    override fun getFileType(): FileType = JasmFileType.INSTANCE

    override fun getIcon(flags: Int): Icon = JasmFileType.ICON

    override fun toString(): String {
        return "JASM Source file"
    }
}