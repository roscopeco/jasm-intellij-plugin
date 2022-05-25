package com.roscopeco.jasm.intellij.listeners

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.roscopeco.jasm.intellij.services.JasmProjectService

internal class JasmProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.service<JasmProjectService>()
    }
}
