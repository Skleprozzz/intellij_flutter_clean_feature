package com.github.skleprozzz.intellijfluttercleanfeature

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import org.jetbrains.annotations.Nullable
import java.awt.*
import javax.swing.*


class TemplateSelectorDialog(
    project: Project?,
    private val options: List<String>
) : DialogWrapper(project)
{

    private var comboBox: JComboBox<String>
    private var selectedValue: String? = null

     init {

        setTitle("Choose an Option")

        // Варианты для выбора

        comboBox = ComboBox<String>(options.toTypedArray())

        // Устанавливаем дефолтный выбор (опционально)
        comboBox.setSelectedIndex(0)

        init() // ВАЖНО: вызвать init() после настройки компонентов
    }

    @Nullable
    protected override fun createCenterPanel(): JComponent {
        val panel = JPanel(BorderLayout())
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10))

        val label = JLabel("Please select an option:")
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0))

        panel.add(label, BorderLayout.NORTH)
        panel.add(comboBox, BorderLayout.CENTER)

        return panel
    }

    override fun getPreferredSize(): Dimension {
        return Dimension(300, 100)
    }

    protected override fun doOKAction() {
        // Сохраняем выбранный элемент перед закрытием
        selectedValue = comboBox.selectedItem as String?
        super.doOKAction()
    }

    fun getSelectedValue(): String? {
        return selectedValue
    }

}