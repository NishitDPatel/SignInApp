package com.example.rushi.signinapp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class Success extends AppCompatActivity {

    private ProgressDialog progress;
    private BluetoothAdapter myBluetooth = null;
    private BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private String MACaddress = null;
    private String deviceName = null;

    private InputStream inputStream;
    private byte buffer[];
    private boolean stopThread;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String user;
        user = intent.getStringExtra(MainActivity.EXTRA_SUCCESS);//receive the MAC address of the bluetooth device

        setContentView(R.layout.activity_success);
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(user);

        //if the device has bluetooth
        //myBluetooth = BluetoothAdapter.getDefaultAdapter();

        MACaddress = "FC:A8:9A:00:31:DE";
        deviceName = "HC-05";

        new ConnectBT().execute();//Call the class to connect

    }

    public void sendData(View view)
    {
        char send=' ';
        if((int)view.getId()==2131165251)
            send='a';
        else if((int)view.getId()==2131165311)
            send='b';
        if (btSocket!=null)
        {
            try{
                    btSocket.getOutputStream().write(send);
            }
            catch (IOException e)
            {
                msg("Error in sending data");
            }
        }
    }

    // efficient way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>// UI thread
    {
        private boolean ConnectSuccess = true;//if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(Success.this, "Connecting with "+deviceName, "Please wait!!");//show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices)//while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice bluetoothDevice = myBluetooth.getRemoteDevice(MACaddress);//connects to the device's address and checks if it's available
                    btSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)//after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is "+deviceName +" a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Successfully Connected with "+deviceName);
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

}
