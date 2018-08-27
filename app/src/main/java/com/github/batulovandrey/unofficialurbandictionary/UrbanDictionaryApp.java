package com.github.batulovandrey.unofficialurbandictionary;

import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.JobIntentService;

import com.github.batulovandrey.unofficialurbandictionary.dagger.AppModule;
import com.github.batulovandrey.unofficialurbandictionary.dagger.DaggerNetComponent;
import com.github.batulovandrey.unofficialurbandictionary.dagger.DataModule;
import com.github.batulovandrey.unofficialurbandictionary.dagger.NetComponent;
import com.github.batulovandrey.unofficialurbandictionary.dagger.NetModule;
import com.github.batulovandrey.unofficialurbandictionary.service.MigrateJobIntentService;
import com.github.batulovandrey.unofficialurbandictionary.service.MigrateService;

/**
 * @author Andrey Batulov on 30/10/2017
 */

public class UrbanDictionaryApp extends MultiDexApplication {

    private static final String BASE_URL = "http://api.urbandictionary.com/";
    private static NetComponent sNetComponent;


    public static NetComponent getNetComponent() {
        return sNetComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BASE_URL))
                .dataModule(new DataModule())
                .build();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            startService(new Intent(this, MigrateService.class));
        } else {
            JobIntentService.enqueueWork(this.getApplicationContext(),
                    MigrateJobIntentService.class, MigrateJobIntentService.JOB_ID, new Intent());
        }
    }
}