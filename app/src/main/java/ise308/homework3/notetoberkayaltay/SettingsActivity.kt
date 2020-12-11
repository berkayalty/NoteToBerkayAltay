package ise308.homework3.notetoberkayaltay

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.widget.SwitchCompat

class SettingsActivity : AppCompatActivity() {
    private val SETTING_1_TAG = "SETTING_1"
    private val SETTING_2_TAG = "SETTING_2"

    private lateinit var setting1Switch:SwitchCompat
    private lateinit var setting2Switch:SwitchCompat
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setting1Switch = findViewById(R.id.setting1SwitchID)
        setting1Switch.tag=SETTING_1_TAG
        setting2Switch = findViewById(R.id.setting2SwitchID)
        setting2Switch.tag=SETTING_2_TAG
        sharedPref = getSharedPreferences("setting_shared_pref", Context.MODE_PRIVATE)
        checkSharedPref()
        setting1Switch.setOnCheckedChangeListener(check)
        setting2Switch.setOnCheckedChangeListener(check)


    }

    private fun checkSharedPref(){
        setting1Switch.isChecked = sharedPref.getBoolean(this.SETTING_1_TAG,false)
        setting2Switch.isChecked = sharedPref.getBoolean(this.SETTING_2_TAG,false)
    }

    private val check = CompoundButton.OnCheckedChangeListener { compoundButton, b ->
        sharedPref.edit().putBoolean(compoundButton.tag as String,b).apply()
    }
}