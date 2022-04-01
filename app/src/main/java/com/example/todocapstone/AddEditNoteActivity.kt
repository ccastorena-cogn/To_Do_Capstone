package com.example.todocapstone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    var TAG = AddEditNoteActivity::class.java.simpleName
    lateinit var noteTitleEdit : EditText
    lateinit var noteDescriptionEdit : EditText
    lateinit var addUpdateBtn : Button

    lateinit var viewModel : NoteViewModel
    var noteID = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        noteTitleEdit = findViewById(R.id.idEditNoteTitle)
        noteDescriptionEdit = findViewById(R.id.idEditNoteDescription)
        addUpdateBtn = findViewById(R.id.idBtnAddUpdate)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if(noteType.equals("Edit")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID", -1)
            addUpdateBtn.setText("Update Note")
            noteTitleEdit.setText(noteTitle)
            noteDescriptionEdit.setText(noteDescription)

        } else {
            addUpdateBtn.setText("Save Note")
        }
        addUpdateBtn.setOnClickListener{
            val noteTitle = noteTitleEdit.text.toString()
            val noteDescription = noteDescriptionEdit.text.toString()

            if(noteType.equals("Edit")){
                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate:String = sdf.format(Date())
                    val updateNote = Note(noteTitle, noteDescription, currentDate)
                    updateNote.id = noteID
                    viewModel.updateNote(updateNote)
                    updateToFirestore(Note(noteTitle, noteDescription, currentDate))
                    Toast.makeText(this, "$noteTitle Updated", Toast.LENGTH_LONG).show()

                }
            }else {
                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate:String = sdf.format(Date())
                    viewModel.addNote(Note(noteTitle, noteDescription, currentDate))
                    addToFirestore(Note(noteTitle, noteDescription, currentDate))
                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }

    private fun addToFirestore(note: Note) {
        var db = Firebase.firestore
        val note = hashMapOf(
            "title" to note.noteTitle,
            "description" to note.noteDescription,
            "timestamp" to note.timeStamp
        )
// Add a new document with a generated ID
        db.collection("notes")
            .add(note)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
    private fun updateToFirestore(note: Note) {
        var db = Firebase.firestore
        val updateNote = hashMapOf(
            "title" to note.noteTitle,
            "description" to note.noteDescription,
            "timestamp" to note.timeStamp
        )
        db.collection("notes").document()

    }


}