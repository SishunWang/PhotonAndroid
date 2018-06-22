package com.example.photon.photonandroid;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import io.particle.android.sdk.utils.ui.Toaster;

public class MainActivity extends Activity  {

     private UsbManager usbManager = null;
     private UsbDevice device = null;
     private UsbDeviceConnection connection = null;
     public UsbSerialDevice serialPort = null;
     private int header = 1;


    //     private  UsbSerialDevice  usbSerialDevice = null;
//    private static final String TAG = "Hello";
//
    private Button startButton, stopButton, sendButton, readButton;
    private TextView textView;
//    private boolean clicked = false;
//    private int i = 0;
//    private long retrieved = 0;
//    private final int SIZE =4096;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
//    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
//
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (ACTION_USB_PERMISSION.equals(action)) {
//                synchronized (this) {
//                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//
//                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//                        if(device != null){
//                            //call method to set up device communication
//                        }
//                    }
//                    else {
//                        Log.d(TAG, "permission denied for device " + device);
//                    }
//                }
//            }
//        }
//    };
////    private List<ParticleDevice> devices;
//    Handler handler;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopButton = findViewById(R.id.buttonStop);
        startButton = findViewById(R.id.buttonStart);
        sendButton = findViewById(R.id.buttonSend);
        readButton = findViewById(R.id.buttonRead);

        textView = findViewById(R.id.textView);

    }


//        connect = findViewById(R.id.connect);
//
//        check =  findViewById(R.id.check);
//        check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(clicked == false ) clicked = true;
//            }
//        });
//
//        variable =  findViewById(R.id.variable);
//
//        final UsbManager mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
//
//        final PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
//        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
//        registerReceiver(mUsbReceiver, filter);
//
//
//        View.OnClickListener connectListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
//                        //mPermissionIntent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(ACTION_USB_PERMISSION), 0);
//
//                        final byte[] bytes =  new byte[SIZE];
//                        final int TIMEOUT = 1000;
//                        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
//                        Map<String, UsbDevice> devices = manager.getDeviceList();
//                        UsbDevice mDevice = null;
//                        Toaster.s(MainActivity.this, "looking for devices");
//                        boolean find = false;
//                        for(String key: devices.keySet()){
//                            mDevice = devices.get(key);
//                            find = true;
//                        }
//                        while (find == true) {
//
//                            if(mUsbManager.hasPermission(mDevice)) {
//                                UsbInterface intf = mDevice.getInterface(0);
//                                final UsbEndpoint endpoint = intf.getEndpoint(0);
//                                final UsbDeviceConnection connection = manager.openDevice(mDevice);
////                                Toaster.s(MainActivity.this, Integer.toString(endpoint.getMaxPacketSize()));
////
//                                connection.claimInterface(mDevice.getInterface(0), true);
//                                new Thread(new Runnable() {
//
//
//                                    @Override
//                                    public void run() {
//                                        while (true) {
//
//                                            connection.bulkTransfer(endpoint, bytes,SIZE, TIMEOUT);
////                                            String t = new String(bytes);
////                                            StringBuilder str = new StringBuilder();
////                                            int value = 0;
////                                            for (int i = 2; i < SIZE; i++) {
//////                                                if (bytes[i] != 0) {
//////                                                   value = (value << 8) | bytes[i];
//////                                                } else {
////////                                                    Log.e("Database", "M=" + str.toString());
//////                                                    break;
//////                                                }
//////                                            }
//                                            StringBuilder test =  new StringBuilder();
//                                            long value = 0;
////                                            String test = new String();
////                                            value = ByteBuffer.wrap(bytes).get();
////                                            test = Long.toBinaryString(value);
//                                            for (int i = 0; i < SIZE; i++) {
//                                                if (bytes[i] != 0) {
//////                                                    value  = Character.getNumericValue( bytes[i]);
//                                                    short tmp = (short) bytes[i];
////                                                    if (tmp < 0) {
////                                                        tmp += 256;
////                                                    }
//                                                    value =  tmp;
////                                                    Toaster.s(MainActivity.this, Long.toString(value));
//////
//                                                    test.append(value);
//                                                    test.append('/');
////
//                                                } else {
////                                                    Log.e("Database", "M=" + str.toString());
//                                                    break;
//                                                }
//                                            }
//
//
//                                            retrieved = value;
////                                            Toaster.s(MainActivity.this, t.toString());
//                                            Toaster.s(MainActivity.this, test.toString());
//                                        }
//                                    }
//                                }).start();
////
//                                Toaster.s(MainActivity.this, "Got Permission");
//                            break;
//                            }
//                            else{
//                                mUsbManager.requestPermission(mDevice, mPermissionIntent);
//                            }
//                        }
//                    }
//                }).start();
//            }
//        };
//        connect.setOnClickListener(connectListener);
//
////        new AsyncTask<Void, Void, String>(){
////
////            protected String doInBackground(Void... params) {
////                try {
////                    // Add your Particle SDK code here to do something
////                    // with the Particle Cloud
//////                    System.out.println("Logged In");
////
////                    return "Some success message";
////                }
////                catch(ParticleCloudException e) {
////                    // We end up here if an error happens
////                    // Add code here to deal with errors
////                    Log.e(TAG, "Error: " + e.toString());
////                    return "Some error message";
////                }
////            }
////
////            protected void onPostExecute(String msg) {
////                // This code runs after the stuff in doInBackground finishes
////                // Add code here to do something after the Particle Cloud
////                // stuff is done
////            }
////        }.execute();
//
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                while(true) {
//                    i = i + 1;
//                    if (i == 100) i = 0;
////                    System.out.print(i);
//                }
//            }
//        }).start();
//
//        handler = new Handler();
//        handler.post(updateView);
//
//
//
////2b0025000b47363330353437, analogvalue
////
//    }
//    private Runnable updateView = new Runnable() {
//
//        @Override
//        public void run() {
////            while(true){
//            if(i <= 100) {
////                System.out.println(clicked);
////                if(clicked == true) {
//                variable.setText(Long.toString(retrieved));
//                handler.postDelayed(this, 100);
////                }
//            }
//
//        }
//    };
    private void tvAppend(TextView tv, CharSequence text) {
        final TextView ftv = tv;
        final CharSequence ftext = text;
//        tv.setText(text.toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                ftv.append(ftext);
                ftv.setText(ftext.toString());
            }
        });
    }

    public void onClickStart(View view){
        Toaster.s(MainActivity.this,"start");
        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        Map<String, UsbDevice>  usbDevices = usbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (String key : usbDevices.keySet()) {
                device = usbDevices.get(key);
                int deviceVID = device.getVendorId();
//                if (deviceVID == 0x2341)//Arduino Vendor ID
//                {
                    PendingIntent pi = PendingIntent.getBroadcast(this, 0,
                            new Intent(ACTION_USB_PERMISSION), 0);
                    usbManager.requestPermission(device, pi);
                    keep = false;
//                } else {
//                    connection = null;
//                    device = null;
//                }
//
                if (!keep)
                    break;
            }
        }
    }

    public final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { //Broadcast Receiver to automatically start and stop the Serial connection.
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_USB_PERMISSION)) {
                boolean granted =
                        intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                if (granted) {
                    Toaster.s(MainActivity.this,"getcast");
                    connection = usbManager.openDevice(device);
                    serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
//                    UartDevice uart = null;
                    if (serialPort != null) {
                        if (serialPort.open()) { //Set Serial Connection Parameters.
//                            setUiEnabled(true); //Enable Buttons in UI
                            serialPort.setBaudRate(9600);
                            serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                            serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            serialPort.read(mCallback); //
                            tvAppend(textView,"Serial Connection Opened!\n");

                        } else {
                            Log.d("SERIAL", "PORT NOT OPEN");
                        }
                    } else {
                        Log.d("SERIAL", "PORT IS NULL");
                    }
                } else {
                    Log.d("SERIAL", "PERM NOT GRANTED");
                }
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
                Toaster.s(MainActivity.this,"startButton");
                onClickStart(startButton);
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
                Toaster.s(MainActivity.this,"stopButton");
                onClickStop(stopButton);
            }
        };
    };

    public void onClickStop(View view) {
        Toaster.s(MainActivity.this,"stopButton");
        serialPort.close();
    }

    public void onClickSend(View view) {
        Toaster.s(MainActivity.this,"send");
        connection = usbManager.openDevice(device);
        serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
//                    UartDevice uart = null;
        if (serialPort != null) {
            if (serialPort.open()) { //Set Serial Connection Parameters.
//                            setUiEnabled(true); //Enable Buttons in UI
                serialPort.setBaudRate(9600);
                serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                serialPort.read(mCallback); //
                tvAppend(textView,"Serial Connection Opened!\n");

            } else {
                Log.d("SERIAL", "PORT NOT OPEN");
            }
        } else {
            Log.d("SERIAL", "PORT IS NULL");
        }
    }



    UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() {
        //Defining a Callback which triggers whenever data is read.
        @Override
        public void onReceivedData(byte[] arg0) {
            String data = null;
            try {
                data = new String(arg0, "UTF-8");
                data.concat("/n");
                if(header <= 0) {
                    tvAppend(textView, data);
                } else{
                    header = header - 1;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    };
}
