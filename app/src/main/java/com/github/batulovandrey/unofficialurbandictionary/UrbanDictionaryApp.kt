package com.github.batulovandrey.unofficialurbandictionary

import android.app.Application
import android.content.Intent
import android.os.Build
import android.support.v4.app.JobIntentService
import com.github.batulovandrey.unofficialurbandictionary.dagger.*
import com.github.batulovandrey.unofficialurbandictionary.service.MigrateJobIntentService
import com.github.batulovandrey.unofficialurbandictionary.service.MigrateService
import com.google.firebase.FirebaseApp

class UrbanDictionaryApp: Application() {

    override fun onCreate() {
        super.onCreate()

        netComponent = DaggerNetComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule(BASE_URL))
                .dataModule(DataModule())
                .build()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            startService(Intent(this, MigrateService::class.java))
        } else {
            JobIntentService.enqueueWork(this.applicationContext,
                    MigrateJobIntentService::class.java, MigrateJobIntentService.JOB_ID, Intent())
        }

        FirebaseApp.initializeApp(this)
    }

    companion object {

        private const val BASE_URL = "http://api.urbandictionary.com/"
        lateinit var netComponent: NetComponent
    }
}