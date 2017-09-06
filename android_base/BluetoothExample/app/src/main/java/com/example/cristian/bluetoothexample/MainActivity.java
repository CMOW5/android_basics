package com.example.cristian.bluetoothexample;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    // Debugging for LOGCAT
    private final String classname = getClass().getSimpleName();

    private final int REQUEST_BLUETOOTH = 1;
    // EXTRA string to send on to mainactivity
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    // Member fields
    private BluetoothAdapter mBtAdapter;

    //HC05 address
    private String mAddress = "20:15:07:24:14:09";

    Handler bluetoothIn;
    final int handlerState = 0; //used to identify handler message
    private StringBuilder recDataString = new StringBuilder();
    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothSocket btSocket = null;

    private ConnectedThread mConnectedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String methodname = " onCreate(..) ";

        Log.d(Utility.LOG_TAG, classname +  methodname);

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        //check if device has bluetooth
        if(mBtAdapter==null) {
            Log.d(Utility.LOG_TAG, classname +  methodname
                    + "this device does not support bluetooth ");
        } else {
            if (mBtAdapter.isEnabled()) {
                Log.d(Utility.LOG_TAG, classname + methodname + "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_BLUETOOTH);

            }
        }

        // Get a set of currently paired devices and append to 'pairedDevices'
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // get paired devices
        if (pairedDevices.size() > 0) {
            //findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);//make title viewable
            for (BluetoothDevice device : pairedDevices) {
                Log.d(Utility.LOG_TAG, classname + methodname
                         + " paired device name: "  + device.getName() + " , paired device address: "
                         + device.getAddress());
                //mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            Log.d(Utility.LOG_TAG, classname + methodname
                    + "not paired devices" );
        }

        //find new devices
        if (mBtAdapter.isDiscovering()) {
            Log.d(Utility.LOG_TAG, classname + methodname + "isDiscovering" );
            // the button is pressed when it discovers, so cancel the discovery
             mBtAdapter.cancelDiscovery();
        }
        else {
            //BTArrayAdapter.clear();
            Log.d(Utility.LOG_TAG, classname + methodname + " start Discovering" );
            mBtAdapter.startDiscovery();
            registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }

        ///////////CONECTION
        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {

                String methodname = " handleMessage(..) ";

                if (msg.what == handlerState) {	 //if message is what we want
                    String readMessage = (String) msg.obj;  // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage); 	//keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~"); // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        //txtString.setText("Data Received = " + dataInPrint);
                        Log.d(Utility.LOG_TAG, classname
                                + methodname + "data received" + recDataString.toString());
                        int dataLength = dataInPrint.length();							//get length of data received

                        //txtStringLength.setText("String Length = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '#')	//if it starts with # we know it is what we are looking for
                        {
                            /*
                            String sensor0 = recDataString.substring(1, 5); //get sensor value from string between indices 1-5
                            String sensor1 = recDataString.substring(6, 10); //same again...
                            String sensor2 = recDataString.substring(11, 15);
                            String sensor3 = recDataString.substring(16, 20);
                            */
                        }
                        recDataString.delete(0, recDataString.length()); 					//clear all string data
                        // strIncom =" ";
                        dataInPrint = " ";
                    }
                }
            }
        };

        //create device and set the MAC address
        BluetoothDevice device = mBtAdapter.getRemoteDevice(mAddress);

        try {
            Log.d(Utility.LOG_TAG, classname + methodname + "Creating socket");
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Log.d(Utility.LOG_TAG, classname + methodname + "Socket creation failed");
        }
        // Establish the Bluetooth socket connection.
        try
        {
            Log.d(Utility.LOG_TAG, classname + methodname + "Connecting to socket");
            btSocket.connect();
        } catch (IOException e) {
            Log.d(Utility.LOG_TAG, classname + methodname + "socket connection failed");
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
                Log.d(Utility.LOG_TAG, classname + methodname + "socket connection close failed");
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            String methodname = " ConnectedThread constructor ";

            try {
                Log.d(Utility.LOG_TAG, classname + methodname + "creating IO streams");
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.d(Utility.LOG_TAG, classname + methodname + "IO streams Exception");
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            String methodname = " write ";
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Log.d(Utility.LOG_TAG, classname + methodname + "Connection Failure");
                finish();

            }
        }
    }

    //called when a new device is found
    final BroadcastReceiver bReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String methodname = " onReceive BroadcastReceiver ";
            String action = intent.getAction();

            Log.d(Utility.LOG_TAG, classname + methodname);

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // add the name and the MAC address of the object to the arrayAdapter
                Log.d(Utility.LOG_TAG, classname + methodname
                        + "device found: " + device.getName() + " , "
                        + device.getAddress());
                //BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                //BTArrayAdapter.notifyDataSetChanged();
            }
        }
    };


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    public void find(View view) {
        //find new devices
        String methodname = " find(..) ";

        if (mBtAdapter.isDiscovering()) {
            Log.d(Utility.LOG_TAG, classname + methodname + "isDiscovering" );
            // the button is pressed when it discovers, so cancel the discovery
            mBtAdapter.cancelDiscovery();
        }
        else {
            //BTArrayAdapter.clear();
            Log.d(Utility.LOG_TAG, classname + methodname + " start Discovering" );
            mBtAdapter.startDiscovery();
            registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        String methodname = " onActivityResult(..) ";

        if (requestCode == REQUEST_BLUETOOTH){
            Log.d(Utility.LOG_TAG, classname + methodname + "requestCode = BLUETOOTH");
            if (resultCode == Activity.RESULT_OK){
                Log.d(Utility.LOG_TAG, classname + methodname + "resultCode = OK");
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bReceiver);
    }

}
