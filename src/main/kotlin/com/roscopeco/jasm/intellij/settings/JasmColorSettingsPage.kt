package com.roscopeco.jasm.intellij.settings

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.roscopeco.jasm.intellij.filetypes.JasmFileType
import com.roscopeco.jasm.intellij.syntax.JasmSyntaxHighlighter

class JasmColorSettingsPage : ColorSettingsPage {
    companion object {
        val DESCRIPTORS = arrayOf(
            AttributesDescriptor("Identifier", JasmSyntaxHighlighter.ID),
            AttributesDescriptor("Keyword", JasmSyntaxHighlighter.KEYWORD),
            AttributesDescriptor("Opcode", JasmSyntaxHighlighter.OPCODE),
            AttributesDescriptor("Number", JasmSyntaxHighlighter.NUMBER),
            AttributesDescriptor("String", JasmSyntaxHighlighter.STRING),
            AttributesDescriptor("Comment", JasmSyntaxHighlighter.LINE_COMMENT),
            AttributesDescriptor("Block comment", JasmSyntaxHighlighter.BLOCK_COMMENT),
            AttributesDescriptor("Comma", JasmSyntaxHighlighter.COMMA),
            AttributesDescriptor("Parenthesis", JasmSyntaxHighlighter.PARENS),
            AttributesDescriptor("Brackets", JasmSyntaxHighlighter.BRACKETS),
            AttributesDescriptor("Braces", JasmSyntaxHighlighter.BRACES),
            AttributesDescriptor("Unary sign", JasmSyntaxHighlighter.SIGN),
            AttributesDescriptor("Dot", JasmSyntaxHighlighter.DOT),
        )
    }

    override fun getAttributeDescriptors() = DESCRIPTORS

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName() = "JASM"

    override fun getIcon() = JasmFileType.ICON

    override fun getHighlighter() = JasmSyntaxHighlighter()

    override fun getDemoText() = Shared.SAMPLE_CODE

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? = null
}