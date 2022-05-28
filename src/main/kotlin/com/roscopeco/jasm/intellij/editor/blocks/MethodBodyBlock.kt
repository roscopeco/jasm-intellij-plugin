package com.roscopeco.jasm.intellij.editor.blocks

import com.intellij.formatting.SpacingBuilder
import com.intellij.formatting.Wrap
import com.intellij.formatting.WrapType
import com.intellij.lang.ASTNode

class MethodBlock(node: ASTNode, spacingBuilder: SpacingBuilder) : JasmBlock(node, Wrap.createWrap(WrapType.NORMAL, false), spacingBuilder)