package com.example.colorsortrobot.activities

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.colorsortrobot.R
import com.example.colorsortrobot.enums.BluetoothConnectionStatus
import com.example.colorsortrobot.matchine_learning.ColorsClassifier
import com.example.colorsortrobot.view_models.BluetoothConnectViewModel
import com.example.colorsortrobot.view_models.CameraViewModel
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_control.*

class ControlActivity : AppCompatActivity() {
    private lateinit var bluetoothViewModel: BluetoothConnectViewModel
    private lateinit var loadingDialog: AlertDialog
    private lateinit var classifier: ColorsClassifier

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
                BluetoothConnectionStatus.STARTED -> loadingDialog.show()
                BluetoothConnectionStatus.FINISHED -> initializeClassifier()
                BluetoothConnectionStatus.CANCELLED -> initializeClassifier()
            }
        })
    }

    private fun initializeClassifier() {
        val initializingTask = classifier.initialize()
        initializingTask?.addOnSuccessListener { runTheCamera() }
        initializingTask?.addOnFailureListener { finish() }
    }

    private fun runTheCamera() {
        loadingDialog.dismiss()

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
        classifier = ColorsClassifier(this)
    }

    private fun start() {
        cameraViewModel.startCamera()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        cameraViewModel.onRequestPermissionsResult(requestCode)
    }

    override fun onDestroy() {
        loadingDialog.dismiss()
        super.onDestroy()
    }

}