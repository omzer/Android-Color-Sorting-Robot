package com.example.colorsortrobot

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.colorsortrobot.ViewModels.BluetoothViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BluetoothViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initListeners()
        observeChanges()

        viewModel.checkBluetoothConnection()
    }

    private fun initViews() {
        // init viewModel
        viewModel = ViewModelProvider(this).get(BluetoothViewModel::class.java)
    }

    private fun initListeners() {
        enableBluetooth.findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.askSystemToTurnBluetoothOn(this)
        }
    }

    private fun observeChanges() {
        // Observe for bluetooth status changed
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
        for (bt: BluetoothDevice in bluetoothDevicesList) {

        }
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