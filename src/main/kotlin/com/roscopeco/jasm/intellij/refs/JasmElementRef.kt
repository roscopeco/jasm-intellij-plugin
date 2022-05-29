package com.roscopeco.jasm.intellij.refs

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.refactoring.suggested.endOffset
import com.roscopeco.jasm.intellij.psi.NamePsiNode
import org.antlr.intellij.adaptor.psi.ScopeNode

abstract class JasmElementRef(element: NamePsiNode) : PsiReferenceBase<NamePsiNode>(element, TextRange(element.startOffset, element.endOffset)) {
    override fun getVariants(): Array<Any?> {
        return arrayOfNulls(0)
    }

    override fun handleElementRename(newElementName: String): PsiElement {
        return myElement.setName(newElementName)
    }

    override fun resolve(): PsiElement? {
        val context = myElement.context
        return if (context is ScopeNode) {
            context.resolve(myElement)
        } else {
            null
        }
    }

    override fun isReferenceTo(def: PsiElement): Boolean {
        return resolve()?.equals(def) ?: false
    }

    abstract fun isDefSubtree(def: PsiElement): Boolean
}