package com.example.colorsortrobot.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.colorsortrobot.R
import com.example.colorsortrobot.activities.ConnectionActivity
import com.example.colorsortrobot.activities.ControlActivity
import com.example.colorsortrobot.adapters.DevicesAdapter
import com.example.colorsortrobot.adapters.interfaces.ItemClickedListener
import com.example.colorsortrobot.local_db.Prefs
import kotlinx.android.synthetic.main.devices_list.*

class DevicesListFragment : Fragment(), ItemClickedListener {

    private lateinit var activity: ConnectionActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity = requireActivity() as ConnectionActivity
        return inflater.inflate(R.layout.devices_list, container, false)
    }


    override fun onStart() {
        super.onStart()
        val devicesList = activity.getDevicesList()
        recyclerView.layoutManager = LinearLayoutManager(context)
        val recent: String = Prefs.getLatestAddress()
        val devicesAdapter = DevicesAdapter(devicesList, recent, this)
        recyclerView.adapter = devicesAdapter
    }

    override fun onItemClicked(address: String) {
        val intent = Intent(activity, ControlActivity::class.java)
        intent.putExtra(ControlActivity.addressKey, address)
        startActivity(intent)
    }


}