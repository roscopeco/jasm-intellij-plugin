package com.roscopeco.jasm.intellij.refs

import com.intellij.psi.PsiElement
import com.roscopeco.jasm.intellij.psi.FieldSubtree
import com.roscopeco.jasm.intellij.psi.NamePsiNode

class FieldDefRef(element: NamePsiNode) : JasmElementRef(element) {
    override fun isDefSubtree(def: PsiElement): Boolean = def is FieldSubtree
}