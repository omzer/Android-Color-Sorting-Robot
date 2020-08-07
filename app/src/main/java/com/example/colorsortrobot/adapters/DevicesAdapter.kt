package com.example.colorsortrobot.adapters

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.colorsortrobot.R
import com.example.colorsortrobot.adapters.ViewHolders.DevicesListViewHolder
import com.example.colorsortrobot.adapters.interfaces.ItemClickedListener
import com.example.colorsortrobot.models.DeviceItemModel

class DevicesAdapter(
    private val list: List<BluetoothDevice>,
    private val latestConnectedAddress: String,
    private val listener: ItemClickedListener

) : Adapter<DevicesListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, type: Int): DevicesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.bluetooth_device_item, parent, false)
        return DevicesListViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DevicesListViewHolder, position: Int) {
        val model = DeviceItemModel(list[position], latestConnectedAddress, listener)
        holder.setData(model)
    }

}
