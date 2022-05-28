package com.roscopeco.jasm.intellij.editor

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler
import com.roscopeco.jasm.antlr.JasmLexer
import com.roscopeco.jasm.intellij.parser.JasmParserDefinition.Companion.TOKEN_ELEMENT_TYPES

class JasmQuoteHandler : SimpleTokenSetQuoteHandler(
    TOKEN_ELEMENT_TYPES[JasmLexer.DQUOTE]
)