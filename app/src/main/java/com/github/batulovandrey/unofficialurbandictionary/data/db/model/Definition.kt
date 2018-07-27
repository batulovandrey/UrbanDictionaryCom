package com.github.batulovandrey.unofficialurbandictionary.data.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "definitions")
data class Definition(@PrimaryKey(autoGenerate = true) var id: Long?,
                      @ColumnInfo(name = "definition") var definition: String,
                      @ColumnInfo(name = "permalink") var permalink: String,
                      @ColumnInfo(name = "thumbs_up") var thumbsUp: Int,
                      @ColumnInfo(name = "thumbs_down") var thumbsDown: Int,
                      @ColumnInfo(name = "author") var author: String,
                      @ColumnInfo(name = "word") var word: String,
                      @ColumnInfo(name = "example") var example: String,
                      @ColumnInfo(name = "favorite") var favorite: Int = 0)