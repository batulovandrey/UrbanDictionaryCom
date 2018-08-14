package com.github.batulovandrey.unofficialurbandictionary.api

import com.github.batulovandrey.unofficialurbandictionary.data.bean.BaseResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Andrey Batulov on 23/12/2017
 */

interface UrbanDictionaryService {

    @GET("/v0/define")
    fun getDefineRx(@Query("term") term: String): Single<BaseResponse>
}