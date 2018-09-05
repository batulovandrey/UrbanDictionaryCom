package com.github.batulovandrey.unofficialurbandictionary.data.network

import com.github.batulovandrey.unofficialurbandictionary.data.bean.BaseResponse
import io.reactivex.Flowable

interface NetworkHelper {

    fun getData(query: String): Flowable<BaseResponse>
}