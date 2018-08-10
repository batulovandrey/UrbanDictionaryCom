package com.github.batulovandrey.unofficialurbandictionary.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition

@Dao
interface DefinitionDao {

    @Query("select * from definitions")
    fun getAll(): List<Definition>

    @Insert(onConflict = REPLACE)
    fun insert(definition: Definition)

    @Query("delete from definitions")
    fun deleteAll()

    @Query("select * from definitions where favorite = :favorite")
    fun getAllFavorites(favorite: Int = 1): List<Definition>

    @Delete
    fun delete(definition: Definition)

    @Query("delete from definitions where favorite = :favorite")
    fun deleteAllFavorites(favorite: Int = 1)
}