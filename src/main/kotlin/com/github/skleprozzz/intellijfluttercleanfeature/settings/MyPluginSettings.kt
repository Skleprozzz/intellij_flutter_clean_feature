package com.github.skleprozzz.intellijfluttercleanfeature.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "MyPluginSettings", storages = [Storage("myPluginSettings.xml")])
class MyPluginSettings : PersistentStateComponent<MyPluginSettings> {

    var myTextFieldValue: String = ".flutter_clean_feature_template"


    override fun getState(): MyPluginSettings? {
        return this
    }

    override fun loadState(state: MyPluginSettings) {
        XmlSerializerUtil.copyBean<MyPluginSettings?>(state, this)
    }

 companion object {
     fun getInstance(): MyPluginSettings {
         return ServiceManager.getService(MyPluginSettings::class.java)
     }
 }
}