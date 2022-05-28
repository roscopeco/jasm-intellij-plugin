package com.roscopeco.jasm.intellij.parser

import com.intellij.lang.DefaultASTFactoryImpl
import com.intellij.psi.impl.source.tree.LeafElement
import com.intellij.psi.tree.IElementType
import com.roscopeco.jasm.antlr.JasmLexer
import com.roscopeco.jasm.intellij.psi.NamePsiNode
import org.antlr.intellij.adaptor.lexer.TokenIElementType

class JasmASTFactory : DefaultASTFactoryImpl() {
    override fun createLeaf(type: IElementType, text: CharSequence): LeafElement {
        return if (type is TokenIElementType && (type.antlrTokenType in arrayOf(JasmLexer.NAME, JasmLexer.QNAME))) {
            NamePsiNode(type, text)
        } else {
            super.createLeaf(type, text)
        }
    }
}