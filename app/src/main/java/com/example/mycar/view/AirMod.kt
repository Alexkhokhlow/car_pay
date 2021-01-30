package com.example.mycar.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirMod:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val a = intent?.getBooleanExtra("state", false) ?: return
        if(!a){
            Toast.makeText(context,"AXUENO EST' INTERNET",Toast.LENGTH_SHORT).show()
        } else{
            val a  = 1
            Toast.makeText(context,"NOU INTERNET",Toast.LENGTH_SHORT).show()

        }
    }
}