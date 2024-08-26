package com.managexr.grantdeviceownertest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends Activity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        Button button = findViewById(R.id.button);
        button.setOnClickListener((view) -> {
            textView.setText("Setting device owner permissions...");
            new Handler().postDelayed(this::setDeviceOwner, 250);
        });
    }

    private void setDeviceOwner() {
        try {
            Process p = Runtime.getRuntime().exec("dpm set-device-owner com.managexr.deviceownertest/.DeviceAdminReceiverTest");
            p.waitFor();

            BufferedReader outputReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String output = outputReader.readLine();

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String errorOutput = errorReader.readLine();

            textView.setText("Output: " + output + "\n\nError Output: " + errorOutput);
        } catch (Exception e) {
            textView.setText("Failed to set device owner permissions!\n\nException Message: " + e.getMessage());
        }
    }
}