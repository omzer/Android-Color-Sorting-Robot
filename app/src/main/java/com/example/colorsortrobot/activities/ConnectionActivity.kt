package com.example.colorsortrobot.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.colorsortrobot.R
import com.example.colorsortrobot.fragments.DevicesListFragment
import com.example.colorsortrobot.fragments.EnableBluetoothFragment
import com.example.colorsortrobot.fragments.NoBluetoothDetectedFragment
import com.example.colorsortrobot.local_db.Prefs
import com.example.colorsortrobot.view_models.BluetoothViewModel


class ConnectionActivity : AppCompatActivity() {
    private lateinit var bluetoothViewModel: BluetoothViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Prefs.initPrefs(this)
        bluetoothViewModel = ViewModelProvider(this).get(BluetoothViewModel::class.java)
        replaceFragment(EnableBluetoothFragment())
        observeChanges()
    }

    private fun observeChanges() = bluetoothViewModel.getStatusObserver().observe(
        this, Observer {
            when (it) {
                true -> replaceFragment(DevicesListFragment())
                false -> replaceFragment(EnableBluetoothFragment())
                null -> replaceFragment(NoBluetoothDetectedFragment())
            }
        }
    )

    fun getDevicesList() = bluetoothViewModel.getBluetoothDevicesList()

    fun askForPermissions() {
        bluetoothViewModel.askSystemToTurnBluetoothOn(this)
    }

    private fun replaceFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment, fragment, fragment.javaClass.name)

        if (!supportFragmentManager.isDestroyed) transaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        bluetoothViewModel.onActivityResult(requestCode, resultCode)
    }
}