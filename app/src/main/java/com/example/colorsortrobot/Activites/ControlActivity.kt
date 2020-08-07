package com.example.colorsortrobot.Activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.colorsortrobot.R

class ControlActivity : AppCompatActivity() {
    var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)
        address = intent.getStringExtra("Address")
    }
}