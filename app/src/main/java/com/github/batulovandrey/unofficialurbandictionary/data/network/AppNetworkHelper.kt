package com.github.batulovandrey.unofficialurbandictionary.data.network

import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp
import com.github.batulovandrey.unofficialurbandictionary.api.UrbanDictionaryService
import com.github.batulovandrey.unofficialurbandictionary.data.bean.BaseResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AppNetworkHelper : NetworkHelper {

    @Inject
    lateinit var service: UrbanDictionaryService

    init {
        UrbanDictionaryApp.netComponent.inject(this)
    }

    override fun getData(query: String): Single<BaseResponse> {
        return service.getDefineRx(query)
                .subscribeOn(Schedulers.io())
    }

    override fun getRandom(): Single<BaseResponse> {
        return service.getRandomDefine()
                .subscribeOn(Schedulers.io())
    }
}