package com.example.photon.photonandroid;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.hardware.usb.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import io.particle.android.sdk.cloud.*;
import io.particle.android.sdk.utils.*;

import static java.lang.Byte.SIZE;


public class MainActivity extends Activity  {
    private static final String TAG = "Hello";

//    public static final String TAG = HttpClient.class.getSimpleName();

    private Button check, connect;
    private TextView variable;
    private boolean clicked = false;
    private int i = 0;
    private long retrieved = 0;
    private final int SIZE = 4096;
    private static final String ACTION_USB_PERMISSION =
            "com.android.example.USB_PERMISSION";
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if(device != null){
                            //call method to set up device communication
                        }
                    }
                    else {
                        Log.d(TAG, "permission denied for device " + device);
                    }
                }
            }
        }
    };
//    private List<ParticleDevice> devices;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = findViewById(R.id.connect);

        check =  findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clicked == false ) clicked = true;
            }
        });

        variable =  findViewById(R.id.variable);

        final UsbManager mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        final PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);


        View.OnClickListener connectListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
                        //mPermissionIntent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(ACTION_USB_PERMISSION), 0);

                        final byte[] bytes =  new byte[SIZE];
                        final int TIMEOUT = 0;
                        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
                        Map<String, UsbDevice> devices = manager.getDeviceList();
                        UsbDevice mDevice = null;
                        Toaster.s(MainActivity.this, "looking for devices");
                        boolean find = false;
                        for(String key: devices.keySet()){
//                            Toaster.s(MainActivity.this, key + "; " + devices.get(key));
                            mDevice = devices.get(key);
                            find = true;
                        }
                        while (find == true) {

                            if(mUsbManager.hasPermission(mDevice)) {
                                UsbInterface intf = mDevice.getInterface(0);
                                final UsbEndpoint endpoint = intf.getEndpoint(0);
                                final UsbDeviceConnection connection = manager.openDevice(mDevice);
//                                Toaster.s(MainActivity.this, Integer.toString(endpoint.getMaxPacketSize()));
//
                                connection.claimInterface(mDevice.getInterface(0), true);
                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        while (true) {
                                            connection.bulkTransfer(endpoint, bytes, SIZE, 1000);

//                                            StringBuilder str = new StringBuilder();
//                                            int value = 0;
//                                            for (int i = 2; i < SIZE; i++) {
////                                                if (bytes[i] != 0) {
////                                                   value = (value << 8) | bytes[i];
////                                                } else {
//////                                                    Log.e("Database", "M=" + str.toString());
////                                                    break;
////                                                }
////                                            }
                                            StringBuilder test =  new StringBuilder();
                                            long value = 0;
                                            value = ByteBuffer.wrap(bytes).get();
//                                            for (int i = 0; i < SIZE; i++) {
//                                                if (bytes[i] != 0) {
////                                                    value  = Character.getNumericValue( bytes[i]);
//                                                    short tmp = (short) bytes[i];
//                                                    if (tmp < 0) {
//                                                        tmp += 256;
//                                                    }
//                                                    value =  tmp;
////                                                    Toaster.s(MainActivity.this, Long.toString(value));
////
//                                                    test.append(value);
//                                                    test.append('/');
//
//                                                } else {
////                                                    Log.e("Database", "M=" + str.toString());
//                                                    break;
//                                                }
//                                            }


                                            retrieved = value;
//                                            Toaster.s(MainActivity.this, test.toString());
                                        }
                                    }
                                }).start();
//
                                Toaster.s(MainActivity.this, "Got Permission");
                            break;
                            }
                            else{
                                mUsbManager.requestPermission(mDevice, mPermissionIntent);
                            }
                        }
                    }
                }).start();
            }
        };
        connect.setOnClickListener(connectListener);

//        new AsyncTask<Void, Void, String>(){
//
//            protected String doInBackground(Void... params) {
//                try {
//                    // Add your Particle SDK code here to do something
//                    // with the Particle Cloud
////                    System.out.println("Logged In");
//
//                    return "Some success message";
//                }
//                catch(ParticleCloudException e) {
//                    // We end up here if an error happens
//                    // Add code here to deal with errors
//                    Log.e(TAG, "Error: " + e.toString());
//                    return "Some error message";
//                }
//            }
//
//            protected void onPostExecute(String msg) {
//                // This code runs after the stuff in doInBackground finishes
//                // Add code here to do something after the Particle Cloud
//                // stuff is done
//            }
//        }.execute();

        new Thread(new Runnable() {

            @Override
            public void run() {
                while(true) {
                    i = i + 1;
                    if (i == 100) i = 0;
//                    System.out.print(i);
                }
            }
        }).start();

        handler = new Handler();
        handler.post(updateView);



//2b0025000b47363330353437, analogvalue
//
    }
    private Runnable updateView = new Runnable() {

        @Override
        public void run() {
//            while(true){
            if(i <= 100) {
//                System.out.println(clicked);
//                if(clicked == true) {
                variable.setText(Long.toString(retrieved));
                handler.postDelayed(this, 100);
//                }
            }

        }
    };


}
