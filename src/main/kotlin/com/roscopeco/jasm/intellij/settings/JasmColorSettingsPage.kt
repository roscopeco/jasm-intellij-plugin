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

    override fun getDemoText() = """
        /*
         * Sample block comment for my awesome example class
         */
        public class com/example/JasmClass extends OtherClass implements Interface {
            public static final CONST_STR java/lang/String = "Some String"
            public static final CONST_INT I = 42
            public static final CONST_FLOAT F = -39.5
         
            // The main method
            public static main([java/lang/String)V {
                getstatic java/lang/System.out
                ldc "Hello, World"
                invokevirtual java/io/PrintStream.println(java/lang/String)V
                return
            }
                               
            public final addInts(int, int) int {
                iload 1
                iload 2
                iadd
                ireturn
            }
            
            /* Another block comment */
            public useInvokeDynamic()java/lang/String {
                invokedynamic get()java/util/function/Supplier {
                    invokestatic java/lang/invoke/LambdaMetafactory.metafactory(
                        java/lang/invoke/MethodHandles${"$"}Lookup,
                        java/lang/String,
                        java/lang/invoke/MethodType,
                        java/lang/invoke/MethodType,
                        java/lang/invoke/MethodHandle,
                        java/lang/invoke/MethodType,
                    )java/lang/invoke/CallSite
                    [
                        ()java/lang/Object,
                        invokestatic com/example/Bootstraps.lambdaGetImpl()java/lang/String,
                        ()java/lang/String
                    ]
                }
        
                invokeinterface java/util/function/Supplier.get()java/lang/Object
                checkcast java/lang/String
                areturn
            }
        }        
    """.trimIndent()

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? = null
}