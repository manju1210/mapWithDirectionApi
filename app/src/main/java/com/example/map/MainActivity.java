

//package com.example.map;
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.example.map.directionhelpers.FetchURL;
//import com.example.map.directionhelpers.TaskLoadedCallback;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.Polyline;
//import com.google.android.gms.maps.model.PolylineOptions;
//
//public class MainActivity  extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback, LocationListener {
//
//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
//
//    GoogleMap map;
//    private MarkerOptions place1, place2;
//    Button getDirection;
//    private Polyline currentPolyline;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        getDirection = findViewById(R.id.btnGetDirection);
//
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkLocationPermission();
//        }
//
//        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapNearBy);
//        mapFragment.getMapAsync(this);
//
//        ///directions api
//        getDirection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url = getUrl(place1.getPosition(), place2.getPosition(), "driving");
//                new FetchURL(MainActivity.this).execute(url, "driving");
//            }
//        });
//
//        //27.658143,85.3199503
//        //27.667491,85.3208583
//        //dp -17.0029, 75.0713
//        //pune -18.5204, 73.8567
//        place1 = new MarkerOptions().position(new LatLng(18.501059, 73.862686)).title("Location 1");
//        place2 = new MarkerOptions().position(new LatLng(18.626076, 73.812157)).title("Location 2");
//
//    }
//
//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//        map = googleMap;
//        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        Log.d("mylog", "Added Markers");
//        map.addMarker(place1);
//        map.addMarker(place2);
//    }
//
//    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
//        // Origin of route
//        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
//        // Destination of route
//        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
//        // Mode
//        String mode = "mode=" + directionMode;
//        // Building the parameters to the web service
//        String parameters = str_origin + "&" + str_dest + "&" + mode;
//        // Output format
//        String output = "json";
//        // Building the url to the web service
//        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_map_key);
//        return url;
//    }
//
//    @Override
//    public void onTaskDone(Object... values) {
//        if (currentPolyline != null)
//            currentPolyline.remove();
//        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
//    }
//
//    public boolean checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//            } else {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//            }
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    @Override
//    public void onLocationChanged(@NonNull Location location) {
//
//    }
//}



package com.example.map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //initialize variables
    EditText etSource,etDestination;
    Button btTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///assign variables
        etSource = findViewById(R.id.et_source);
        etDestination = findViewById(R.id.et_destination);
        btTrack = findViewById(R.id.bt_track);

        btTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///get value from edit text
                String sSource = etSource.getText().toString().trim();
                String sDestination = etDestination.getText().toString().trim();

                ///check condition
                if (sSource.equals("") && sDestination.equals("")){
                    Toast.makeText(getApplicationContext(), "Enter both locations..",Toast.LENGTH_SHORT).show();
                }else{
                    DisplayTrack(sSource,sDestination);
                }
            }
        });
    }

    private void DisplayTrack(String sSource, String sDestination) {
        //if the device does not have map installed, then redirect to play store
        try {
            ///google map installed
            //initialize uri
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + sSource + "/" +sDestination);
            //Initialize intent with action view
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            //set package
            intent.setPackage("com.google.android.apps.maps");
            //set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            //Initialize intent with action view
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            //set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}