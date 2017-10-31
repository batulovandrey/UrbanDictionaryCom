package com.github.batulovandrey.unofficialurbandictionary.dagger;

import android.app.Application;

import com.github.batulovandrey.unofficialurbandictionary.realm.RealmManager;
import com.github.batulovandrey.unofficialurbandictionary.service.UrbanDictionaryService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Andrey Batulov on 30/10/2017
 */

@Module
public class NetModule {

    private final String baseUrl;

    public NetModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    RealmManager provideRealmManager(Application application) {
        return new RealmManager(application.getApplicationContext());
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
    }

    @Provides
    @Singleton
    UrbanDictionaryService provideService(Retrofit retrofit) {
        return retrofit.create(UrbanDictionaryService.class);
    }
}