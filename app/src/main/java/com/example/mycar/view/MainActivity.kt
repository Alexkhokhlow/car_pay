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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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

}