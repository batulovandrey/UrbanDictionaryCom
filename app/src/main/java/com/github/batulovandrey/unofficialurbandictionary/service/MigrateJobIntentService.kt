package com.github.batulovandrey.unofficialurbandictionary.service

import android.content.Intent
import android.support.v4.app.JobIntentService
import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
import com.github.batulovandrey.unofficialurbandictionary.data.db.MigrateManager
import com.github.batulovandrey.unofficialurbandictionary.data.realm.RealmManager
import javax.inject.Inject

class MigrateJobIntentService: JobIntentService() {

    @Inject
    lateinit var dataManager: DataManager

    override fun onCreate() {
        super.onCreate()
        UrbanDictionaryApp.netComponent.inject(this)
    }

    override fun onHandleWork(intent: Intent) {
        val realmManager = RealmManager(this)
        val migrateManager = MigrateManager(realmManager, dataManager)
        migrateManager.migrate()
    }

    companion object {

        const val JOB_ID: Int = 2507
    }
}