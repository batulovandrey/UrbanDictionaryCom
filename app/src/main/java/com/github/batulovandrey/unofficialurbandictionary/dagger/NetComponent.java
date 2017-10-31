package com.github.batulovandrey.unofficialurbandictionary.dagger;

import com.github.batulovandrey.unofficialurbandictionary.model.DetailModel;
import com.github.batulovandrey.unofficialurbandictionary.model.FavoritesModel;
import com.github.batulovandrey.unofficialurbandictionary.model.MainModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Andrey Batulov on 30/10/2017
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {

    void inject(MainModel model);

    void inject(DetailModel model);

    void inject(FavoritesModel model);
}