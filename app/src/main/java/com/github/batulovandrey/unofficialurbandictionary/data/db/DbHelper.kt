package com.github.batulovandrey.unofficialurbandictionary.data.db

import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.SavedUserQuery
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface DbHelper {

    fun getDefinitions(): Flowable<List<Definition>>?

    fun saveDefinition(definition: Definition): Completable

    fun saveDefinitionToFavorites(definition: Definition): Completable

    fun deleteDefinition(definition: Definition)

    fun deleteDefinitionFromFavorites(definition: Definition)

    fun deleteAllDefinitions()

    fun getQueries(): Observable<List<SavedUserQuery>>

    fun filterQueries(text: String): Observable<List<SavedUserQuery>>

    fun deleteQuery(query: SavedUserQuery)

    fun deleteAllQueries()

    fun saveQuery(savedUserQuery: SavedUserQuery)
}