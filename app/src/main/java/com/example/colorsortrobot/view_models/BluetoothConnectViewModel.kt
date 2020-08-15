package com.example.colorsortrobot.view_models

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.colorsortrobot.enums.BluetoothConnectionStatus
import java.util.*

class BluetoothConnectViewModel : ViewModel() {

    companion object {
        private val CONNECTION_STATUS_OBSERVER: MutableLiveData<BluetoothConnectionStatus> = MutableLiveData()
        private var socket: BluetoothSocket? = null
        private val bluetoothAdapter: BluetoothAdapter by lazy { BluetoothAdapter.getDefaultAdapter() }
        private val uuid: UUID by lazy { UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") }
    }

    fun getConnectionStatusObserver() = CONNECTION_STATUS_OBSERVER

    fun connectToAddress(address: String) {
        CONNECTION_STATUS_OBSERVER.value = BluetoothConnectionStatus.STARTED
        BluetoothConnectTask().execute(address)
    }

    override fun onCleared() {
        super.onCleared()
        socket?.close()
    }

    fun sendData(data: Int) {
        try {
            socket?.outputStream?.write(data)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    class BluetoothConnectTask : AsyncTask<String, BluetoothConnectionStatus, Boolean>() {

        override fun doInBackground(vararg params: String): Boolean {
            val address: String = params[0]
            if (socket != null) return false // Already connected

            try {
                tryConnecting(address)
            } catch (exception: Exception) {
                exception.printStackTrace()
                cancel(true)
                return false
            }

            return true
        }

        private fun tryConnecting(address: String) {
            val bluetoothDevice: BluetoothDevice = bluetoothAdapter.getRemoteDevice(address)
            socket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid)
            bluetoothAdapter.cancelDiscovery()
            socket?.connect()
        }

        override fun onCancelled() {
            super.onCancelled()
            CONNECTION_STATUS_OBSERVER.value = BluetoothConnectionStatus.CANCELLED
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            CONNECTION_STATUS_OBSERVER.value = BluetoothConnectionStatus.FINISHED
        }

    }

}