package com.example.photon.photonandroid;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import io.particle.android.sdk.cloud.*;
import io.particle.android.sdk.utils.*;


public class MainActivity extends Activity  {
    private static final String TAG = "Hello";

//    public static final String TAG = HttpClient.class.getSimpleName();

    private Button check, connect;
    private TextView variable;
    private boolean clicked = false;
    private int i = 0;
    private int retrieved = 0;
    private List<ParticleDevice> devices;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParticleCloudSDK.init(this);
        connect = findViewById(R.id.connect);

        check =  findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clicked == false ) clicked = true;
            }
        });

        variable =  findViewById(R.id.variable);



        View.OnClickListener connectListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            ParticleCloudSDK.getCloud().logIn("hc.eng@outlook.com", "Qinyou413");
//                            System.out.println("Logged In");
                            Toaster.s(MainActivity.this, "Logged in!");
                            devices = ParticleCloudSDK.getCloud().getDevices();
//                            Toaster.s(MainActivity.this, devices.get(0).getID());
                            while(true) {
                                retrieved = devices.get(0).getIntVariable("analogvalue");
                            }
                        } catch (ParticleCloudException e) {
                            e.printStackTrace();
                        } catch (ParticleDevice.VariableDoesNotExistException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
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
                variable.setText(Integer.toString(retrieved));
                handler.postDelayed(this, 100);
//                }
            }

        }
    };


}
