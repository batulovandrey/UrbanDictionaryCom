package com.github.batulovandrey.unofficialurbandictionary.data.db

import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.SavedUserQuery
import io.reactivex.Completable
import io.reactivex.Observable

interface DbHelper {

    fun getDefinitions(): Observable<List<Definition>>

    fun getFavoritesDefinitions(): Observable<List<Definition>>

    fun saveDefinition(definition: Definition): Completable

    fun saveDefinitionToFavorites(definition: Definition): Completable

    fun deleteDefinition(definition: Definition): Completable

    fun deleteDefinitionFromFavorites(definition: Definition): Completable

    fun deleteAllDefinitions(): Completable

    fun getQueries(): Observable<List<SavedUserQuery>>

    fun filterQueries(text: String): Observable<List<SavedUserQuery>>

    fun deleteQuery(query: SavedUserQuery): Completable

    fun deleteAllQueries(): Completable

    fun saveQuery(savedUserQuery: SavedUserQuery): Completable
}