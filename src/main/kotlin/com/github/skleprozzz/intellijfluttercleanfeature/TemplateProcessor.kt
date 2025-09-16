package com.github.skleprozzz.intellijfluttercleanfeature


import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.annotations.NotNull
import java.io.IOException


class TemplateProcessor {


    companion object {
        @Throws(IOException::class)
        fun processTemplate(
            @NotNull project: Project?, @NotNull sourceDir: VirtualFile,
            @NotNull targetDir: VirtualFile, @NotNull featureName: String,
            @NotNull templateName: String,
        ) {
            var featureTemplate = sourceDir.findChild(templateName)
            if (featureTemplate == null || !featureTemplate.isDirectory()) {
                print(project)
                throw IOException("Template $templateName directory not found")
            }


            // Создаем целевую директорию для фичи
            val featureTargetDir = targetDir.createChildDirectory(project, featureName)


            // Рекурсивно копируем и обрабатываем файлы
            copyAndProcessDirectory(project, featureTemplate, featureTargetDir, featureName, templateName)
        }

        @Throws(IOException::class)
        private fun copyAndProcessDirectory(
            @NotNull project: Project?, @NotNull source: VirtualFile,
            @NotNull target: VirtualFile, @NotNull featureName: String, templateName: String
        ) {
            for (file in source.getChildren()) {
                if (file.isDirectory()) {
                    // Обрабатываем имя директории
                    val newDirName: String = PlaceholderReplacer.replacePlaceholders(file.getName(), featureName, templateName)
                    val newDir = target.createChildDirectory(project, newDirName)
                    copyAndProcessDirectory(project, file, newDir, featureName, templateName)
                } else {
                    // Обрабатываем имя файла и его содержимое
                    val newFileName: String = PlaceholderReplacer.replacePlaceholders(file.getName(), featureName, templateName)
                    val newFile = target.createChildData(project, newFileName)


                    // Читаем содержимое файла, заменяем плейсхолдеры и записываем
                    val content = String(file.contentsToByteArray(), charset("UTF-8"))
                    val processedContent: String = PlaceholderReplacer.replacePlaceholders(content, featureName, templateName)
                    newFile.setBinaryContent(processedContent.toByteArray(charset("UTF-8")))
                }
            }
        }
    }
}