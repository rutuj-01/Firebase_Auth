package com.example.firstapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//import com.facebook.stetho.Stetho;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;


public class PotholeActivity extends AppCompatActivity implements SensorEventListener, LocationListener {
    TextView x, y, z, record, t1, t2;
    Button start, stop;
    //    SQLiteDatabase SQLITEDATABASE ;     //this is database
//    String SQLiteQuery;
//    String Xacc;
//    String Yacc;
//    String Zacc;
    Boolean flag = false, butt = false;
    //Vibrator v;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Stetho.initializeWithDefaults(this);
       // Toast.makeText(this, "asdasdadsads", Toast.LENGTH_SHORT).show();
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        setContentView(R.layout.activity_pothole);


        x = findViewById(R.id.x1);
        y = findViewById(R.id.y1);
        z = findViewById(R.id.z1);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        record = findViewById(R.id.record);
        start = findViewById(R.id.started);
        stop = findViewById(R.id.stoped);

//        x.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        Log.i("asd", String.valueOf(start));
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                butt = true;
                record.setText("Recording");
                record.setTextColor(Color.GREEN);
                stop.setBackgroundColor(Color.RED);
                start.setBackgroundColor(Color.GRAY);
                //start.setBackgroundColor(0x0106000d);


            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //flag = false;
                butt = false;
                record.setText("Not Recording");
                record.setTextColor(Color.RED);

                start.setBackgroundColor(Color.GREEN);
                stop.setBackgroundColor(Color.GRAY);


            }
        });


//bracket
    }

        void getLocation () {
            try {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
            } catch (SecurityException e) {
                e.printStackTrace();
            }


        }


        @Override
        public void onSensorChanged (SensorEvent event){
            x.setText("X: " + event.values[0] + "");
            y.setText("Y: " + event.values[1] + "");
            z.setText("Z: " + event.values[2] + "");
            if (flag && (Math.abs(event.values[0]) > 17 || Math.abs(event.values[1]) > 17 || Math.abs(event.values[2]) > 17) && (butt == flag)) {
                getLocation();
                flag = false;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flag = true;
                    }
                }, 5000);
            }
        }

        @Override
        public void onAccuracyChanged (Sensor sensor,int accuracy){

        }


        @Override
        public void onLocationChanged (Location location)
        {
            Toast.makeText(getApplicationContext(), "Hello from getLocation()", Toast.LENGTH_SHORT).show();
            t1.setText("YOUR LOCATION : " + "\n Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
            try {

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                t2.setText("\n" + addresses.get(0).getAddressLine(0) + ", " +
                        addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2));
                DatabaseReference firebase = FirebaseDatabase.getInstance().getReference().child("Pothole").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                firebase.push().setValue(t2.getText().toString());


            } catch (Exception e) {

            }
        }

        @Override
        public void onStatusChanged (String provider,int status, Bundle extras){

        }

        @Override
        public void onProviderEnabled (String provider){

        }

        @Override
        public void onProviderDisabled (String provider){
            Toast.makeText(PotholeActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();

        }
    }

