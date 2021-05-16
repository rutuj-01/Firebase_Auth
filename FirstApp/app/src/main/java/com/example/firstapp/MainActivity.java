package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {
    Switch simpleSwitch;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button driveButton=(Button )findViewById(R.id.driveButton);
        Button potholeButton=(Button )findViewById(R.id.potholeButton);

        driveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DriveActivity.class));
                finish();
            }
        });
        //submit= (Button) findViewById(R.id.submit);
        potholeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PotholeActivity.class));
            }
        });
    }






//    public void startService(View view) {
//        Log.i("UIN","IN");
//        startService(new Intent(getBaseContext(), MyService.class));
//    }
//
//    // Method to stop the service
//    public void stopService(View view) {
//        stopService(new Intent(getBaseContext(), MyService.class));
//    }
}