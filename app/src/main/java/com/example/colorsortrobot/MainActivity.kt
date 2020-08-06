package com.example.colorsortrobot

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.colorsortrobot.ViewModels.BluetoothViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BluetoothViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init viewModel
        viewModel = ViewModelProvider(this).get(BluetoothViewModel::class.java)

        observeChanges()
        viewModel.checkBluetoothConnection()
    }

    private fun observeChanges() {
        // Observe for bluetooth status changed
        viewModel.getStatusObserver().observe(this, Observer {
            when (it) {
                true -> viewModel.getList() // todo: get list of connected devices, highlight the one connected to last time
                false -> showEnableBluetoothDialog()
                null -> showEnableBluetoothDialog() // todo not supported dialog
            }
        })
    }

    private fun showEnableBluetoothDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.enable_bluetooth_dialog)
        // val body = dialog.findViewById(R.id.body) as TextView
        dialog.show()
    }

}