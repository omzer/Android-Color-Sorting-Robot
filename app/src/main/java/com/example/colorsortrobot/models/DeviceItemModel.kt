package com.example.colorsortrobot.models

import android.bluetooth.BluetoothDevice
import com.example.colorsortrobot.adapters.interfaces.ItemClickedListener

class DeviceItemModel(
    private val deviceInfo: BluetoothDevice,
    private val latestAddress: String,
    private val listener: ItemClickedListener
) {
    fun getDeviceInfo(): BluetoothDevice = deviceInfo
    fun getLatestAddress(): String = latestAddress
    fun getListener(): ItemClickedListener = listener
}