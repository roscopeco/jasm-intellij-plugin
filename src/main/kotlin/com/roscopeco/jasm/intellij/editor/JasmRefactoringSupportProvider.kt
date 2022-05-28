package com.roscopeco.jasm.intellij.editor

import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement
import com.roscopeco.jasm.intellij.psi.FieldSubtree

class JasmRefactoringSupportProvider : RefactoringSupportProvider() {
    override fun isMemberInplaceRenameAvailable(element: PsiElement, context: PsiElement?): Boolean {
        return element is FieldSubtree
    }
}