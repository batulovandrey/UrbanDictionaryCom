package com.github.batulovandrey.unofficialurbandictionary.service

import android.app.IntentService
import android.content.Intent
import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
import com.github.batulovandrey.unofficialurbandictionary.data.db.MigrateManager
import com.github.batulovandrey.unofficialurbandictionary.data.realm.RealmManager
import javax.inject.Inject

class MigrateService : IntentService("migrateService") {

    @Inject
    lateinit var dataManager: DataManager

    override fun onCreate() {
        super.onCreate()
        UrbanDictionaryApp.netComponent.inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        val realmManager = RealmManager(this)
        val migrateManager = MigrateManager(realmManager, dataManager)
        migrateManager.migrate()
    }
}