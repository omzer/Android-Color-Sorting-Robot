package com.example.colorsortrobot.activities

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.colorsortrobot.R
import com.example.colorsortrobot.enums.ProgressType
import com.example.colorsortrobot.view_models.BluetoothConnectViewModel
import com.example.colorsortrobot.view_models.CameraViewModel
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_control.*

class ControlActivity : AppCompatActivity() {
    private lateinit var bluetoothViewModel: BluetoothConnectViewModel
    private lateinit var loadingDialog: AlertDialog

    companion object {
        const val addressKey: String = "ADDRESS"
    }

    private lateinit var cameraViewModel: CameraViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        initViewsAndModels()
        observeChanges()
        connectToDevice()
        cameraViewModel.setupCamera()
    }

    private fun connectToDevice() {
        val address = intent.getStringExtra(addressKey)
        address?.let { bluetoothViewModel.connectToAddress(address) }
    }

    private fun observeChanges() {
        bluetoothViewModel.getConnectionStatusObserver().observe(this, Observer {
            when (it) {
                ProgressType.STARTED -> loadingDialog.show()
                ProgressType.FINISHED -> runTheCamera()
                ProgressType.CANCELLED -> finish()
            }
        })
    }


    override fun onDestroy() {
        loadingDialog.dismiss()
        super.onDestroy()
    }

    private fun runTheCamera() {
        loadingDialog.hide()
        if (cameraViewModel.allPermissionsGranted()) {
            start()
        } else {
            cameraViewModel.askForPermissions()
        }
    }

    private fun initViewsAndModels() {
        cameraViewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
        bluetoothViewModel = ViewModelProvider(this).get(BluetoothConnectViewModel::class.java)
        cameraViewModel.setActivity(this, cameraPreview)
        loadingDialog = SpotsDialog.Builder().setContext(this).build()
    }

    private fun start() {
        cameraViewModel.startCamera()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        cameraViewModel.onRequestPermissionsResult(requestCode)
    }

}