package ise308.homework3.notetoberkayaltay

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class DialogShowNote(private var selectedNote:Note) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)

        val dialogLayout = activity!!.layoutInflater.inflate(R.layout.dialog_show_selected_note,null)

        val title = dialogLayout.findViewById<TextView>(R.id.titleTextViewID)
        val description = dialogLayout.findViewById<TextView>(R.id.descriptionTextViewID)
        val todo = dialogLayout.findViewById<TextView>(R.id.todoTextView)
        val important = dialogLayout.findViewById<TextView>(R.id.importantTextView)
        val idea = dialogLayout.findViewById<TextView>(R.id.ideaTextView)
        val buttonOk = dialogLayout.findViewById<Button>(R.id.okButtonID)

        title.text = this.selectedNote.title
        description.text = this.selectedNote.description
        when {
            selectedNote.idea -> {
                idea.setTextColor(Color.RED)
            }
            selectedNote.important -> {
                important.setTextColor(Color.RED)
            }
            selectedNote.todo -> {
                todo.setTextColor(Color.RED)
            }
        }

        builder.setView(dialogLayout)
                .setMessage("Note!")

        buttonOk.setOnClickListener {
            dismiss()
        }

        return builder.create()
    }
}