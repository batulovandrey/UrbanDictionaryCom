package com.github.batulovandrey.unofficialurbandictionary.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import io.reactivex.Flowable

@Dao
interface DefinitionDao {

    @Query("select * from definitions")
    fun getAll(): Flowable<List<Definition>>

    @Insert(onConflict = REPLACE)
    fun insert(definition: Definition)

    @Query("delete from definitions")
    fun deleteAll()

    @Query("select * from definitions where favorite = 1")
    fun getAllFavorites(): List<Definition>

    @Delete
    fun delete(definition: Definition)
}