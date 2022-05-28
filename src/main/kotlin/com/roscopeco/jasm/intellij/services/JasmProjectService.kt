package com.roscopeco.jasm.intellij.services

import com.intellij.openapi.project.Project
import com.roscopeco.jasm.intellij.JasmBundle

class JasmProjectService(project: Project) {

    init {
        println(JasmBundle.message("projectService", project.name))
    }
}
