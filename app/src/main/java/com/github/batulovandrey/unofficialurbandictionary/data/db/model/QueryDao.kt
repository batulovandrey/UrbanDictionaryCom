package com.github.batulovandrey.unofficialurbandictionary.data.db.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface QueryDao {

    @Query("select * from userQuery")
    fun getAll(): List<SavedUserQuery>

    @Insert(onConflict = REPLACE)
    fun insert(savedUserQuery: SavedUserQuery)

    @Query("delete from userQuery")
    fun deleteAll()

    @Delete
    fun delete(savedUserQuery: SavedUserQuery)
}