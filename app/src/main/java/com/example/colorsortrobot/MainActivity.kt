package com.example.colorsortrobot

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.colorsortrobot.ViewModels.BluetoothViewModel
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BluetoothViewModel
    private lateinit var loadingDialog: android.app.AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        observeChanges()

        viewModel.checkBluetoothConnection()
        loadingDialog.show()
    }

    private fun init() {
        // init viewModel
        viewModel = ViewModelProvider(this).get(BluetoothViewModel::class.java)

        // init loading dialog
        loadingDialog = SpotsDialog.Builder().setContext(this).build()
    }

    private fun observeChanges() {
        // Observe for bluetooth status changed
        viewModel.getStatusObserver().observe(this, Observer {
            loadingDialog.hide()
            when (it) {
                true -> viewModel.getList() // todo: get list of connected devices, highlight the one connected to last time
                false -> showEnableBluetoothMessage()
                null -> showEnableBluetoothMessage() // todo not supported dialog
            }
        })
    }

    private fun hideStuff() {
        loadingDialog.hide()
        enableBluetooth.visibility = View.GONE
    }

    private fun showEnableBluetoothMessage() {
        hideStuff()
        enableBluetooth.visibility = View.VISIBLE
        enableBluetooth.findViewById<Button>(R.id.button).setOnClickListener {

        }

    }

}