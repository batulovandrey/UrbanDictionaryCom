package com.github.batulovandrey.unofficialurbandictionary.data

import android.content.Context
import com.github.batulovandrey.unofficialurbandictionary.bean.BaseResponse
import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse
import com.github.batulovandrey.unofficialurbandictionary.bean.UserQuery
import com.github.batulovandrey.unofficialurbandictionary.data.network.NetworkHelper
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class AppDataManager(val context: Context,
                     val dbHelper: DbHelper,
                     val networkHelper: NetworkHelper): DataManager {

    override fun getDefinitions(): Observable<List<DefinitionResponse>> {
        return dbHelper.getDefinitions()
    }

    override fun saveDefinition(definitionResponse: DefinitionResponse) {
        dbHelper.saveDefinition(definitionResponse)
    }

    override fun filterQueries(text: String): Observable<List<UserQuery>> {
        return dbHelper.filterQueries(text)
    }

    override fun deleteQuery(query: UserQuery) {
        dbHelper.deleteQuery(query)
    }

    override fun getData(query: String): Single<BaseResponse> {
        return networkHelper.getData(query)
    }
}