package com.github.skleprozzz.intellijfluttercleanfeature

import com.github.skleprozzz.intellijfluttercleanfeature.settings.MyPluginSettings
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile


class TemplateDetector {

    companion object {


        fun isTemplatePresent(project: Project): Boolean {
            var currentValue: String? = MyPluginSettings.getInstance().myTextFieldValue
            val baseDir = project.getBaseDir()
            if (baseDir == null) return false
            val templateDir: VirtualFile? = baseDir.findChild(currentValue ?: ".flutter_clean_feature_template")
            if (templateDir == null || !templateDir.isDirectory()) return false


            // Проверяем наличие необходимой структуры

//            val featureDir = templateDir.findChild("[FEATURE]")
            return templateDir.children.isNotEmpty()
//            return !(featureDir == null || !featureDir.isDirectory);
        }

        fun getTemplateDirectory(project: Project): VirtualFile? {
            var currentValue: String? = MyPluginSettings.getInstance().myTextFieldValue

            val baseDir = project.getBaseDir()
            if (baseDir == null) return null

            return baseDir.findChild(currentValue ?:".flutter_clean_feature_template")
        }
    }
}