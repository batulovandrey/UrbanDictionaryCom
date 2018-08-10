package com.github.batulovandrey.unofficialurbandictionary.dagger;

import com.github.batulovandrey.unofficialurbandictionary.data.network.AppNetworkHelper;
import com.github.batulovandrey.unofficialurbandictionary.model.PopularWordsModel;
import com.github.batulovandrey.unofficialurbandictionary.service.MigrateService;
import com.github.batulovandrey.unofficialurbandictionary.ui.detail.DetailFragment;
import com.github.batulovandrey.unofficialurbandictionary.ui.favorites.FavoritesFragment;
import com.github.batulovandrey.unofficialurbandictionary.ui.main.MainSearchFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Andrey Batulov on 30/10/2017
 */

@Singleton
@Component(modules = {AppModule.class, DataModule.class, NetModule.class})
public interface NetComponent  {

    void inject(FavoritesFragment fragment);

    void inject(PopularWordsModel model);

    void inject(AppNetworkHelper appNetworkHelper);

    void inject(MainSearchFragment fragment);

    void inject(DetailFragment fragment);

    void inject(MigrateService service);
}