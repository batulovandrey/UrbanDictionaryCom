package com.github.batulovandrey.unofficialurbandictionary.model;

import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp;
import com.github.batulovandrey.unofficialurbandictionary.data.PopularWords;
import com.github.batulovandrey.unofficialurbandictionary.presenter.PopularWordsPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author Andrey Batulov on 31/10/2017
 */

public class PopularWordsModel {

    @Inject
    PopularWords mWords;

    private final PopularWordsPresenter mPopularWordsPresenter;
    private Map<String, List<String>> mDictionary;

    public PopularWordsModel(PopularWordsPresenter popularWordsPresenter) {
        UrbanDictionaryApp.getNetComponent().inject(this);
        mPopularWordsPresenter = popularWordsPresenter;
        mDictionary = mWords.getDictionary();
    }

    public ArrayList<String> getAlphabet() {
        ArrayList<String> alphabet = new ArrayList<>();
        for (Map.Entry<String, List<String>> map : mDictionary.entrySet()) {
            alphabet.add(map.getKey());
        }
        return alphabet;
    }

    public ArrayList<String> getWords(String letter) {
        ArrayList<String> words = new ArrayList<>();
        for (Map.Entry<String, List<String>> map : mDictionary.entrySet()) {
            if (map.getKey().equals(letter)) {
                words.addAll(map.getValue());
            }
        }
        return words;
    }
}