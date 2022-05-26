package com.roscopeco.jasm.intellij.editor

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.roscopeco.jasm.antlr.JasmLexer
import com.roscopeco.jasm.intellij.parser.JasmParserDefinition.Companion.TOKEN_ELEMENT_TYPES

class JasmBraceMatcher : PairedBraceMatcher {
    override fun getPairs(): Array<out BracePair> = arrayOf(
        BracePair(TOKEN_ELEMENT_TYPES[JasmLexer.LPAREN], TOKEN_ELEMENT_TYPES[JasmLexer.RPAREN], false),
        BracePair(TOKEN_ELEMENT_TYPES[JasmLexer.LSQUARE], TOKEN_ELEMENT_TYPES[JasmLexer.RSQUARE], false),
        BracePair(TOKEN_ELEMENT_TYPES[JasmLexer.LBRACE], TOKEN_ELEMENT_TYPES[JasmLexer.RBRACE], true),
    )

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?) = true

    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int) = openingBraceOffset
}