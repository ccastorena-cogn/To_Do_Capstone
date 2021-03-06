package com.example.todocapstone

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
class Note(
    @ColumnInfo(name = "title") val noteTitle: String,
    @ColumnInfo(name="description") val noteDescription: String,
    @ColumnInfo(name= "timestamp") val timeStamp: String
    ) {

    @PrimaryKey(autoGenerate = true)

    var id = 0

    fun note() {
    }

    fun getTitle(): String {
        return noteTitle
    }

    fun getDescription(): String {
        return noteDescription
    }

    fun getTime(): String {
        return timeStamp
    }

}

