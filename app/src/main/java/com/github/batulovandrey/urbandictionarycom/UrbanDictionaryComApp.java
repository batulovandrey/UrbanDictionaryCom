package com.github.batulovandrey.urbandictionarycom;

import android.app.Application;

import com.github.batulovandrey.urbandictionarycom.dagger.AppModule;
import com.github.batulovandrey.urbandictionarycom.dagger.DaggerNetComponent;
import com.github.batulovandrey.urbandictionarycom.dagger.NetComponent;
import com.github.batulovandrey.urbandictionarycom.dagger.NetModule;

/**
 * @author Andrey Batulov on 30/10/2017
 */

public class UrbanDictionaryComApp extends Application {

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