package com.example.colorsortrobot.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.colorsortrobot.EnableBluetoothFragment
import com.example.colorsortrobot.R
import com.example.colorsortrobot.local_db.Prefs


class ConnectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Prefs.initPrefs(this)
        replaceFragment(EnableBluetoothFragment(), false)
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment, fragment, fragment.javaClass.name)
        if (addToBackStack) transaction.addToBackStack(fragment.javaClass.name)

        if (!supportFragmentManager.isDestroyed) transaction.commit()
    }
}