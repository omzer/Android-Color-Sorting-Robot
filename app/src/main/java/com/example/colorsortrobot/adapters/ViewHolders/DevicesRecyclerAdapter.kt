package com.example.colorsortrobot.adapters.ViewHolders

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.colorsortrobot.R
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.bluetooth_device_item.view.*

class DevicesListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun setData(deviceInfo: BluetoothDevice, latestDeviceAddress: String) {
        itemView.findViewById<TextView>(R.id.name).text = "Name: ${deviceInfo.name}"
        itemView.findViewById<TextView>(R.id.address).text = "Address: ${deviceInfo.address}"
        if (latestDeviceAddress == deviceInfo.address) {
            itemView.findViewById<MaterialButton>(R.id.recentBadge)
                .recentBadge
                .setBackgroundColor(Color.parseColor("#28E832"))
        }
    }

}