package ise308.homework3.notetoberkayaltay

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class NoteAdapter(private val context: Context,private var activity: MainActivity) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    var inflater: LayoutInflater = LayoutInflater.from(this.context)
    private val noteList: LinkedList<Note> = LinkedList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.note_list_view, parent, false)
        return ViewHolder(view,activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note: Note = noteList[position]
        holder.setData(note)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    open fun addNote(note: Note){
        this.noteList.addFirst(note)
        this.notifyItemInserted(0)
    }
    open fun deleteNote(position: Int){
        this.noteList.removeAt(position)
        this.notifyItemRemoved(position)
    }

    class ViewHolder(var itemView: View,var activity: MainActivity) : RecyclerView.ViewHolder(itemView){
        private var ideaTodoImportantTextView : TextView = itemView.findViewById(R.id.ideaTodoImportantTextViewID)
        private var noteTitleTextView : TextView = itemView.findViewById(R.id.noteTitleTextViewID)
        private var noteDescription : TextView = itemView.findViewById(R.id.noteDescriptionTextViewID)
        private lateinit var note:Note

        private val onClick = View.OnClickListener {
            activity.showSelectedNote(note)
        }

        private val onLongPress = View.OnLongClickListener {
            val builder = AlertDialog.Builder(activity);
                builder.setMessage("Are you sure to delete Note ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialogInterface, i ->
                        activity.deleteSelectedNode(note,adapterPosition)
                    }
                    .setNegativeButton("No") { dialogInterface, i ->

                    }

            val alert = builder.create();
            alert.show();
            return@OnLongClickListener true
        }

        init{
            itemView.setOnClickListener(this.onClick)
            itemView.setOnLongClickListener(onLongPress)
        }

        fun setData(note: Note) {
            this.note = note;
            if(note.idea){
                ideaTodoImportantTextView.text = "Idea"
            }else if(note.important){
                ideaTodoImportantTextView.text = "Important"
            }else if(note.todo){
                ideaTodoImportantTextView.text = "TODO"
            }
            noteTitleTextView.setText(note.title)
            noteDescription.setText(note.description)
        }
    }


}