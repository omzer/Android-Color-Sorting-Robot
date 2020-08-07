package com.example.colorsortrobot.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.colorsortrobot.adapters.DevicesAdapter
import com.example.colorsortrobot.DataSources.Prefs
import com.example.colorsortrobot.R
import com.example.colorsortrobot.ViewModels.BluetoothViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.devices_list.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BluetoothViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Prefs.initPrefs(this)
        initViews()
        initListeners()
        observeChanges()

        viewModel.checkBluetoothConnection()
    }

    private fun initViews() {
        // init viewModel
        viewModel = ViewModelProvider(this).get(BluetoothViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initListeners() {
        enableBluetooth.findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.askSystemToTurnBluetoothOn(this)
        }
    }

    private fun observeChanges() {
        // Observe for ic_bluetooth status changed
        viewModel.getStatusObserver().observe(this, Observer {
            hideStuff()
            when (it) {
                true -> showConnectedDevicesList()
                false -> enableBluetooth.visibility = View.VISIBLE
                null -> noBluetoothDetected.visibility = View.VISIBLE
            }
        })
    }

    private fun showConnectedDevicesList() {
        devices_list.visibility = View.VISIBLE
        val bluetoothDevicesList = viewModel.getBluetoothDevicesList()
        // todo if the list is empty - show empty state
        val recent: String = Prefs.getLatestAddress()
        val devicesAdapter = DevicesAdapter(bluetoothDevicesList, recent)
        recyclerView.adapter = devicesAdapter
    }

    private fun hideStuff() {
        enableBluetooth.visibility = View.GONE
        noBluetoothDetected.visibility = View.GONE
        devices_list.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode)
    }
}