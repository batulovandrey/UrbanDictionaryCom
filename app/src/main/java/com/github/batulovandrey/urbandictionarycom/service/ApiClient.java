package com.github.batulovandrey.urbandictionarycom.service;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class ApiClient {

    private static final String BASE_URL = "https://mashape-community-urban-dictionary.p.mashape.com";
    private static Retrofit sRetrofit;

    private ApiClient() {
        throw new IllegalStateException("can't create the object");
    }

    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }
}