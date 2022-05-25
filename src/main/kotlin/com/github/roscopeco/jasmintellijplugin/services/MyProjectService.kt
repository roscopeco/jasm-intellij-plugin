package com.github.roscopeco.jasmintellijplugin.services

import com.intellij.openapi.project.Project
import com.github.roscopeco.jasmintellijplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
