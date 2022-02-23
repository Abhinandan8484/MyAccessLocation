package com.example.myaccesslocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    Geocoder geocoder;
    TextView txt1;
    double latitude;
    double longitude;
    List<Address> addressList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt1 = findViewById(R.id.txt1);

        geocoder = new Geocoder(this, Locale.ENGLISH);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Toast.makeText(getApplicationContext(), "In main", Toast.LENGTH_LONG).show();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);

        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        Criteria criteria=new Criteria();
        String provider=locationManager.getBestProvider(criteria,true);
        Location location=locationManager.getLastKnownLocation(provider);
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        Log.i("access","In Main");
        try {
            addressList=geocoder.getFromLocation(latitude,longitude,1);
            Log.i("access","In try");
            Toast.makeText(getApplicationContext(), "In try", Toast.LENGTH_LONG).show();

            if (addressList!=null && addressList.size()>0){

                String address=addressList.get(0).getAddressLine(0);
                String city=addressList.get(0).getLocality();
                String country=addressList.get(0).getCountryName();
                String zip=addressList.get(0).getPostalCode();
                String knownName=addressList.get(0).getFeatureName();
                Log.i("access",address);
                txt1.setText("Address "+address);
                Toast.makeText(getApplicationContext(), "Address"+address, Toast.LENGTH_LONG).show();

            }
            else{
                Log.i("access","In else");
                Toast.makeText(getApplicationContext(), "else", Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            Log.i("access","In catch");
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "catch", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}