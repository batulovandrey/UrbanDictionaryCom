package com.github.batulovandrey.unofficialurbandictionary.service

import com.github.batulovandrey.unofficialurbandictionary.bean.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * @author Andrey Batulov on 23/12/2017
 */

interface UrbanDictionaryService {

    @GET("/v0/define")
    fun getDefineRx(@Query("term") term: String): Observable<BaseResponse>
}