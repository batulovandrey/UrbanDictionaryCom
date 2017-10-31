package com.github.batulovandrey.unofficialurbandictionary.presenter;

import java.util.ArrayList;

/**
 * @author Andrey Batulov on 31/10/2017
 */

public interface PopularWordsPresenter {

    ArrayList<String> getAlphabet();

    ArrayList<String> getWords(String letter);
}