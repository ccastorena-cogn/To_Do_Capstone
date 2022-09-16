package com.example.todocapstone

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), NoteClickDeleteInterface, NoteClickInterface {

    lateinit var notesRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notesRV = findViewById(R.id.idRVNotes)
        addFAB = findViewById(R.id.idFABAddNote)

        notesRV.layoutManager = LinearLayoutManager(this)

        val noteRVAdapter = NoteRVAdapter(this, this, this)
        notesRV.adapter = noteRVAdapter
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                noteRVAdapter.updateList(it)
            }
        })
        addFAB.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteID", note.id)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
        deleteFromFirestore(note)
            Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
        Log.i("Info", "Success")
    }

    private fun deleteFromFirestore(note: Note) {
        var db = Firebase.firestore
        // var noteId = db.collection("notes").document().id

        var NOTEID = "nlSVPJnCXYyTfL0e4MNF"
        var NEWID = Firebase.firestore.collection("notes").document().get().toString()

        /*val note = hashMapOf(
            "title" to note.noteTitle,
            "description" to note.noteDescription,
            "timestamp" to note.timeStamp
        )*/

        /*db.collection("notes").addSnapshotListener{
            snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen Failed", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {

                val documents = snapshot.documents
                documents.forEach{
                    val note = it.toObject(Note::class.java)
                    if (note != null) {
                        noteId = it.id
                    }
                }
            }
        }*/

        db.collection("notes").document(NOTEID)
            .delete()
    }

}


