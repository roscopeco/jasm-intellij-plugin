package com.roscopeco.jasm.intellij.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.roscopeco.jasm.antlr.JasmParser
import com.roscopeco.jasm.intellij.JasmLanguage
import com.roscopeco.jasm.intellij.parser.JasmParserDefinition.Companion.RULE_ELEMENT_TYPES
import org.antlr.intellij.adaptor.SymtabUtils
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode
import org.antlr.intellij.adaptor.psi.IdentifierDefSubtree
import org.antlr.intellij.adaptor.psi.ScopeNode

class FieldSubtree(node: ASTNode) : IdentifierDefSubtree(node, RULE_ELEMENT_TYPES[JasmParser.RULE_membername]), ScopeNode {
    override fun resolve(element: PsiNamedElement?): PsiElement? {
        val resolved = SymtabUtils.resolve(this, JasmLanguage.INSTANCE, element, "field/membername")
        return resolved
    }
}