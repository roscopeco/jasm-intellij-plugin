package com.roscopeco.jasm.intellij

import com.intellij.lang.Language

class JasmLanguage : Language("Jasm" ) {
    companion object {
        @JvmStatic
        val INSTANCE = JasmLanguage()
    }
}