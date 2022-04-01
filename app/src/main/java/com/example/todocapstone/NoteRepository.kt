package com.example.todocapstone

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import java.util.concurrent.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

    suspend fun delete(note: Note){
        noteDao.delete(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }
}