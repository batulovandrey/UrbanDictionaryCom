package com.github.batulovandrey.unofficialurbandictionary.data

import android.content.Context
import com.github.batulovandrey.unofficialurbandictionary.bean.BaseResponse
import com.github.batulovandrey.unofficialurbandictionary.dagger.ApplicationContext
import com.github.batulovandrey.unofficialurbandictionary.data.db.DbHelper
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.SavedUserQuery
import com.github.batulovandrey.unofficialurbandictionary.data.network.NetworkHelper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(@ApplicationContext val context: Context,
                                         val dbHelper: DbHelper,
                                         val networkHelper: NetworkHelper) : DataManager {

    private lateinit var activeDefinition: Definition

    override fun setActiveDefinition(definition: Definition) {
        activeDefinition = definition
    }

    override fun getActiveDefinition(): Definition? {
        return activeDefinition
    }

    override fun saveDefinitionToFavorites(definition: Definition): Completable {
        return dbHelper.saveDefinitionToFavorites(definition)
    }

    override fun deleteDefinition(definition: Definition) {
        dbHelper.deleteDefinition(definition)
    }

    override fun deleteDefinitionFromFavorites(definition: Definition) {
        dbHelper.deleteDefinitionFromFavorites(definition)
    }

    override fun deleteAllDefinitions() {
        dbHelper.deleteAllDefinitions()
    }

    override fun getQueries(): Observable<List<SavedUserQuery>> {
        return dbHelper.getQueries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    override fun deleteAllQueries() {
        dbHelper.deleteAllQueries()
    }

    override fun getDefinitions(): Flowable<List<Definition>>? {
        return dbHelper.getDefinitions()
    }

    override fun saveDefinition(definition: Definition): Completable {
        return dbHelper.saveDefinition(definition)
    }

    override fun filterQueries(text: String): Observable<List<SavedUserQuery>> {
        return dbHelper.filterQueries(text)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    override fun deleteQuery(query: SavedUserQuery) {
        dbHelper.deleteQuery(query)
    }

    override fun getData(query: String): Single<BaseResponse> {
        return networkHelper.getData(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    override fun saveQuery(savedUserQuery: SavedUserQuery) {
        dbHelper.saveQuery(savedUserQuery);
    }
}