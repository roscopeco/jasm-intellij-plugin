package com.roscopeco.jasm.intellij.parser

import com.intellij.lang.DefaultASTFactoryImpl
import com.intellij.psi.impl.source.tree.LeafElement
import com.intellij.psi.tree.IElementType

class JasmASTFactory : DefaultASTFactoryImpl() {
    // TODO override this to e.g. handle identifiers once appropriate PSI node(s) exist
    override fun createLeaf(type: IElementType, text: CharSequence): LeafElement {
        return super.createLeaf(type, text)
    }
}