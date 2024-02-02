package com.github.darrenyuen.reminderplugin.services

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "state", storages = [Storage(value = "state.xml")])
class PersistentState private constructor(): PersistentStateComponent<PersistentState> {

    companion object {
        fun getInstance(): PersistentState {
            return ApplicationManager.getApplication().getService(PersistentState::class.java)
        }
    }

    var remindIntervalTime: Int? = null


    override fun getState(): PersistentState? {
        return this
    }

    override fun loadState(p0: PersistentState) {
        XmlSerializerUtil.copyBean(p0, this)
    }
}