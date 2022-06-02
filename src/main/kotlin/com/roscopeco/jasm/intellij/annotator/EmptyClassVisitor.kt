package com.roscopeco.jasm.intellij.annotator

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.ModuleVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.TypePath

internal class EmptyClassVisitor : ClassVisitor(Opcodes.ASM7) {
    override fun visitField(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        value: Any?
    ) = object : FieldVisitor(Opcodes.ASM7) {
        /* empty */
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ) = object : MethodVisitor(Opcodes.ASM7) {
        /* empty */
    }

    override fun visitModule(name: String?, access: Int, version: String?) = object : ModuleVisitor(Opcodes.ASM7) {
        /* empty */
    }

    override fun visitAnnotation(descriptor: String?, visible: Boolean) = object : AnnotationVisitor(Opcodes.ASM7) {
        /* empty */
    }

    override fun visitTypeAnnotation(
        typeRef: Int,
        typePath: TypePath?,
        descriptor: String?,
        visible: Boolean
    ) = object : AnnotationVisitor(Opcodes.ASM7) {
        /* empty */
    }
}