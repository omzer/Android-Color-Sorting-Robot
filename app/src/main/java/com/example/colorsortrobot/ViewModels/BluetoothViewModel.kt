package com.example.colorsortrobot.ViewModels

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BluetoothViewModel : ViewModel() {
    private val BLUETOOTH_REQUEST_CODE: Int = 911

    // Observers
    private val bluetoothConnectionStatus: MutableLiveData<Boolean?> = MutableLiveData()

    fun getStatusObserver(): MutableLiveData<Boolean?> = bluetoothConnectionStatus

    fun checkBluetoothConnection() {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null) {
            bluetoothConnectionStatus.value = null
        } else {
            bluetoothConnectionStatus.value = bluetoothAdapter.isEnabled
        }
    }

    fun askSystemToTurnBluetoothOn(activity: Activity) {
        val turnBTIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        activity.startActivityForResult(turnBTIntent, BLUETOOTH_REQUEST_CODE)
    }

    fun getList() {}

    fun onActivityResult(requestCode: Int, resultCode: Int) {
        if (requestCode == BLUETOOTH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            bluetoothConnectionStatus.value = true
        }
    }

}

//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        initBluetoothAdapter()
//        viewBluetoothData()
//
//
//    }
//
//    private fun viewBluetoothData() {
//        val list = ArrayList<String>()
//
//        if (pairedDevices!!.isNotEmpty()) {
//            for (bt: BluetoothDevice in pairedDevices!!) {
//                list.add(bt.name + "\n" + bt.address);
//            }
//        }
//        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, list)
//        bluetoothListView.adapter = adapter
//        bluetoothListView.setOnItemClickListener { _, view, _, _ ->
//            // Get the device MAC address, the last 17 chars in the View
//            val info: String = (view as TextView).text.toString()
//            val address = info.substring(info.length - 17)
//            // Make an intent to start next activity.
//            Toast.makeText(this, R.string.connect_success, Toast.LENGTH_SHORT).show()
//
//            val intent = Intent(this, ControlActivity::class.java)
//            intent.putExtra("Address", address)
//            startActivity(intent)
//        }
//    }
//
//    private fun initBluetoothAdapter() {
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//        if (bluetoothAdapter == null) {
//            Toast.makeText(this, R.string.no_bluetooth, Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        bluetoothAdapter.let {
//            pairedDevices = it.bondedDevices
//            if (!it.isEnabled) {
//                //Ask to the user turn the bluetooth on
//                val turnBTon = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//                startActivityForResult(turnBTon, 1);
//            } else {
//                viewBluetoothData()
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            initBluetoothAdapter()
//            viewBluetoothData()
//        } else {
//            Toast.makeText(applicationContext, R.string.bluetooth_off, Toast.LENGTH_SHORT).show();
//        }
//    }
//}