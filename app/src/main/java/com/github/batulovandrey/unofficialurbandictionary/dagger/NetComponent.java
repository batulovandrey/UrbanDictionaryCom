package com.github.batulovandrey.unofficialurbandictionary.dagger;

import com.github.batulovandrey.unofficialurbandictionary.data.WordsRepository;
import com.github.batulovandrey.unofficialurbandictionary.data.network.AppNetworkHelper;
import com.github.batulovandrey.unofficialurbandictionary.service.MigrateService;
import com.github.batulovandrey.unofficialurbandictionary.ui.detail.DetailFragment;
import com.github.batulovandrey.unofficialurbandictionary.ui.favorites.FavoritesFragment;
import com.github.batulovandrey.unofficialurbandictionary.ui.main.MainSearchFragment;
import com.github.batulovandrey.unofficialurbandictionary.ui.top.TopWordsFragment;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Andrey Batulov on 30/10/2017
 */

@Singleton
@Component(modules = {AppModule.class, DataModule.class, NetModule.class})
public interface NetComponent  {

    void inject(FavoritesFragment fragment);

    void inject(AppNetworkHelper appNetworkHelper);

    void inject(MainSearchFragment fragment);

    void inject(DetailFragment fragment);

    void inject(MigrateService service);

    void inject(@NotNull WordsRepository repository);

    void inject(@NotNull TopWordsFragment fragment);
}