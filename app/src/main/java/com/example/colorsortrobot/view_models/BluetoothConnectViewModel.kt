package com.example.colorsortrobot.view_models

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.AsyncTask
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.colorsortrobot.enums.ProgressType
import java.util.*

class BluetoothConnectViewModel : ViewModel() {

    companion object {
        private val connectionStatusObserver: MutableLiveData<ProgressType> = MutableLiveData()
        private var socket: BluetoothSocket? = null
        private val bluetoothAdapter: BluetoothAdapter by lazy { BluetoothAdapter.getDefaultAdapter() }
        private val uuid: UUID by lazy { UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") }
    }

    fun getConnectionStatusObserver() = connectionStatusObserver

    fun connectToAddress(address: String) {
        connectionStatusObserver.value = ProgressType.STARTED
        BluetoothConnectTask().execute(address)
    }

    class BluetoothConnectTask : AsyncTask<String, ProgressType, Boolean>() {

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
            connectionStatusObserver.value = ProgressType.CANCELLED
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            connectionStatusObserver.value = ProgressType.FINISHED
        }

    }

}