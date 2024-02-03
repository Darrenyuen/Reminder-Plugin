package com.github.darrenyuen.reminderplugin.services

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project

class RemindTask(private val project: Project, private val interval: Int) :Runnable {
    override fun run() {
        thisLogger().warn("$this RemindTask start interval=$interval")
        for (i in interval downTo  1) {
            try {
                Thread.sleep(1000 * 60)
            } catch (e: Exception) {
                e.printStackTrace()
                if (e is InterruptedException) {
                    return
                }
            }
        }
        thisLogger().warn("$this RemindTask finish")
        Notifications.Bus.notify(
            Notification(
                "Print",
                "Remind",
                "It's time to have a rest",
                NotificationType.WARNING
            )
        )

        project.service<ReminderProjectService>().startRemindTask(interval)
    }
}