package com.github.skleprozzz.intellijfluttercleanfeature

import java.util.*

class PlaceholderReplacer {

    companion object {
        fun replacePlaceholders(content: String, featureName: String, templateName: String): String {
            var result = content


            // Заменяем [FEATURE] в именах файлов/папок
            result = result.replace(templateName, featureName)


            val replacedTemplate =templateName.replace("[", "").replace("]", "")
            // Заменяем <FEATURE | pascalcase> на PascalCase
            val pascalCase = toPascalCase(featureName)
            result = result.replace("<$replacedTemplate \\| pascalcase>".toRegex(), pascalCase)

            // Заменяем <FEATURE | camelcase> на camelCase
            val camelCase = toCamelCase(featureName)
            result = result.replace("<$replacedTemplate \\| camelcase>".toRegex(), camelCase)

            // Заменяем <FEATURE | toLowerCase> на lowercase
            val lowerCase = toLowerCase(featureName)
            result = result.replace("<$replacedTemplate \\| lowercase>".toRegex(), lowerCase)

            // Заменяем <FEATURE | toLowerCase> на uppercase
            val uppercase  = toUpperCase(featureName)
            result = result.replace("<$replacedTemplate \\| uppercase>".toRegex(), uppercase)

            // Заменяем <FEATURE | toLowerCase> на capitalcase
            val capitalcase   = toCapitalCase(featureName)
            result = result.replace("<$replacedTemplate \\| capitalcase>".toRegex(), capitalcase )

            // Заменяем <FEATURE | toLowerCase> на constantcase
            val constantcase    = toConstantCase(featureName)
            result = result.replace("<$replacedTemplate \\| constantcase >".toRegex(), constantcase )

            // Заменяем <FEATURE | toLowerCase> на dotcase
            val dotcase    = toDotCase(featureName)
            result = result.replace("<$replacedTemplate \\| dotcase>".toRegex(), dotcase )

            // Заменяем <FEATURE | toLowerCase> на nocase
            val nocase    = toNoCase(featureName)
            result = result.replace("<$replacedTemplate \\| nocase>".toRegex(), nocase )

            // Заменяем <FEATURE | toLowerCase> на pathcase
            val pathcase    = toPathCase(featureName)
            result = result.replace("<$replacedTemplate \\| pathcase>".toRegex(), pathcase )

            // Заменяем <FEATURE | toLowerCase> на constantcase
            val sentencecase    = toSentenceCase(featureName)
            result = result.replace("<$replacedTemplate \\| sentencecase>".toRegex(), sentencecase )

            // Заменяем <FEATURE | toLowerCase> на constantcase
            val snakecase    = toSnakeCase(featureName)
            result = result.replace("<$replacedTemplate \\| snakecase>".toRegex(), snakecase )

            // Заменяем <FEATURE | toLowerCase> на constantcase
            val singular    = toSingular(featureName)
            result = result.replace("<$replacedTemplate \\| singular>".toRegex(), singular )

            val plural    = toPlural(featureName)
            result = result.replace("<$replacedTemplate \\| plural>".toRegex(), plural )

            val lowercasefirstchar    = toLowercaseFirstChar(featureName)
            result = result.replace("<$replacedTemplate \\| lowercasefirstchar>".toRegex(), lowercasefirstchar )

            val capitalize   = toCapitalize(featureName)
            result = result.replace("<$replacedTemplate \\| capitalize>".toRegex(), capitalize)

            return result
        }


    private fun toPascalCase(input: String?): String {
        if (input == null || input.isEmpty()) return input!!

        val parts = input.split("[\\s_-]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val result = StringBuilder()

        for (part in parts) {
            if (!part.isEmpty()) {
                result.append(part.get(0).uppercaseChar())
                    .append(part.substring(1).lowercase(Locale.getDefault()))
            }
        }

        return result.toString()
    }

    private fun toCamelCase(input: String?): String {
        if (input == null || input.isEmpty()) return input!!

        val pascal = toPascalCase(input)
        return pascal.get(0).lowercaseChar().toString() + pascal.substring(1)
    }

        private fun toLowerCase(input: String): String {
            return input.lowercase(Locale.getDefault())
        }

        private fun toUpperCase(input: String): String {
            return input.uppercase(Locale.getDefault())
        }

        private fun toCapitalCase(input: String?): String {
            if (input == null || input.isEmpty()) return input ?: ""
            return input[0].uppercaseChar() + input.substring(1).lowercase(Locale.getDefault())
        }

        private fun toConstantCase(input: String?): String {
            if (input == null || input.isEmpty()) return input ?: ""
            return input.split("[\\s_-]".toRegex())
                .joinToString("_") { it.uppercase(Locale.getDefault()) }
        }

        private fun toDotCase(input: String?): String {
            if (input == null || input.isEmpty()) return input ?: ""
            return input.split("[\\s_-]".toRegex())
                .joinToString(".") { it.lowercase(Locale.getDefault()) }
        }

        private fun toNoCase(input: String): String {
            return input
        }

        private fun toPathCase(input: String?): String {
            if (input == null || input.isEmpty()) return input ?: ""
            return input.split("[\\s_-]".toRegex())
                .joinToString("/") { it.lowercase(Locale.getDefault()) }
        }

        private fun toSentenceCase(input: String?): String {
            if (input == null || input.isEmpty()) return input ?: ""
            return input[0].uppercaseChar() + input.substring(1)
        }

        private fun toSnakeCase(input: String?): String {
            if (input == null || input.isEmpty()) return input ?: ""
            return input.split("[\\s_-]".toRegex())
                .joinToString("_") { it.lowercase(Locale.getDefault()) }
        }

        private fun toSingular(input: String?): String {
            // Простая реализация - в реальности требуется более сложная логика
            if (input == null || input.isEmpty()) return input ?: ""
            return input.removeSuffix("s")
        }

        private fun toPlural(input: String?): String {
            // Простая реализация - в реальности требуется более сложная логика
            if (input == null || input.isEmpty()) return input ?: ""
            return input + "s"
        }

        private fun toLowercaseFirstChar(input: String?): String {
            if (input == null || input.isEmpty()) return input ?: ""
            return input[0].lowercaseChar() + input.substring(1)
        }

        private fun toCapitalize(input: String?): String {
            if (input == null || input.isEmpty()) return input ?: ""
            return input.split("[\\s_-]".toRegex())
                .joinToString(" ") { it.capitalize(Locale.getDefault()) }
        }

    }
}