package com.hrishikesh.contactstatusapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "android.intent.action.PHONE_STATE"){
            val state = intent.getStringExtra("state")
            if(state == "RINGING"){
                val preference = context?.getSharedPreferences("CallReceiverPref", Context.MODE_PRIVATE)
                val customMessage = preference?.getString("custom_message", "Incoming call received!")

                if(isToggleOn(context)){
                    Toast.makeText(context, customMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun isToggleOn(context: Context?) : Boolean{
        val preference = context?.getSharedPreferences("CallReceiverPref", Context.MODE_PRIVATE)
        return preference?.getBoolean("toggle_state", false) == true
    }
}