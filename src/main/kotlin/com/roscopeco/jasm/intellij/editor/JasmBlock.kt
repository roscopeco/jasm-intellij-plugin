package com.roscopeco.jasm.intellij.editor

import com.intellij.formatting.Alignment
import com.intellij.formatting.Block
import com.intellij.formatting.Indent
import com.intellij.formatting.SpacingBuilder
import com.intellij.formatting.Wrap
import com.intellij.formatting.WrapType
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock
import com.roscopeco.jasm.antlr.JasmParser
import org.antlr.intellij.adaptor.lexer.RuleIElementType

class JasmBlock(node: ASTNode, wrap: Wrap?, alignment: Alignment?, private val spacingBuilder: SpacingBuilder) : AbstractBlock(node, wrap, null) {

    override fun getIndent(): Indent? {
        val elementType = myNode.elementType

        return if (elementType is RuleIElementType) {
            when (elementType.ruleIndex) {
                JasmParser.RULE_class -> Indent.getNormalIndent()
                JasmParser.RULE_stat -> Indent.getNormalIndent()
                JasmParser.RULE_label -> Indent.getLabelIndent()
                else -> Indent.getNoneIndent()
            }
        } else {
            Indent.getNoneIndent()
        }
    }

    override fun getSpacing(child1: Block?, child2: Block)
        = spacingBuilder.getSpacing(this, child1, child2)

    override fun isLeaf() = myNode.firstChildNode == null

    override fun buildChildren(): MutableList<Block> {
        val blocks: MutableList<Block> = ArrayList()
        var child = myNode.firstChildNode
        while (child != null) {
            if (child.elementType != TokenType.WHITE_SPACE) {
                val block: Block = JasmBlock(
                    child,
                    Wrap.createWrap(WrapType.NONE, false),
                    Alignment.createAlignment(),
                    spacingBuilder
                )
                blocks.add(block)
            }
            child = child.treeNext
        }
        return blocks
    }
}