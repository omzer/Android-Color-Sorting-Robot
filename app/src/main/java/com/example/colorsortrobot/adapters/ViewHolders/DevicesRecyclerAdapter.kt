package com.example.colorsortrobot.adapters.ViewHolders

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.colorsortrobot.local_db.Prefs
import com.example.colorsortrobot.R
import com.example.colorsortrobot.models.DeviceItemModel
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.bluetooth_device_item.view.*

class DevicesListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun setData(model: DeviceItemModel) {
        val deviceInfo = model.getDeviceInfo()
        val address = deviceInfo.address
        val name = deviceInfo.name

        itemView.findViewById<TextView>(R.id.name).text = "Name: $name"
        itemView.findViewById<TextView>(R.id.address).text = "Address: $address"

        if (model.getLatestAddress() == address) {
            itemView.findViewById<MaterialButton>(R.id.recentBadge)
                .recentBadge
                .setBackgroundColor(Color.parseColor("#28E832"))
        }

        itemView.findViewById<CardView>(R.id.itemCard).itemCard.setOnClickListener {
            Prefs.setLatestAddress(address)
            model.getListener().onItemClicked(address)
        }
    }

}