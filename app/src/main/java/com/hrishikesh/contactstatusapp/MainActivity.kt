package com.hrishikesh.contactstatusapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etStatus : EditText
    private lateinit var tvStatus : TextView
    private lateinit var btnStatus : Button
    private lateinit var toggleSwitch : Switch

    private val callReceiver = CallReceiver()
    private val callReceiverIntentFilter = IntentFilter("android.intent.action.PHONE_STATE")
    private val permission = arrayOf(Manifest.permission.READ_PHONE_STATE)
    private val requestCode = 101

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etStatus = findViewById(R.id.etStatus)
        tvStatus = findViewById(R.id.tvStatus)
        btnStatus = findViewById(R.id.btnStatus)
        toggleSwitch = findViewById(R.id.toggleSwitch)

        if(!hasPhoneStatePermission()){
            ActivityCompat.requestPermissions(this, permission, requestCode)
        }

        toggleSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            saveToggleState(isChecked)
        }


        btnStatus.setOnClickListener {
            if(etStatus.text.isNullOrBlank()){
                Toast.makeText(this, "Please Enter The Status", Toast.LENGTH_SHORT).show()
            }else{
                tvStatus.text = etStatus.text.toString()

                val customMessage = tvStatus.text.toString()
                val preference = getSharedPreferences("CallReceiverPref", Context.MODE_PRIVATE)
                val editor = preference.edit()
                editor.putString("custom_message", customMessage)
                editor.apply()

                Toast.makeText(this, "Status Set!!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(callReceiver)
    }
    private fun hasPhoneStatePermission() : Boolean{
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
    }
    private fun saveToggleState(isChecked : Boolean){
        val preference = getSharedPreferences("CallReceiverPref", Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("toggle_state", isChecked)
        editor.apply()
    }
}