package ise308.homework3.notetoberkayaltay

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: NoteAdapter
    private val FILE_NAME="NOTES"
    private var JSONarray = JSONArray()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<FloatingActionButton>(R.id.floatingButtonID).setOnClickListener {
            val dialogNewNote = DialogNewNote()
            dialogNewNote.show(supportFragmentManager, "NEWNOTE")
        }
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerViewID)
        adapter = NoteAdapter(applicationContext, this)
        recyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        readJSON()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settingsButtonID -> openSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openSettings(){
        val intent = Intent(applicationContext, SettingsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        applicationContext.startActivity(intent)
    }

    fun showSelectedNote(noteParam: Note){
        val dialogShowNote = DialogShowNote(noteParam)
        dialogShowNote.show(supportFragmentManager, "SHOWNOTE")
    }

    fun deleteSelectedNode(noteParam: Note,adapterPosition:Int){
        val tempJsonArray = JSONArray()
        var tempJsonArrayCount =0
        for(i in 0 until JSONarray.length()){
            if((JSONarray.get(i) as JSONObject).getString("id") != noteParam.id){
                tempJsonArray.put(tempJsonArrayCount,JSONarray.get(i))
                tempJsonArrayCount++
            }
        }
        this.JSONarray = JSONArray()
        for(i in 0 until tempJsonArray.length()){
            JSONarray.put(i,tempJsonArray.get(i))
        }
        writeJSON(JSONarray.toString())
        this.adapter.deleteNote(adapterPosition)
    }

    fun createNote(noteParam: Note){
        val jsonObject =JSONObject()
        jsonObject.put("id",noteParam.id)
        jsonObject.put("title", noteParam.title)
        jsonObject.put("description", noteParam.description)
        jsonObject.put("idea", noteParam.idea)
        jsonObject.put("todo", noteParam.todo)
        jsonObject.put("important", noteParam.important)

        JSONarray.put(JSONarray.length(), jsonObject)
        writeJSON(JSONarray.toString())
        this.adapter.addNote(noteParam)
    }

    private fun writeJSON(noteString : String){
        val file = File(applicationContext.filesDir, FILE_NAME)
        val fileWriter = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.append(noteString)
        bufferedWriter.close()
    }

    private fun readJSON(){
        try{
            val file= File(applicationContext.filesDir, FILE_NAME)
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader)
            val line: String = bufferedReader.readText()
            bufferedReader.close()

            val jsonArray = JSONArray(line)
            for(i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)
                JSONarray.put(JSONarray.length(), jsonObject)

                val note = Note()
                note.id = jsonObject.getString("id")
                note.title = jsonObject.getString("title")
                note.description =  jsonObject.getString("description")
                note.idea = jsonObject.getBoolean("idea")
                note.todo = jsonObject.getBoolean("todo")
                note.important = jsonObject.getBoolean("important")
                adapter.addNote(note)
            }
        }catch(fileNot: FileNotFoundException){

        }
    }

}
