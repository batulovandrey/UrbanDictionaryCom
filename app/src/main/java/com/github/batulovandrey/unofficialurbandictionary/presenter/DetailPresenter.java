package com.github.batulovandrey.unofficialurbandictionary.presenter;

import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse;

/**
 * @author Andrey Batulov on 29/10/2017
 */

public interface DetailPresenter {

    void setImageResToImageView(DefinitionResponse definition);

    DefinitionResponse getDefinitionByDefIf(long defId);

    boolean isFavoriteDefinition(DefinitionResponse definition);

    void isAddedToFav(long defId);
}