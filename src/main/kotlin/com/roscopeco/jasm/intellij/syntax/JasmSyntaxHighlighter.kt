package com.roscopeco.jasm.intellij.syntax

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import com.roscopeco.jasm.antlr.JasmLexer
import com.roscopeco.jasm.antlr.JasmParser
import com.roscopeco.jasm.intellij.JasmLanguage
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.lexer.TokenIElementType


class JasmSyntaxHighlighter : SyntaxHighlighterBase() {
    companion object {
        val ID: TextAttributesKey
            = createTextAttributesKey("JASM_ID", DefaultLanguageHighlighterColors.IDENTIFIER)
        val KEYWORD: TextAttributesKey
            = createTextAttributesKey("JASM_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
        val STRING: TextAttributesKey
            = createTextAttributesKey("JASM_STRING", DefaultLanguageHighlighterColors.STRING)
        val LINE_COMMENT: TextAttributesKey
                = createTextAttributesKey("JASM_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BLOCK_COMMENT: TextAttributesKey
                = createTextAttributesKey("JASM_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT)
        val COMMA: TextAttributesKey
            = createTextAttributesKey("JASM_COMMA", DefaultLanguageHighlighterColors.COMMA)
        val PARENS: TextAttributesKey
                = createTextAttributesKey("JASM_PARENS", DefaultLanguageHighlighterColors.PARENTHESES)
        val BRACKETS: TextAttributesKey
                = createTextAttributesKey("JASM_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS)
        val BRACES: TextAttributesKey
            = createTextAttributesKey("JASM_BRACES", DefaultLanguageHighlighterColors.BRACES)
        val DOT: TextAttributesKey
            = createTextAttributesKey("JASM_DOT", DefaultLanguageHighlighterColors.DOT)
        val SIGN: TextAttributesKey
            = createTextAttributesKey("JASM_OPERATOR_SIGN", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val NUMBER: TextAttributesKey
            = createTextAttributesKey("JASM_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
        val OPCODE: TextAttributesKey
            = createTextAttributesKey("JASM_OPCODE", DefaultLanguageHighlighterColors.FUNCTION_CALL)

        init {
            PSIElementTypeFactory.defineLanguageIElementTypes(
                JasmLanguage.INSTANCE,
                JasmParser.tokenNames,
                JasmParser.ruleNames
            )
        }
    }

    private val emptyKeys = emptyArray<TextAttributesKey>()
    private val badToken: TextAttributesKey
            = createTextAttributesKey("JASM_BAD_TOKEN", HighlighterColors.BAD_CHARACTER)

    override fun getHighlightingLexer() = ANTLRLexerAdaptor(JasmLanguage.INSTANCE, JasmLexer(null))

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        if (tokenType !is TokenIElementType) return emptyKeys
        val attrKey: TextAttributesKey = when (tokenType.antlrTokenType) {
            JasmLexer.LSQUARE, JasmLexer.RSQUARE -> BRACKETS
            JasmLexer.LPAREN, JasmLexer.RPAREN -> PARENS
            JasmLexer.LBRACE, JasmLexer.RBRACE -> BRACES
            JasmLexer.DOT -> DOT
            JasmLexer.MINUS -> SIGN
            JasmLexer.SEMI -> badToken
            JasmLexer.COMMA -> COMMA
            in JasmLexer.CLASS..JasmLexer.VOLATILE -> KEYWORD
            in JasmLexer.AALOAD..JasmLexer.CONSTDYNAMIC -> OPCODE
            in JasmLexer.TYPE_VOID..JasmLexer.TYPE_BOOL -> KEYWORD
            JasmLexer.TRUE, JasmLexer.FALSE -> KEYWORD
            JasmLexer.INIT, JasmLexer.CLINIT -> KEYWORD
            JasmLexer.LABEL, JasmLexer.NAME, JasmLexer.QNAME -> ID
            JasmLexer.INT, JasmLexer.FLOAT -> NUMBER
            JasmLexer.STRING -> STRING
            JasmLexer.COMMENT -> LINE_COMMENT
            JasmLexer.BLOCK_COMMENT -> BLOCK_COMMENT
            JasmLexer.OTHER -> badToken
            else -> return emptyKeys
        }
        return arrayOf(attrKey)
    }
}