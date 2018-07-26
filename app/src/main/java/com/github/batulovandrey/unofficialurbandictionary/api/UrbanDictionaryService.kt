package com.github.batulovandrey.unofficialurbandictionary.api

import com.github.batulovandrey.unofficialurbandictionary.bean.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Single

/**
 * @author Andrey Batulov on 23/12/2017
 */

interface UrbanDictionaryService {

    @GET("/v0/define")
    fun getDefineRx(@Query("term") term: String): Single<BaseResponse>

    @GET("/v0/define")
    fun getDefine(@Query("term") term: String): Call<BaseResponse>
}