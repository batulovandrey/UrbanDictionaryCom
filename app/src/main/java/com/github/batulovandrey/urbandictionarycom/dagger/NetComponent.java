package com.github.batulovandrey.urbandictionarycom.dagger;

import com.github.batulovandrey.urbandictionarycom.model.DetailModel;
import com.github.batulovandrey.urbandictionarycom.model.FavoritesModel;
import com.github.batulovandrey.urbandictionarycom.model.MainModel;

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