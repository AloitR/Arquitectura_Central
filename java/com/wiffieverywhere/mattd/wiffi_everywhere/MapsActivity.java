package com.wiffieverywhere.mattd.wiffi_everywhere;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private static final LatLng wifi1 = new LatLng(41.4090553, 2.1616994);
    private static final LatLng wifi2 = new LatLng(41.3817109, 2.1437715);
    private static final LatLng wifi3 = new LatLng(41.4129246, 2.2036036);
    private static final LatLng wifi4 = new LatLng(41.4481092, 2.1770949);
    private Circle circle;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*
        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, supportMapFragment);
        fragmentTransaction.commit();
        supportMapFragment.getMapAsync(this);*/

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Barcelona = new LatLng(41.403657, 2.1742997);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Barcelona, 12));

        // Location Zoom
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();


        final Marker mwifi1 = mMap.addMarker(new MarkerOptions().position(wifi1).title("Wifi n#1532").snippet("Usuarios conectados: 3\n Velocidad: 23 Mb"));
        circle = mMap.addCircle(new CircleOptions() .center(new LatLng(41.4090553, 2.1616994)).radius(200).strokeWidth(5).strokeColor(Color.GREEN).clickable(false));
        final Marker mwifi2 = mMap.addMarker(new MarkerOptions().position(wifi2).title("Wifi n#213").snippet("Usuarios conectados: 0\n Velocidad: 10 Mb"));
        circle = mMap.addCircle(new CircleOptions() .center(new LatLng(41.3817109, 2.1437715)).radius(200).strokeWidth(5).strokeColor(Color.GREEN).clickable(false));
        final Marker mwifi3 = mMap.addMarker(new MarkerOptions().position(wifi3).title("Wifi n#23").snippet("Usuarios conectados: 8\n Velocidad: 100 Mb"));
        circle = mMap.addCircle(new CircleOptions() .center(new LatLng(41.4129246, 2.2036036)).radius(200).strokeWidth(5).strokeColor(Color.GREEN).clickable(false));
        final Marker mwifi4 = mMap.addMarker(new MarkerOptions().position(wifi4).title("Wifi n#7563").snippet("Usuarios conectados: 1\n Velocidad: 5 Mb"));
        circle = mMap.addCircle(new CircleOptions().center(new LatLng(41.4481092, 2.1770949)).radius(200).strokeWidth(5).strokeColor(Color.GREEN).clickable(false));

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0)
            {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                Context context = getApplicationContext();

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

    }


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE, android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        else if (mMap != null)
        {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick()
    {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location)
    {

    }
}
