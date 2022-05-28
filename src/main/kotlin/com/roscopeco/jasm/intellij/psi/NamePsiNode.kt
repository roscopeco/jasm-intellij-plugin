package com.roscopeco.jasm.intellij.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.PsiReference
import com.roscopeco.jasm.antlr.JasmParser
import com.roscopeco.jasm.intellij.JasmLanguage
import com.roscopeco.jasm.intellij.refs.FieldDefRef
import com.roscopeco.jasm.intellij.refs.MethodDefRef
import com.roscopeco.jasm.intellij.refs.TypeRef
import org.antlr.intellij.adaptor.lexer.RuleIElementType
import org.antlr.intellij.adaptor.lexer.TokenIElementType
import org.antlr.intellij.adaptor.psi.ANTLRPsiLeafNode
import org.antlr.intellij.adaptor.psi.Trees

class NamePsiNode(type: TokenIElementType, text: CharSequence) : ANTLRPsiLeafNode(type, text), PsiNamedElement {
    override fun getName() = text

    override fun setName(name: String): PsiElement {
        val new = Trees.createLeafFromText(project, JasmLanguage.INSTANCE, context, name, elementType) ?: return this
        return replace(new)
    }

    override fun getReference(): PsiReference? {
        val elementType = parent.node.elementType

        return if (elementType is RuleIElementType) {
            when (elementType.ruleIndex) {
                JasmParser.RULE_membername -> when ((parent.parent.node.elementType as RuleIElementType).ruleIndex) {
                    JasmParser.RULE_field -> FieldDefRef(this)
                    JasmParser.RULE_method -> MethodDefRef(this)
                    JasmParser.RULE_insn_getfield,
                    JasmParser.RULE_insn_getstatic,
                    JasmParser.RULE_insn_putfield,
                    JasmParser.RULE_insn_putstatic -> FieldDefRef(this)
                    else -> null
                }

                JasmParser.RULE_ref_type,
                JasmParser.RULE_owner -> TypeRef(this)

                else -> null
            }
        } else {
            null
        }
    }
}