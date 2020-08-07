package com.example.colorsortrobot

import android.content.Intent
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

        initViews()
        initListeners()
        observeChanges()

        viewModel.checkBluetoothConnection()
        loadingDialog.show()
    }

    private fun initViews() {
        // init viewModel
        viewModel = ViewModelProvider(this).get(BluetoothViewModel::class.java)

        // init loading dialog
        loadingDialog = SpotsDialog.Builder().setContext(this).build()
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode)
    }
}