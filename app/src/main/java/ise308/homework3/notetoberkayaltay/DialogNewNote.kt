package ise308.homework3.notetoberkayaltay

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import kotlin.random.Random

class DialogNewNote : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)

        val dialogLayout = activity!!.layoutInflater.inflate(R.layout.dialog_note_new,null)

        val editTitle = dialogLayout.findViewById<EditText>(R.id.edit_title)
        val editDescription = dialogLayout.findViewById<EditText>(R.id.edit_description)
        val checkBoxIdea = dialogLayout.findViewById<CheckBox>(R.id.checkBox_idea)
        val checkBoxToDo = dialogLayout.findViewById<CheckBox>(R.id.checkBox_todo)
        val checkBoxImportant = dialogLayout.findViewById<CheckBox>(R.id.checkBox_important)
        val buttonOk = dialogLayout.findViewById<Button>(R.id.button_ok)
        val buttonCancel = dialogLayout.findViewById<Button>(R.id.button_cancel)

        builder.setView(dialogLayout)
            .setMessage("Add a new note")

        buttonCancel.setOnClickListener {
            dismiss()
        }

        buttonOk.setOnClickListener {
            val newNote = Note()
            newNote.id = editTitle.text.substring(0,1)+editDescription.text.substring(0,1)+Random(9999).nextInt()
            newNote.title = editTitle.text.toString()
            newNote.description = editDescription.text.toString()
            newNote.idea = checkBoxIdea.isChecked
            newNote.todo = checkBoxToDo.isChecked
            newNote.important = checkBoxImportant.isChecked

            val callingActivity = activity as MainActivity?
            callingActivity!!.createNote(newNote)
            dismiss()
        }

        return builder.create()
    }
}