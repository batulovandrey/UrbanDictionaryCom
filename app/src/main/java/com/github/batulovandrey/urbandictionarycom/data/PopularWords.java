package com.github.batulovandrey.urbandictionarycom.data;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Andrey Batulov on 27/10/2017
 */

public class PopularWords {

    private Map<Character, List<String>> dictionary;

    public PopularWords(Context context) {
        dictionary = new TreeMap<>();
        fillDictionary(context);
    }

    public Map<Character, List<String>> getDictionary() {
        return dictionary;
    }

    private void fillDictionary(Context context) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        try {
            for (char anAlphabet : alphabet) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(anAlphabet + ".txt")));
                String temp;
                List<String> tempList = new ArrayList<>();
                while ((temp = reader.readLine()) != null) {
                    tempList.add(temp);
                }
                dictionary.put(anAlphabet, tempList);
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}