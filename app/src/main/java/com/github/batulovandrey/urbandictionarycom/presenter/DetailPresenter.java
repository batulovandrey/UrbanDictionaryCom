package com.github.batulovandrey.urbandictionarycom.presenter;

import android.content.Context;

import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;

import io.realm.Realm;

/**
 * @author Andrey Batulov on 29/10/2017
 */

public interface DetailPresenter {

    void setImageResToImageView(DefinitionResponse definition);

    DefinitionResponse getDefinitionByDefIf(long defId);

    boolean isFavoriteDefinition(DefinitionResponse definition);

    void isAddedToFav(long defId);

    Context getContext();

    Realm getRealm(String nameDb);
}