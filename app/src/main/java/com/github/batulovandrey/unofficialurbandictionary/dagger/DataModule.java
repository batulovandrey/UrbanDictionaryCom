package com.github.batulovandrey.unofficialurbandictionary.dagger;

import android.content.Context;

import com.github.batulovandrey.unofficialurbandictionary.data.AppDataManager;
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager;
import com.github.batulovandrey.unofficialurbandictionary.data.PopularWords;
import com.github.batulovandrey.unofficialurbandictionary.data.db.AppDbHelper;
import com.github.batulovandrey.unofficialurbandictionary.data.db.DbHelper;
import com.github.batulovandrey.unofficialurbandictionary.data.network.AppNetworkHelper;
import com.github.batulovandrey.unofficialurbandictionary.data.network.NetworkHelper;
import com.github.batulovandrey.unofficialurbandictionary.realm.RealmManager;
import com.github.batulovandrey.unofficialurbandictionary.ui.detail.DetailMvpPresenter;
import com.github.batulovandrey.unofficialurbandictionary.ui.detail.DetailMvpView;
import com.github.batulovandrey.unofficialurbandictionary.ui.detail.DetailPresenter;
import com.github.batulovandrey.unofficialurbandictionary.ui.favorites.FavoritesMvpPresenter;
import com.github.batulovandrey.unofficialurbandictionary.ui.favorites.FavoritesMvpView;
import com.github.batulovandrey.unofficialurbandictionary.ui.favorites.FavoritesPresenter;
import com.github.batulovandrey.unofficialurbandictionary.ui.main.MainMvpPresenter;
import com.github.batulovandrey.unofficialurbandictionary.ui.main.MainMvpView;
import com.github.batulovandrey.unofficialurbandictionary.ui.main.MainPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class DataModule {

    @Provides
    @Singleton
    RealmManager provideRealmManager(Context context) {
        return new RealmManager(context);
    }

    @Provides
    @Singleton
    PopularWords providePopularWords(Context context) {
        return new PopularWords(context);
    }

    @Provides
    @Singleton
    MainMvpPresenter<MainMvpView> provideMainPresenter(DataManager dataManager,
                                                       CompositeDisposable disposable) {
        return new MainPresenter<>(dataManager, disposable);
    }

    @Provides
    @Singleton
    DetailMvpPresenter<DetailMvpView> provideDetailPresenter(DataManager dataManager,
                                                             CompositeDisposable disposable) {
        return new DetailPresenter<>(dataManager, disposable);
    }

    @Provides
    @Singleton
    FavoritesMvpPresenter<FavoritesMvpView> provideFavoritesPresenter(DataManager dataManager,
                                                                      CompositeDisposable disposable) {
        return new FavoritesPresenter<>(dataManager, disposable);
    }

    @Provides
    CompositeDisposable provideDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(Context context) {
        return new AppDbHelper(context);
    }

    @Provides
    @Singleton
    NetworkHelper provideNetworkHelper() {
        return new AppNetworkHelper();
    }

    @Provides
    @Singleton
    DataManager provideDataManager(DbHelper dbHelper,
                                   NetworkHelper networkHelper) {
        return new AppDataManager(dbHelper, networkHelper);
    }
}