package com.github.skleprozzz.intellijfluttercleanfeature


import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import java.io.IOException


class GenerateFolderStructureAction : AnAction() {

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun actionPerformed(event: AnActionEvent) {

        val dialog = InputDialog()
        dialog.show()
        ApplicationManager.getApplication().runWriteAction {
            if (dialog.isOK) {
                val project: Project? = event.getData(PlatformDataKeys.PROJECT)
                val selectedDir: VirtualFile? = event.getData(PlatformDataKeys.VIRTUAL_FILE)

                val featureName = dialog.featureName

                try {
                    if (project != null && selectedDir != null) {
                        val templateDir = TemplateDetector.getTemplateDirectory(project)
                        if (templateDir != null) {
                            val directories = templateDir.children.filter { it.isDirectory }.map {  it.name };

                            if (directories.isEmpty()) {
                                return@runWriteAction
                            }

                            if (directories.size > 1) {
                                val selectorDialog = TemplateSelectorDialog(null, directories)
                                selectorDialog.show()
                                if (selectorDialog.isOK) {
                                    val selectedValue = selectorDialog.getSelectedValue()
                                    if (selectedValue != null) {
                                        TemplateProcessor.processTemplate(project, templateDir, selectedDir, featureName, selectedValue)
                                        Messages.showInfoMessage(project, "Feature template applied successfully!", "Success")

                                    }else {
                                        Messages.showErrorDialog(project, "Failed to apply template: ", "Error")
                                    }

                                }
                                return@runWriteAction
                            }

                                    val selectedTemplate = directories[0]
                                    TemplateProcessor.processTemplate(project, templateDir, selectedDir, featureName, selectedTemplate)
                                    Messages.showInfoMessage(project, "Feature template applied successfully!", "Success")





                        }
                    }

                } catch (ex: IOException) {
                    Messages.showErrorDialog(project, "Failed to apply template: " + ex.message, "Error")
                }
//                generateFolderStructure(libDir, featureName)
            }
        }

    }
//
//    override fun update(e: AnActionEvent) {
//        val project = e.getData<Project?>(PlatformDataKeys.PROJECT)
//        val isTemplatePresent = project != null && TemplateDetector.isTemplatePresent(project)
//        e.presentation.isEnabledAndVisible = isTemplatePresent
//    }

}