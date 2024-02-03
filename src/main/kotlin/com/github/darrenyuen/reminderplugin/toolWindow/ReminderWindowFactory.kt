package com.github.darrenyuen.reminderplugin.toolWindow

import com.github.darrenyuen.reminderplugin.ReminderBundle
import com.github.darrenyuen.reminderplugin.services.PersistentState
import com.github.darrenyuen.reminderplugin.services.ReminderProjectService
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.GotItTooltip
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import javax.swing.JButton
import javax.swing.JFormattedTextField


class ReminderWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = ReminderWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class ReminderWindow(toolWindow: ToolWindow) {

        private val service = toolWindow.project.service<ReminderProjectService>()
        private val persistentService = PersistentState.getInstance()

        fun getContent() = JBPanel<JBPanel<*>>().apply {
            val label = JBLabel(ReminderBundle.message("RemindIntervalTime"))

            val field = JFormattedTextField(this).apply {
                columns = 5
                value = persistentService.remindIntervalTime
            }

            add(label)
            add(field)
            add(JButton(ReminderBundle.message("Confirm")).apply {
                addActionListener {
                    val value = service.intervalTimeIsValid(field.value?.toString())
                    if (value > 0) {
                        thisLogger().warn("remind interval time is $value")
                        persistentService.remindIntervalTime = value
                        service.startRemindTask(value)
                        GotItTooltip( "ide.features.trainer.confirm","Reset Success!").show(this, GotItTooltip.BOTTOM_MIDDLE)
                    } else {
                        thisLogger().warn("remind interval time is invalid")
                    }
                }
            })
            add(JButton(ReminderBundle.message("Cancel")).apply {
                addActionListener {
                    service.cancelAllRemindTask()
                    GotItTooltip( "ide.features.trainer.cancel","Cancel Success!").show(this, GotItTooltip.BOTTOM_MIDDLE)
                }
            })
        }
    }
}
