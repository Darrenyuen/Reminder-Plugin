package com.github.darrenyuen.reminderplugin.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.github.darrenyuen.reminderplugin.ReminderBundle

@Service(Service.Level.PROJECT)
class ReminderProjectService(project: Project) {

    init {
        thisLogger().info(ReminderBundle.message("projectService", project.name))
        thisLogger().warn("ProjectService Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    fun getRandomNumber() = (1..100).random()

    fun intervalTimeIsValid(intervalTime: String?): Int {
        return intervalTime?.toIntOrNull() ?: -1
    }
}
