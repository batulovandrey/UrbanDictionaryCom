package com.github.batulovandrey.unofficialurbandictionary.data.db

import android.content.Context
import com.github.batulovandrey.unofficialurbandictionary.dagger.ApplicationContext
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.SavedUserQuery
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDbHelper @Inject constructor(@ApplicationContext context: Context) : DbHelper {

    private val urbanDatabase = UrbanDatabase.getInstance(context)
    private val definitionDataDao = urbanDatabase?.definitionDataDao()
    private val queryDataDao = urbanDatabase?.queryDataDao()

    override fun getDefinitions(): Flowable<List<Definition>>? {
        return definitionDataDao?.getAll()
    }

    override fun saveDefinition(definition: Definition): Completable {
        return Completable.fromAction { definitionDataDao?.insert(definition) }
    }

    override fun saveDefinitionToFavorites(definition: Definition): Completable {
        return Completable.fromAction {
            definition.favorite = 1
            definitionDataDao?.insert(definition)
        }
    }

    override fun deleteDefinition(definition: Definition) {
        definitionDataDao?.delete(definition)
    }

    /**
     * There is no need to delete the definition at all,
     * so just change the flag and update the record in db
     */
    override fun deleteDefinitionFromFavorites(definition: Definition) {
        definition.favorite = 0
        definitionDataDao?.insert(definition)
    }

    override fun deleteAllDefinitions() {
        definitionDataDao?.deleteAll()
    }

    override fun getQueries(): Observable<List<SavedUserQuery>> {
        return Observable.fromCallable { queryDataDao?.getAll() }
    }

    override fun filterQueries(text: String): Observable<List<SavedUserQuery>> {
        return Observable.fromCallable { queryDataDao?.getAll()?.filter { it.text.contains(text) } }
    }

    override fun deleteQuery(query: SavedUserQuery) {
        queryDataDao?.delete(query)
    }

    override fun deleteAllQueries() {
        queryDataDao?.deleteAll()
    }

    override fun saveQuery(savedUserQuery: SavedUserQuery) {
        queryDataDao?.insert(savedUserQuery)
    }
}