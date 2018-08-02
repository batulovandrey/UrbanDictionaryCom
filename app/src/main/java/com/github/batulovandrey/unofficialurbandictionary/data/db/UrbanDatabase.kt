package com.github.batulovandrey.unofficialurbandictionary.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.QueryDao
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.SavedUserQuery

@Database(entities = arrayOf(Definition::class, SavedUserQuery::class), version = 1)
abstract class UrbanDatabase: RoomDatabase() {

    abstract fun definitionDataDao(): DefinitionDao
    abstract fun queryDataDao(): QueryDao

    companion object {

        private var INSTANCE: UrbanDatabase? = null

        fun getInstance(context: Context): UrbanDatabase? {
            if (INSTANCE == null) {
                synchronized(UrbanDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            UrbanDatabase::class.java, "urban.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroy() {
            INSTANCE == null
        }
    }
}