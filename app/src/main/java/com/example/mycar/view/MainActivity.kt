package com.example.mycar.view

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.mycar.R
import com.example.mycar.databinding.ActivityMainBinding
import com.example.mycar.databinding.FragmentMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var reciver:AirMod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        reciver = AirMod()

        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(reciver,it)
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.home -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.mainFragment)
                    true
                }
                R.id.service ->{
                    findNavController(R.id.nav_host_fragment).navigate(R.id.addEventFragment)
                    true
                }
                else -> {false}
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(reciver)
    }
}