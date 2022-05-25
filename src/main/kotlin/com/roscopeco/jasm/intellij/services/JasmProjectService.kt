package com.roscopeco.jasm.intellij.services

import com.intellij.openapi.project.Project
import com.roscopeco.jasm.intellij.MyBundle

class JasmProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
