package com.github.batulovandrey.unofficialurbandictionary.data

import com.github.batulovandrey.unofficialurbandictionary.data.db.DbHelper
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.data.network.NetworkHelper

interface DataManager : DbHelper, NetworkHelper {

    fun setActiveDefinition(definition: Definition)

    fun getActiveDefinition(): Definition?

    fun putDefinitionToMap(id: Long, definition: Definition)

    fun clearMap()

    fun getDefinitionId(definition: Definition): Long

    fun getSavedListOfDefinition(): List<Definition>
}