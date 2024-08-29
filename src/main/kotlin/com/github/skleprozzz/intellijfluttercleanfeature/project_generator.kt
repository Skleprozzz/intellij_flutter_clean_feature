package com.github.skleprozzz.intellijfluttercleanfeature


import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import java.util.*


class GenerateFolderStructureAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val dialog = InputDialog()
        dialog.show()
        ApplicationManager.getApplication().runWriteAction {
            if (dialog.isOK) {
                val featureName = dialog.featureName
                val libDir = event.getData(PlatformDataKeys.VIRTUAL_FILE)
                generateFolderStructure(libDir, featureName)
            }
        }

    }

    private fun generateFolderStructure(libDir: VirtualFile?, featureName: String) {
        if (libDir != null) {
            val featureDir = libDir.createChildDirectory(null, featureName)

            /// data

            val dataDir = featureDir.createChildDirectory(null, "data")
            dataDir.createChildDirectory(null, "data_source").createChildData(null, "${featureName}_data_source.dart")
                .setBinaryContent(getDataSourceContent(featureName).toByteArray())

            val dtoDir = dataDir.createChildDirectory(null, "dto")
            dtoDir.createChildDirectory(null, "requests").createChildData(null, "${featureName}_request_dto.dart")
                .setBinaryContent(getRequestsDtoContent(featureName).toByteArray())
            dtoDir.createChildDirectory(null, "responses").createChildData(null, "${featureName}_response_dto.dart")
                .setBinaryContent(getResponsesDtoContent(featureName).toByteArray())

            dataDir.createChildDirectory(null, "repository").createChildData(null, "${featureName}_repository.dart")
                .setBinaryContent(getRepositoryContent(featureName).toByteArray())


            /// domain

            val domainDir = featureDir.createChildDirectory(null, "domain")

            domainDir.createChildDirectory(null, "entity")
                .createChildData(null, "${featureName}_entity.dart")
                .setBinaryContent(getEntityContent(featureName).toByteArray())

            domainDir.createChildDirectory(null, "failures")
                .createChildData(null, "${featureName}_failure.dart")
                .setBinaryContent(getFailuresContent(featureName).toByteArray())

            domainDir.createChildDirectory(null, "repository")
                .createChildData(null, "${featureName}_repository_interface.dart")
                .setBinaryContent(getRepositoryInterfaceContent(featureName).toByteArray())


            domainDir.createChildDirectory(null, "service").createChildData(null, "${featureName}_service.dart")
                .setBinaryContent(getServiceContent(featureName).toByteArray())


            domainDir.createChildDirectory(null, "use_case").createChildData(null, "${featureName}_use_case.dart")
                .setBinaryContent(
                    getUseCaseContent(featureName).toByteArray()
                )


            /// presentation

            val presentationDir = featureDir.createChildDirectory(null, "presentation")

            presentationDir.createChildData(null, "${featureName}_page.dart").setBinaryContent(
                getPageContent(featureName).toByteArray()
            )

            presentationDir.createChildDirectory(null, "widgets")

            val blocDir = presentationDir.createChildDirectory(null, "bloc")

            blocDir.createChildData(null, "${featureName}_bloc.dart").setBinaryContent(
                getBlocContent(featureName).toByteArray()
            )

            blocDir.createChildData(null, "${featureName}_event.dart").setBinaryContent(
                getEventContent(featureName).toByteArray()
            )

            blocDir.createChildData(null, "${featureName}_state.dart").setBinaryContent(
                getStateContent(featureName).toByteArray()
            )


            /// module

            featureDir.createChildData(null, "${featureName}_module.dart").setBinaryContent(
                getModuleContent(featureName).toByteArray()
            )

            showToastMessage("Generated Successfully!")

        }
    }

    private fun String.toCamelCase(): String {
        return this.split("_")
            .joinToString("") { it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }
    }

    private fun showToastMessage(message: String) {
        ApplicationManager.getApplication().invokeLater {
            Messages.showMessageDialog(message, "Success", Messages.getInformationIcon())
        }
    }

//    private fun getPackageName(project: Project): String? {
//        val pubspecFile = project.guessProjectDir()?.findChild("pubspec.yaml") ?: return null
//        val psiFile = PsiManager.getInstance(project).findFile(pubspecFile) ?: return null
//        val virtualFile = psiFile.virtualFile ?: return null
//
//        val yaml = Yaml()
//        val inputStream = virtualFile.inputStream
//        val yamlObject = yaml.load<Map<*, *>>(InputStreamReader(inputStream))
//
//        return yamlObject?.get("name") as? String
//    }

    private fun getDataSourceContent(featureName: String): String {
        return """
            |final class ${featureName.toCamelCase()}DataSource {
            |  const ${featureName.toCamelCase()}DataSource({required NetworkService service}) : _service = service;
            |
            | final NetworkService _service;
            |
            | }
            | 
        """.trimMargin()
    }

    private fun getRequestsDtoContent(featureName: String): String {
        return """
            |final class ${featureName.toCamelCase()}RequestDto {}
            |
        """.trimMargin()

    }

    private fun getResponsesDtoContent(featureName: String): String {
        return """
            |final class ${featureName.toCamelCase()}ResponseDto {}
            |
        """.trimMargin()

    }

    private fun getRepositoryContent(featureName: String): String {
        return """
            |class ${featureName.toCamelCase()}Repository implements ${featureName.toCamelCase()}RepositoryInterface {
            |   ${featureName.toCamelCase()}Repository({required ${featureName.toCamelCase()}DataSource dataSource}) : 
            |   _dataSource = dataSource;
            |   
            |   ${featureName.toCamelCase()}DataSource _dataSource;
            |   
            |}
            |
        """.trimMargin()
    }

    private fun getEntityContent(featureName: String): String {
        return """
            |import 'package:freezed_annotation/freezed_annotation.dart';
            |
            |part '${featureName}_entity.freezed.dart';
            |
            |@freezed
            |abstract class ${featureName.toCamelCase()}Entity with _$${featureName.toCamelCase()}Entity {
            |  const ${featureName.toCamelCase()}Entity._();
            |
            |  const factory ${featureName.toCamelCase()}Entity() = _${featureName.toCamelCase()}Entity;
            |}
            |
        """.trimMargin()
    }

    private fun getFailuresContent(featureName: String): String {
        return """
            |class Failures implements Exception {
            |  const Failures({this.message, this.responseCode});
            |
            |  final String? message;
            |  final String? responseCode;
            |}
            |
            |sealed class ${featureName.toCamelCase()}Failures extends Failures {
            |  const ${featureName.toCamelCase()}Failures();
            |}
            |
            |final class ${featureName.toCamelCase()}ApiFailure extends ${featureName.toCamelCase()}Failures {
            |  const ${featureName.toCamelCase()}ApiFailure();
            |}
            |
        """.trimMargin()
    }

    private fun getRepositoryInterfaceContent(featureName: String): String {
        return """
            |abstract interface class ${featureName.toCamelCase()}RepositoryInterface {}
            |
        """.trimMargin()
    }

    private fun getServiceContent(featureName: String): String {
        return """
            |final class ${featureName.toCamelCase()}Service {
            |
            |const ${featureName.toCamelCase()}Service({required ${featureName.toCamelCase()}RepositoryInterface repository}) :  _repository = repository;
            |
            |final ${featureName.toCamelCase()}RepositoryInterface _repository;
            |}
            |
        """.trimMargin()
    }

    private fun getUseCaseContent(featureName: String): String {
        return """
            |final class ${featureName.toCamelCase()}UseCase {
            |
            |const ${featureName.toCamelCase()}UseCase({required ${featureName.toCamelCase()}RepositoryInterface repository}) :  _repository = repository;
            |
            |final ${featureName.toCamelCase()}RepositoryInterface _repository;
            |}
            |
        """.trimMargin()
    }

    private fun getPageContent(featureName: String): String {
        return """
                |import 'package:flutter/material.dart';
                |import 'package:flutter_bloc/flutter_bloc.dart';
                |
                |import '../bloc/${featureName}_bloc.dart';

                |class ${featureName.toCamelCase()}Page extends StatelessWidget {
                |  const ${featureName.toCamelCase()}Page({
                |    super.key,
                |  });
                |
                |
                |  @override
                |  Widget build(BuildContext context) {
                |    return BlocProvider<${featureName.toCamelCase()}Bloc>(
                |      create: (_) => <${featureName.toCamelCase()}Bloc>(),
                |      child: const SizedBox(),
                |    );
                |  }
                |}
        """.trimMargin()
    }

    private fun getBlocContent(featureName: String): String {
        return """
                |import 'package:bloc/bloc.dart';
                |import 'package:freezed_annotation/freezed_annotation.dart';
                |
                |part '${featureName}_event.dart';
                |part '${featureName}_state.dart';
                |part '${featureName}_bloc.freezed.dart';
                |
                |class ${featureName.toCamelCase()}Bloc extends Bloc<${featureName.toCamelCase()}Event, ${featureName.toCamelCase()}State> {
                |  ${featureName.toCamelCase()}Bloc({required ${featureName.toCamelCase()}UseCase useCase}) : _useCase = useCase, super(_${featureName.toCamelCase()}State()) {
                |    on<${featureName.toCamelCase()}Event>((event, emit) {
                |      // TODO: implement event handler
                |    });
                |  }
                |
                |  final ${featureName.toCamelCase()}UseCase _useCase;
                |}
                |
        """.trimMargin()
    }

    private fun getEventContent(featureName: String): String {
        return """
                |part of '${featureName}_bloc.dart';
                |
                |@freezed
                |abstract class ${featureName.toCamelCase()}Event  with _$${featureName.toCamelCase()}Event {
                |  const factory ${featureName.toCamelCase()}Event.init() = Init;
                |}
                |
            """.trimMargin()
    }

    private fun getStateContent(featureName: String): String {
        return """
                |part of '${featureName}_bloc.dart';
                |
                |@freezed
                |abstract class ${featureName.toCamelCase()}State with _$${featureName.toCamelCase()}State {
                |  const factory ${featureName.toCamelCase()}State() = _${featureName.toCamelCase()}State;
                |}
                |
            """.trimMargin()
    }

    private fun getModuleContent(featureName: String): String {
        return """
            |import 'package:injectable/injectable.dart';
            |
            |@module
            |abstract class ${featureName.toCamelCase()}Module {
            |  ${featureName.toCamelCase()}DataSource get${featureName.toCamelCase()}DataSource(NetworkService service) =>
            |      ${featureName.toCamelCase()}DataSource(service: service);
            |
            |  ${featureName.toCamelCase()}RepositoryInterface get${featureName.toCamelCase()}RepositoryInterface(${featureName.toCamelCase()}DataSource dataSource) =>
            |      ${featureName.toCamelCase()}Repository(dataSource: dataSource);
            |
            |  ${featureName.toCamelCase()}UseCase get${featureName.toCamelCase()}UseCase(${featureName.toCamelCase()}RepositoryInterface repository) =>
            |      ${featureName.toCamelCase()}UseCase(repository: repository);
            |
            |  ${featureName.toCamelCase()}Service get${featureName.toCamelCase()}Service(${featureName.toCamelCase()}RepositoryInterface repository) =>
            |      ${featureName.toCamelCase()}Service(repository: repository);
            |
            |  ${featureName.toCamelCase()}Bloc get${featureName.toCamelCase()}Bloc(${featureName.toCamelCase()}UseCase useCase) =>
            |     ${featureName.toCamelCase()}Bloc(useCase: useCase);
            |}
            
        """.trimMargin()
    }
}