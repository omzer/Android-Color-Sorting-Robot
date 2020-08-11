package com.example.colorsortrobot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.colorsortrobot.view_models.BluetoothViewModel

class EnableBluetoothFragment() : Fragment() {

    private lateinit var bluetoothViewModel: BluetoothViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.enable_bluetooth_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bluetoothViewModel = ViewModelProvider(this).get(BluetoothViewModel::class.java)
        observeChanges()
        bluetoothViewModel.askSystemToTurnBluetoothOn(requireActivity())
    }


    private fun observeChanges() = bluetoothViewModel.getStatusObserver().observe(
        viewLifecycleOwner, Observer { result ->
            when (result) {
                true -> onResume()
                false -> onResume()
                null -> onResume()
            }
        })

}