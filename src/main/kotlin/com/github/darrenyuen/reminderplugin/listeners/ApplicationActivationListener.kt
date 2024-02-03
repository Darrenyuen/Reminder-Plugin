package com.github.darrenyuen.reminderplugin.listeners

import com.github.darrenyuen.reminderplugin.services.PersistentState
import com.github.darrenyuen.reminderplugin.services.ReminderProjectService
import com.intellij.openapi.application.ApplicationActivationListener
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.wm.IdeFrame

internal class ApplicationActivationListener :
    ApplicationActivationListener {

    override fun applicationActivated(ideFrame: IdeFrame) {
        // 获取配置，启动提醒
        val value = PersistentState.getInstance().remindIntervalTime
        thisLogger().warn("ApplicationActivationListener applicationActivated remind interval time is $value min")
//        val service = ideFrame.project?.service<ReminderProjectService>()
//        value?.let {
//            service?.startRemindTask(it)
//        }
    }
}
