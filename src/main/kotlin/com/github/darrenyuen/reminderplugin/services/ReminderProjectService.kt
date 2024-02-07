package com.github.darrenyuen.reminderplugin.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.github.darrenyuen.reminderplugin.ReminderBundle
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.Future

@Service(Service.Level.PROJECT)
class ReminderProjectService(private val project: Project) {

    private val useMultiThread = true
    private val executor by lazy { Executors.newSingleThreadExecutor() }
    private var lastFuture: Future<*>? = null
    private val mainScope = MainScope()

    init {
        thisLogger().info(ReminderBundle.message("projectService", project.name))
    }

    fun getRandomNumber() = (1..100).random()

    fun intervalTimeIsValid(intervalTime: String?): Int {
        return intervalTime?.toIntOrNull() ?: -1
    }

    fun startRemindTask(interval: Int) {
        if (useMultiThread) {
            lastFuture?.cancel(true)
            lastFuture = executor.submit(RemindTask(project, interval))
        } else {
            mainScope.launch {
                cancel()
                while (true) {
                    delay(interval * 1000L * 60)
                    RemindNotify.showRemindNotify()
                }
            }
        }
    }

    fun cancelAllRemindTask() {
        mainScope.cancel()
        thisLogger().warn("$this cancelAllRemindTask")
        executor.shutdownNow()
    }
}
