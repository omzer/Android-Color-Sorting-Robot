package com.example.colorsortrobot.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.colorsortrobot.R
import com.example.colorsortrobot.view_models.CameraViewModel
import kotlinx.android.synthetic.main.activity_control.*

class ControlActivity : AppCompatActivity() {

    companion object {
        const val addressKey: String = "ADDRESS"
    }

    private lateinit var cameraViewModel: CameraViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)
//        val address = intent.getStringExtra(addressKey)
        initViewModels()

        if (cameraViewModel.allPermissionsGranted()) {
            start()
        } else {
            cameraViewModel.askForPermissions()
        }

        cameraViewModel.setupCamera()
    }


    private fun initViewModels() {
        cameraViewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
        cameraViewModel.setActivity(this, cameraPreview)
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