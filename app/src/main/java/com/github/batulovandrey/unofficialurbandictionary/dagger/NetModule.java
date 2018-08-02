package com.github.batulovandrey.unofficialurbandictionary.dagger;

import android.app.Application;
import android.content.Context;

import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp;
import com.github.batulovandrey.unofficialurbandictionary.api.UrbanDictionaryService;
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager;
import com.github.batulovandrey.unofficialurbandictionary.data.PopularWords;
import com.github.batulovandrey.unofficialurbandictionary.realm.RealmManager;
import com.github.batulovandrey.unofficialurbandictionary.ui.main.MainMvpPresenter;
import com.github.batulovandrey.unofficialurbandictionary.ui.main.MainMvpView;
import com.github.batulovandrey.unofficialurbandictionary.ui.main.MainPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
    PopularWords providePopularWords(Application application) {
        return new PopularWords(application.getApplicationContext());
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    UrbanDictionaryService provideService(Retrofit retrofit) {
        return retrofit.create(UrbanDictionaryService.class);
    }

    @Provides
    @Singleton
    MainMvpPresenter<MainMvpView> provideMainPresenter(DataManager dataManager,
                                                       CompositeDisposable disposable) {
        return new MainPresenter<>(dataManager, disposable);
    }

    @Provides
    CompositeDisposable provideDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    DataManager provideDataManager(Application application) {
        return ((UrbanDictionaryApp) application).getDataManager();
    }


    @Provides
    Context provideContext(Application application) {
        return application.getApplicationContext();
    }
}