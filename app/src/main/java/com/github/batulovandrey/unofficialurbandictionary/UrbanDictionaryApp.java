package com.github.batulovandrey.unofficialurbandictionary;

import android.app.Application;
import android.content.Intent;

import com.github.batulovandrey.unofficialurbandictionary.dagger.AppModule;
import com.github.batulovandrey.unofficialurbandictionary.dagger.DaggerNetComponent;
import com.github.batulovandrey.unofficialurbandictionary.dagger.DataModule;
import com.github.batulovandrey.unofficialurbandictionary.dagger.NetComponent;
import com.github.batulovandrey.unofficialurbandictionary.dagger.NetModule;
import com.github.batulovandrey.unofficialurbandictionary.service.MigrateService;

/**
 * @author Andrey Batulov on 30/10/2017
 */

public class UrbanDictionaryApp extends Application {

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

        startService(new Intent(this, MigrateService.class));
    }
}