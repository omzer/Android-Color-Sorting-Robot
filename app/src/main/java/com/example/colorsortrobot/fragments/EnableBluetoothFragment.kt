package com.example.colorsortrobot.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.colorsortrobot.R
import com.example.colorsortrobot.activities.ConnectionActivity
import kotlinx.android.synthetic.main.enable_bluetooth_fragment.*

class EnableBluetoothFragment : Fragment() {

    private lateinit var activity: ConnectionActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity = requireActivity() as ConnectionActivity
        return inflater.inflate(R.layout.enable_bluetooth_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        button.setOnClickListener { activity.askForPermissions() }
    }

}