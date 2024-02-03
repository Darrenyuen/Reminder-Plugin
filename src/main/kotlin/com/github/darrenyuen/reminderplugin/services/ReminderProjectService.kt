package com.github.darrenyuen.reminderplugin.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.github.darrenyuen.reminderplugin.ReminderBundle
import java.util.concurrent.Executors
import java.util.concurrent.Future

@Service(Service.Level.PROJECT)
class ReminderProjectService(private val project: Project) {

    val executor = Executors.newSingleThreadExecutor()
    var lastFuture: Future<*>? = null

    init {
        thisLogger().info(ReminderBundle.message("projectService", project.name))
    }

    fun getRandomNumber() = (1..100).random()

    fun intervalTimeIsValid(intervalTime: String?): Int {
        return intervalTime?.toIntOrNull() ?: -1
    }

    fun startRemindTask(interval: Int) {
        lastFuture?.cancel(true)
        lastFuture = executor.submit(RemindTask(project, interval))
    }

    fun cancelAllRemindTask() {
        thisLogger().warn("$this cancelAllRemindTask")
        executor.shutdownNow()
    }
}
