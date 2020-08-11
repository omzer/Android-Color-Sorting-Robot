package com.example.colorsortrobot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.colorsortrobot.activities.ConnectionActivity

class EnableBluetoothFragment : Fragment() {

    private lateinit var activity: ConnectionActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity = requireActivity() as ConnectionActivity
        val view = inflater.inflate(R.layout.enable_bluetooth_fragment, container, false)
        view.findViewById<View>(R.id.button).setOnClickListener { activity.askForPermissions() }
        return view
    }


}