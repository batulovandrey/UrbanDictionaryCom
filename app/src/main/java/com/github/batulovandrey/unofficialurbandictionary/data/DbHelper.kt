package com.github.batulovandrey.unofficialurbandictionary.data

import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse
import com.github.batulovandrey.unofficialurbandictionary.bean.UserQuery
import io.reactivex.Observable

interface DbHelper {

    fun getDefinitions(): Observable<List<DefinitionResponse>>

    fun saveDefinition(definitionResponse: DefinitionResponse)

    fun filterQueries(text: String): Observable<List<UserQuery>>

    fun deleteQuery(query: UserQuery)
}