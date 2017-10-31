package com.github.batulovandrey.unofficialurbandictionary;

import android.app.Application;

import com.github.batulovandrey.unofficialurbandictionary.dagger.AppModule;
import com.github.batulovandrey.unofficialurbandictionary.dagger.DaggerNetComponent;
import com.github.batulovandrey.unofficialurbandictionary.dagger.NetComponent;
import com.github.batulovandrey.unofficialurbandictionary.dagger.NetModule;

/**
 * @author Andrey Batulov on 30/10/2017
 */

public class UrbanDictionaryApp extends Application {

    private static final String BASE_URL = "https://mashape-community-urban-dictionary.p.mashape.com";
    private static NetComponent sNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BASE_URL))
                .build();
    }

    public static NetComponent getNetComponent() {
        return sNetComponent;
    }
}