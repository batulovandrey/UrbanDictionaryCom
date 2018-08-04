package com.github.batulovandrey.unofficialurbandictionary.utils

import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse
import com.github.batulovandrey.unofficialurbandictionary.bean.UserQuery
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.SavedUserQuery
import io.realm.RealmResults

fun List<DefinitionResponse>.convertToDefinitionList(): List<Definition> {

    val result = ArrayList<Definition>()

    for (item in this) {

        val definition = Definition(null,
                item.definition,
                item.permalink,
                item.thumbsUp,
                item.thumbsDown,
                item.author,
                item.word,
                item.example)

        result.add(definition)

    }

    return result
}

fun RealmResults<DefinitionResponse>.convertToDefinitionList(): List<Definition> {
    val result = ArrayList<Definition>()

    for (item in this) {
        val definition = Definition(null,
                item.definition ?: "",
                item.permalink ?: "",
                item.thumbsUp,
                item.thumbsDown,
                item.author ?: "",
                item.word ?: "",
                item.example ?: "")

        result.add(definition)
    }

    return result
}

fun RealmResults<UserQuery>.convertToUserQueriesList()
        : List<SavedUserQuery> {
    val result = ArrayList<SavedUserQuery>()

    for (item in this) {
        val userQuery = SavedUserQuery(null, item.query)
        result.add(userQuery)
    }

    return result
}