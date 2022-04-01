package com.example.todocapstone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import org.w3c.dom.Text
import java.lang.String


class NoteRVAdapter(
//    val context: FirestoreRecyclerOptions<Note>,
    val noteClickDeleteInterface: NoteClickDeleteInterface,
    val noteClickInterface: NoteClickInterface,
    var  options: FirebaseRecyclerOptions<Note>
) :
    FirebaseRecyclerAdapter<Note?, NoteRVAdapter.ViewHolder?>(options) {

//    inner class NoteRVAdapter : FirestoreRecyclerAdapter<Note?, com.example.todocapstone.NoteRVAdapter.ViewHolder?>() {
        fun NoteRVAdapter(@NonNull options: FirestoreRecyclerOptions<Note?>?) {
            super(options)
        }

        @NonNull
        override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
            val v: View = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note_rv_item,
                parent, false
            )
            return ViewHolder(v)
        }

        override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int, @NonNull model: Note) {
            holder.noteTV.setText(model.getTitle())
            holder.descriptionTV.setText(model.getDescription())
            holder.timeTV.setText("Last Updated: " + allNotes.get(position).timeStamp)
//            holder.textViewPriority.setText(String.valueOf(model.getPriority()))

            holder.deleteIV.setOnClickListener {
                noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
            }

            holder.itemView.setOnClickListener {
                noteClickInterface.onNoteClick(allNotes.get(position))
            }

        }
//    }

    private val allNotes = ArrayList<Note>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTV: TextView = itemView.findViewById<TextView>(R.id.idTVNoteTitle)
        val timeTV: TextView = itemView.findViewById<TextView>(R.id.idTVTimeStamp)
        val deleteIV: ImageView = itemView.findViewById<ImageView>(R.id.idIVDelete)
        val descriptionTV: EditText = itemView.findViewById<EditText>(R.id.idEditNoteDescription)
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_rv_item, parent, false)
//        return ViewHolder(itemView)
//    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.noteTV.setText(allNotes.get(position).noteTitle)
//        holder.timeTV.setText("Last Updated: " + allNotes.get(position).timeStamp)
//
//        holder.deleteIV.setOnClickListener {
//            noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
//        }
//
//        holder.itemView.setOnClickListener {
//            noteClickInterface.onNoteClick(allNotes.get(position))
//        }
//    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList : List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }


}



interface NoteClickDeleteInterface{
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface{
    fun onNoteClick(note: Note)
}