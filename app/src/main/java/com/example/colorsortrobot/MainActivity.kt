package com.example.colorsortrobot

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private lateinit var bluetoothAdapter: BluetoothAdapter
private var pairedDevices: Set<BluetoothDevice>? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBluetoothAdapter()
        viewBluetoothData()


    }

    private fun viewBluetoothData() {
        val list = ArrayList<String>()

        if (pairedDevices!!.isNotEmpty()) {
            for (bt: BluetoothDevice in pairedDevices!!) {
                list.add(bt.name + "\n" + bt.address);
            }
        }
        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, list)
        bluetoothListView.adapter = adapter
        bluetoothListView.setOnItemClickListener { _, view, _, _ ->
            // Get the device MAC address, the last 17 chars in the View
            val info: String = (view as TextView).text.toString()
            val address = info.substring(info.length - 17)
            // Make an intent to start next activity.
            Toast.makeText(this, R.string.connect_success, Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ControlActivity::class.java)
            intent.putExtra("Address", address)
            startActivity(intent)
        }
    }

    private fun initBluetoothAdapter() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(this, R.string.no_bluetooth, Toast.LENGTH_SHORT).show()
            return
        }

        bluetoothAdapter.let {
            pairedDevices = it.bondedDevices
            if (!it.isEnabled) {
                //Ask to the user turn the bluetooth on
                val turnBTon = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(turnBTon, 1);
            } else {
                viewBluetoothData()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            initBluetoothAdapter()
            viewBluetoothData()
        } else {
            Toast.makeText(applicationContext, R.string.bluetooth_off, Toast.LENGTH_SHORT).show();
        }
    }
}

/**
 *
public class ConnectAct extends AppCompatActivity {

private ListView devicelist;
private Button paired_btn;
private BluetoothAdapter myBluetooth ;
private Set <BluetoothDevice> pairedDevices ;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_connect);

devicelist = findViewById(R.id.list_view);
paired_btn = findViewById(R.id.paired_button);


myBluetooth = BluetoothAdapter.getDefaultAdapter();
if(myBluetooth == null)
{
//Show a mensag. that thedevice has no bluetooth adapter
Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
//finish apk
startActivity(new Intent(ConnectAct.this,MainActivity.class));
}
else
{
if (myBluetooth.isEnabled())
{ }
else
{
//Ask to the user turn the bluetooth on
Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
startActivityForResult(turnBTon,1);
}
}


paired_btn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v)
{
pairedDevicesList(); //method that will be called
}

private void pairedDevicesList() {
pairedDevices = myBluetooth.getBondedDevices();
ArrayList list = new ArrayList<>();

if(pairedDevices.size() > 0){
for(BluetoothDevice bt : pairedDevices){
list.add(bt.getName()+"\n"+bt.getAddress());
}
}else{

Toast.makeText(getApplicationContext(), "No paired devices", Toast.LENGTH_SHORT).show();
}

final ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, list);
devicelist.setAdapter(adapter);
devicelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
// One Item clicked
// Get the device MAC address, the last 17 chars in the View
String info = ((TextView) view).getText().toString();
String address = info.substring(info.length() - 17);
// Make an intent to start next activity.
Toast.makeText(ConnectAct.this, "connected to "+address, Toast.LENGTH_SHORT).show();
Data.address=address;
startActivity(new Intent(ConnectAct.this,MainActivity.class));
//Change the activity.
}
});
}
});

}
}

 */