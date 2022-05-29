package com.roscopeco.jasm.intellij.editor.blocks

import com.intellij.formatting.Block
import com.intellij.formatting.ChildAttributes
import com.intellij.formatting.Indent
import com.intellij.formatting.SpacingBuilder
import com.intellij.formatting.Wrap
import com.intellij.formatting.WrapType
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock
import com.roscopeco.jasm.antlr.JasmParser
import com.roscopeco.jasm.intellij.parser.JasmParserDefinition.Companion.RULE_ELEMENT_TYPES
import org.antlr.intellij.adaptor.lexer.RuleIElementType

open class JasmBlock(node: ASTNode, wrap: Wrap?, private val spacingBuilder: SpacingBuilder) : AbstractBlock(node, wrap, null) {

    override fun getIndent(): Indent? {
        val elementType = myNode.elementType

        return if (elementType is RuleIElementType) {
            when (elementType.ruleIndex) {
                JasmParser.RULE_classbody,
                JasmParser.RULE_method_arguments,
                JasmParser.RULE_stat,
                JasmParser.RULE_invokedynamic_body,
                JasmParser.RULE_const_args -> Indent.getNormalIndent()

                JasmParser.RULE_label -> Indent.getLabelIndent()

                else -> Indent.getNoneIndent()
            }
        } else {
            Indent.getNoneIndent()
        }
    }

    override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
        val elementType = myNode.elementType

        val indent = if (elementType is RuleIElementType) {
            when (elementType.ruleIndex) {
                JasmParser.RULE_class,
                JasmParser.RULE_method,
                JasmParser.RULE_stat_block,
                JasmParser.RULE_method_arguments,
                JasmParser.RULE_insn_invokedynamic,
                JasmParser.RULE_const_args -> Indent.getNormalIndent()
                else -> Indent.getNoneIndent()
            }
        } else {
            Indent.getNoneIndent()
        }

        return ChildAttributes(indent, null)
    }

    override fun getSpacing(child1: Block?, child2: Block)
        = spacingBuilder.getSpacing(this, child1, child2)

    override fun isLeaf() = myNode.firstChildNode == null

    override fun buildChildren(): MutableList<Block> {
        val blocks: MutableList<Block> = ArrayList()
        var child = myNode.firstChildNode
        while (child != null) {
            val block = when (child.elementType) {
                TokenType.WHITE_SPACE -> null
                ruleType(JasmParser.RULE_stat_block) -> MethodBlock(child, spacingBuilder)
                else -> JasmBlock(
                    child,
                    Wrap.createWrap(WrapType.NONE, false),
                    spacingBuilder
                )
            }

            if (block != null) {
                blocks.add(block)
            }

            child = child.treeNext
        }
        return blocks
    }

    private fun ruleType(parserType: Int) = RULE_ELEMENT_TYPES[parserType]
}