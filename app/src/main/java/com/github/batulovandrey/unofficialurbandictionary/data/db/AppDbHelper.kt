package com.github.batulovandrey.unofficialurbandictionary.data.db

import android.content.Context
import com.github.batulovandrey.unofficialurbandictionary.dagger.ApplicationContext
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.SavedUserQuery
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDbHelper @Inject constructor(@ApplicationContext context: Context) : DbHelper {

    private val urbanDatabase = UrbanDatabase.getInstance(context)
    private val definitionDataDao = urbanDatabase?.definitionDataDao()
    private val queryDataDao = urbanDatabase?.queryDataDao()

    override fun getDefinitions(): Observable<List<Definition>> {
        return Observable.fromCallable { definitionDataDao?.getAll() }
    }

    override fun getDefinitionById(id: Long?): Single<Definition?> {
        return Single.fromCallable { definitionDataDao?.getDefinitionById(id) }
    }

    override fun getFavoritesDefinitions(): Observable<List<Definition>> {
        return Observable.fromCallable { definitionDataDao?.getAllFavorites() }
    }

    override fun saveDefinition(definition: Definition): Single<Long> {
        return Single.fromCallable { definitionDataDao?.insert(definition) }
    }

    override fun saveDefinitionToFavorites(definition: Definition): Completable {
        return Completable.fromAction {
            definition.favorite = 1
            definitionDataDao?.insert(definition)
        }
    }

    override fun deleteDefinition(definition: Definition): Completable {
        return Completable.fromAction { definitionDataDao?.delete(definition) }
    }

    /**
     * There is no need to delete the definition at all,
     * so just change the flag and update the record in db
     */
    override fun deleteDefinitionFromFavorites(definition: Definition): Completable {
        return Completable.fromAction {
            definition.favorite = 0
            definitionDataDao?.insert(definition)
        }
    }

    override fun deleteAllDefinitions(): Completable {
        return Completable.fromAction { definitionDataDao?.deleteAll() }
    }

    override fun getQueries(): Observable<List<SavedUserQuery>> {
        return Observable.fromCallable { queryDataDao?.getAll() }
    }

    override fun getAllQueries(): Observable<List<SavedUserQuery>> {
        return Observable.fromCallable { queryDataDao?.getAll() }
    }

    override fun deleteQuery(query: SavedUserQuery): Completable {
        return Completable.fromAction { queryDataDao?.delete(query) }
    }

    override fun deleteAllQueries(): Completable {
        return Completable.fromAction { queryDataDao?.deleteAll() }
    }

    override fun saveQuery(savedUserQuery: SavedUserQuery): Completable {
        return Completable.fromAction { queryDataDao?.insert(savedUserQuery) }
    }

    override fun deleteFavoritesDefinitions(): Completable {
        return Completable.fromAction { definitionDataDao?.deleteAllFavorites() }
    }

    override fun deleteCachedDefinitions(): Completable {
        return Completable.fromAction{ definitionDataDao?.deleteCachedDefinitions() }
    }
}