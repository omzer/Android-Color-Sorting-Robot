package com.example.colorsortrobot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ControlActivity : AppCompatActivity() {
    var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)
        address = intent.getStringExtra("Address")
    }
}