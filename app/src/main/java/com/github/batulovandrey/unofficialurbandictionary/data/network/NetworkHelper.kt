package com.github.batulovandrey.unofficialurbandictionary.data.network

import com.github.batulovandrey.unofficialurbandictionary.bean.BaseResponse
import io.reactivex.Single

interface NetworkHelper {

    fun getData(query: String): Single<BaseResponse>
}