package com.roscopeco.jasm.intellij.refs

import com.intellij.psi.PsiElement
import com.roscopeco.jasm.intellij.psi.MethodSubtree
import com.roscopeco.jasm.intellij.psi.NamePsiNode

class MethodDefRef(element: NamePsiNode) : JasmElementRef(element) {
    override fun isDefSubtree(def: PsiElement): Boolean = def is MethodSubtree
}