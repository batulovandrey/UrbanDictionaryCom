package com.github.batulovandrey.urbandictionarycom.service;

import com.github.batulovandrey.urbandictionarycom.bean.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public interface UrbanDictionaryService {

    @GET("/define")
    @Headers({"X-Mashape-Key: KYRWCyThbMmsh4z6Ep7dNEa61bDip1asxIkjsnfhWID3QOEUk4", "Accept: text/plain"})
    public Call<BaseResponse> getDefine(@Query("term") String term);
}