package com.roscopeco.jasm.intellij.settings.codestyle

import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.psi.codeStyle.CodeStyleConfigurable
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider
import com.intellij.psi.codeStyle.CustomCodeStyleSettings
import com.roscopeco.jasm.intellij.JasmBundle
import com.roscopeco.jasm.intellij.JasmLanguage

class JasmCodeStyleSettingsProvider : CodeStyleSettingsProvider() {
    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings
        = JasmCodeStyleSettings(settings)

    override fun getConfigurableDisplayName(): String = JasmBundle.message("langName")

    override fun createConfigurable(
        settings: CodeStyleSettings,
        modelSettings: CodeStyleSettings
    ): CodeStyleConfigurable {
        return object : CodeStyleAbstractConfigurable(settings, modelSettings, configurableDisplayName) {
            override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel
                = JasmCodeStyleMainPanel(currentSettings, settings)
        }
    }
}

private class JasmCodeStyleMainPanel(current: CodeStyleSettings, settings : CodeStyleSettings)
    : TabbedLanguageCodeStylePanel(JasmLanguage.INSTANCE, current, settings)