package com.github.darrenyuen.reminderplugin.services

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications

object RemindNotify {
    fun showRemindNotify() {
        Notifications.Bus.notify(
            Notification(
                "Print",
                "Remind",
                "It's time to have a rest",
                NotificationType.WARNING
            )
        )
    }
}