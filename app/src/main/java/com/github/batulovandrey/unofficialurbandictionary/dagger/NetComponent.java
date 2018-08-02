package com.github.batulovandrey.unofficialurbandictionary.dagger;

import com.github.batulovandrey.unofficialurbandictionary.data.network.AppNetworkHelper;
import com.github.batulovandrey.unofficialurbandictionary.model.DetailModel;
import com.github.batulovandrey.unofficialurbandictionary.model.FavoritesModel;
import com.github.batulovandrey.unofficialurbandictionary.model.PopularWordsModel;
import com.github.batulovandrey.unofficialurbandictionary.view.SearchFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Andrey Batulov on 30/10/2017
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {

    void inject(DetailModel model);

    void inject(FavoritesModel model);

    void inject(PopularWordsModel model);

    void inject(AppNetworkHelper appNetworkHelper);

    void inject(SearchFragment fragment);
}