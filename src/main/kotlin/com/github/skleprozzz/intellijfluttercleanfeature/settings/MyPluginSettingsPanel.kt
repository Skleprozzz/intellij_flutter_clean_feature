package com.github.skleprozzz.intellijfluttercleanfeature.settings

import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*


class MyPluginSettingsPanel {
    private val mainPanel: JPanel
    private val textField: JTextField
    private val resetButton: JButton

    val DEFAULT_VALUE: String = ".flutter_clean_feature_template"

    init {
        mainPanel = JPanel(BorderLayout())
        val label = JLabel("Clean feature generator plugin settings name:")
        textField = JTextField(MyPluginSettings.getInstance().myTextFieldValue, 20, )


        val inputPanel = JPanel(FlowLayout(FlowLayout.LEFT))
        inputPanel.add(label)
        inputPanel.add(textField)

        resetButton = JButton("Reset to Default")
        resetButton.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                textField.setText(DEFAULT_VALUE)
            }
        })

        val buttonPanel = JPanel(FlowLayout(FlowLayout.LEFT))
        buttonPanel.add(resetButton)

        mainPanel.add(inputPanel, BorderLayout.NORTH)
        mainPanel.add(buttonPanel, BorderLayout.CENTER)
    }

    fun getMainPanel(): JComponent {
        return mainPanel
    }

    fun getTextFieldValue(): String? {
        return textField.getText()
    }

    fun setTextFieldValue(value: String?) {
        textField.setText(value ?: DEFAULT_VALUE)
    }
}