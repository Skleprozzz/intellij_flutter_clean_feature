package com.github.skleprozzz.intellijfluttercleanfeature.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class MyPluginConfigurable : Configurable {
    private var panel: MyPluginSettingsPanel? = null
    private val settings: MyPluginSettings? = MyPluginSettings.getInstance()

    @Nls(capitalization = Nls.Capitalization.Title)
    override fun getDisplayName(): String {
        return "Clean feature generator plugin settings"
    }

    override fun createComponent(): JComponent? {
        panel = MyPluginSettingsPanel()
        return panel?.getMainPanel()
    }

    override fun isModified(): Boolean {
        return settings?.myTextFieldValue != panel?.getTextFieldValue()
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        settings?.myTextFieldValue = panel?.getTextFieldValue() ?: ""
    }

    override fun reset() {
        panel?.setTextFieldValue(settings?.myTextFieldValue)
    }

    override fun disposeUIResources() {
        panel = null
    }
}