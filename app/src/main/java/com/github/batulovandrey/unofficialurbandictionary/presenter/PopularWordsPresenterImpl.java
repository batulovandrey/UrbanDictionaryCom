package com.github.batulovandrey.unofficialurbandictionary.presenter;

import com.github.batulovandrey.unofficialurbandictionary.model.PopularWordsModel;
import com.github.batulovandrey.unofficialurbandictionary.view.PopularWordsView;

import java.util.ArrayList;

/**
 * @author Andrey Batulov on 31/10/2017
 */

public class PopularWordsPresenterImpl implements PopularWordsPresenter {

    private final PopularWordsModel mPopularWordsModel;
    private final PopularWordsView mPopularWordsView;

    public PopularWordsPresenterImpl(PopularWordsView popularWordsView) {
        mPopularWordsView = popularWordsView;
        mPopularWordsModel = new PopularWordsModel(this);
    }

    @Override
    public ArrayList<String> getAlphabet() {
        return mPopularWordsModel.getAlphabet();
    }

    @Override
    public ArrayList<String> getWords(String letter) {
        return mPopularWordsModel.getWords(letter);
    }
}