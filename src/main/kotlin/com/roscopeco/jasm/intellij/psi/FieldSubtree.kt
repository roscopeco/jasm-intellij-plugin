package com.roscopeco.jasm.intellij.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.roscopeco.jasm.intellij.JasmLanguage
import org.antlr.intellij.adaptor.SymtabUtils
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode
import org.antlr.intellij.adaptor.psi.ScopeNode

class FieldSubtree(node: ASTNode) : ANTLRPsiNode(node), ScopeNode {
    override fun resolve(element: PsiNamedElement?): PsiElement?
        = SymtabUtils.resolve(this, JasmLanguage.INSTANCE, element,"/membername")
}