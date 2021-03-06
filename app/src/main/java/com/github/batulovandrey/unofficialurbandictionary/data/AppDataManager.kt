package com.github.batulovandrey.unofficialurbandictionary.data

import com.github.batulovandrey.unofficialurbandictionary.data.bean.BaseResponse
import com.github.batulovandrey.unofficialurbandictionary.data.db.DbHelper
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.SavedUserQuery
import com.github.batulovandrey.unofficialurbandictionary.data.network.NetworkHelper
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class AppDataManager @Inject constructor(private val dbHelper: DbHelper,
                                         private val networkHelper: NetworkHelper) : DataManager {

    private val currentMapOfDefinition = ArrayList<Definition>()
    private lateinit var activeDefinition: Definition

    override fun setActiveDefinition(definition: Definition) {
        activeDefinition = definition
    }

    override fun getActiveDefinition(): Definition? {
        return if (::activeDefinition.isInitialized)
            activeDefinition
        else null
    }

    override fun saveDefinitionToFavorites(definition: Definition): Completable {
        return dbHelper.saveDefinitionToFavorites(definition)
    }

    override fun getDefinitionById(id: Long?): Single<Definition?> {
        return dbHelper.getDefinitionById(id)
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

    override fun deleteCachedDefinitions(): Completable {
        return dbHelper.deleteCachedDefinitions()
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

    override fun saveDefinition(definition: Definition): Single<Long> {
        return dbHelper.saveDefinition(definition)
    }

    override fun getAllQueries(): Observable<List<SavedUserQuery>> {
        return dbHelper.getAllQueries()
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

    override fun getSavedListOfDefinition(): List<Definition> {
        return currentMapOfDefinition
    }

    override fun deleteFavoritesDefinitions(): Completable {
        return dbHelper.deleteFavoritesDefinitions()
    }

    override fun putDefinitionToSavedList(definition: Definition) {
        currentMapOfDefinition.add(definition)
    }

    override fun clearMap() {
        currentMapOfDefinition.clear()
    }

    override fun getRandom(): Single<BaseResponse> {
        return networkHelper.getRandom()
    }
}