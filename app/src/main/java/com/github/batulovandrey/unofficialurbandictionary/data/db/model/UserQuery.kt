package com.github.batulovandrey.unofficialurbandictionary.data.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "userQuery")
data class UserQuery(@PrimaryKey(autoGenerate = true) var id: Long?,
                     @ColumnInfo(name = "text") var text: String)