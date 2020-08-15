package com.example.colorsortrobot.activities

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
    private val handler: Handler = Handler()
    private lateinit var runnable: Runnable

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
        bluetoothViewModel.getConnectionStatusObserver().observe(this, Observer { status ->
            when (status) {
                BluetoothConnectionStatus.STARTED -> loadingDialog.show()
                BluetoothConnectionStatus.FINISHED -> initializeClassifier()
                BluetoothConnectionStatus.CANCELLED -> finish()
                null -> finish()
            }
        })
    }

    private fun initializeClassifier() {
        val initializingTask = classifier.initialize()
        initializingTask?.addOnSuccessListener {
            runTheCamera()
            handler.postDelayed(runnable, 2000)
        }
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
        runnable = Runnable {
            val classifierTask = classifier.classifyAsync(cameraPreview.bitmap!!)

            classifierTask.addOnSuccessListener {
                onClassificationResultReceived(it)
                handler.postDelayed(runnable, 200)
            }
            classifierTask.addOnFailureListener { Log.i("TAG", "Classifier failed: ${it.message}") }

        }
    }

    private fun onClassificationResultReceived(result: String) {
        when (result) {
            "Happy" -> rootLayout.setBackgroundColor(Color.GREEN)
            "Sad" -> rootLayout.setBackgroundColor(Color.RED)
        }
    }

    private fun start() {
        cameraViewModel.startCamera()
    }

    override fun onRequestPermissionsResult(requestCode: Int, p: Array<String>, res: IntArray) {
        cameraViewModel.onRequestPermissionsResult(requestCode)
    }

    override fun onDestroy() {
        loadingDialog.dismiss()
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }

}