package com.example.colorsortrobot.view_models

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BluetoothDiscoveryViewModel : ViewModel() {
    companion object {
        private const val BLUETOOTH_REQUEST_CODE: Int = 911
    }

    // Observers
    private val bluetoothConnectionStatus: MutableLiveData<Boolean?> = MutableLiveData()

    fun getStatusObserver(): MutableLiveData<Boolean?> = bluetoothConnectionStatus

    fun checkBluetoothConnection() {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null) {
            bluetoothConnectionStatus.value = null
        } else {
            bluetoothConnectionStatus.value = bluetoothAdapter.isEnabled
        }
    }

    fun askSystemToTurnBluetoothOn(activity: Activity) {
        val turnBTIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        activity.startActivityForResult(turnBTIntent, BLUETOOTH_REQUEST_CODE)
    }

    fun getBluetoothDevicesList(): List<BluetoothDevice> {
        return BluetoothAdapter.getDefaultAdapter().bondedDevices.toList()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int) {
        if (requestCode == BLUETOOTH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            bluetoothConnectionStatus.value = true
        }
    }


}