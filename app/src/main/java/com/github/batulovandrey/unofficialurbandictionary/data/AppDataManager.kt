package com.github.batulovandrey.unofficialurbandictionary.data

import com.github.batulovandrey.unofficialurbandictionary.bean.BaseResponse
import com.github.batulovandrey.unofficialurbandictionary.data.db.DbHelper
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.SavedUserQuery
import com.github.batulovandrey.unofficialurbandictionary.data.network.NetworkHelper
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(private val dbHelper: DbHelper,
                                         private val networkHelper: NetworkHelper) : DataManager {

    private lateinit var currentListOfDefinition: List<Definition>
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

    override fun deleteDefinition(definition: Definition): Completable {
        return dbHelper.deleteDefinition(definition)
    }

    override fun deleteDefinitionFromFavorites(definition: Definition): Completable {
        return dbHelper.deleteDefinitionFromFavorites(definition)
    }

    override fun deleteAllDefinitions(): Completable {
        return dbHelper.deleteAllDefinitions()
    }

    override fun getQueries(): Observable<List<SavedUserQuery>> {
        return dbHelper.getQueries()
    }

    override fun deleteAllQueries(): Completable {
        return dbHelper.deleteAllQueries()
    }

    override fun getDefinitions(): Observable<List<Definition>> {
        return dbHelper.getDefinitions()
    }

    override fun getFavoritesDefinitions(): Observable<List<Definition>> {
        return dbHelper.getFavoritesDefinitions()
    }

    override fun saveDefinition(definition: Definition): Completable {
        return dbHelper.saveDefinition(definition)
    }

    override fun filterQueries(text: String): Observable<List<SavedUserQuery>> {
        return dbHelper.filterQueries(text)
    }

    override fun deleteQuery(query: SavedUserQuery): Completable {
        return dbHelper.deleteQuery(query)
    }

    override fun getData(query: String): Single<BaseResponse> {
        return networkHelper.getData(query)
    }

    override fun saveQuery(savedUserQuery: SavedUserQuery): Completable {
        return dbHelper.saveQuery(savedUserQuery);
    }

    override fun saveCurrentListOfDefinition(list: List<Definition>?) {
        list?.let { currentListOfDefinition = it }
    }

    override fun getSavedListOfDefinition(): List<Definition> {
        return currentListOfDefinition
    }

    override fun deleteFavoritesDefinitions(): Completable {
        return dbHelper.deleteFavoritesDefinitions()
    }
}