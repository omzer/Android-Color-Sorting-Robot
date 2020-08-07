package com.example.colorsortrobot.Adapters

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.colorsortrobot.R
import com.example.colorsortrobot.Adapters.ViewHolders.DevicesListViewHolder

class DevicesAdapter(
    private val list: List<BluetoothDevice>, private val latestConnectedAddress: String
) : Adapter<DevicesListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): DevicesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.bluetooth_device_item, parent, false)
        return DevicesListViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DevicesListViewHolder, position: Int) {
        holder.setData(list[position], latestConnectedAddress)
    }

}
