package com.wiffieverywhere.mattd.wiffi_everywhere;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import im.delight.android.location.SimpleLocation;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    //private static final LatLng wifi1 = new LatLng(41.4090553, 2.1616994);
    //private static final LatLng wifi2 = new LatLng(41.3817109, 2.1437715);
    //private static final LatLng wifi3 = new LatLng(41.4129246, 2.2036036);
    //private static final LatLng wifi4 = new LatLng(41.4481092, 2.1770949);
    private Circle circle;

    private SimpleLocation location;


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

        // Geting current location
        location = new SimpleLocation(this);
        if (!location.hasLocationEnabled())
        {
            SimpleLocation.openSettings(this);
        }

        //final double latitude = location.getLatitude();
        //final double longitude = location.getLongitude();

        // GeoFire

        GeoFireTest();

    }

    private void GeoFireTest()
    {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("geofire");
        final GeoFire geoFire = new GeoFire(ref);


        //testNotifications(geoFire);


        // Rango a mostrar los Markers
        GeoQuery geoQueryMarkers = geoFire.queryAtLocation(new GeoLocation(location.getLatitude(), location.getLongitude()), 6.0);
        geoQueryMarkers.addGeoQueryEventListener(new GeoQueryEventListener()
        {
            @Override
            public void onKeyEntered(String key, GeoLocation location1)
            {
                //System.out.println(String.format("Key %s entered the search area at [%f,%f]", key, location.latitude, location.longitude));

                LatLng meetLatLng = new LatLng(location1.latitude, location1.longitude);
                mMap.addMarker(new MarkerOptions().position(meetLatLng));

                Query locationQuery = FirebaseDatabase.getInstance().getReference().child("geofire");
                locationQuery.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot snap : dataSnapshot.getChildren())
                        {
                            Double latitude = (Double) snap.child("l/0").getValue();
                            Double longitude = (Double) snap.child("l/1").getValue();
                            LatLng meetLatLng = new LatLng(latitude, longitude);

                            mMap.addMarker(new MarkerOptions().position(meetLatLng));
                            mMap.addCircle(new CircleOptions().center(meetLatLng).radius(100).strokeWidth(5).strokeColor(Color.RED).clickable(false));

                            Location near_locations = new Location("nearlocation");
                            near_locations.setLatitude(latitude);
                            near_locations.setLongitude(longitude);

                            Location actual_location = new Location("actuallocation");
                            near_locations.setLatitude(location.getLatitude());
                            near_locations.setLongitude(location.getLongitude());

                            float distance = near_locations.distanceTo(actual_location);
                            //Toast.makeText(MapsActivity.this, String.valueOf(distance), Toast.LENGTH_SHORT).show();

                            // TODO Notificar en funcion de la distancia

                            if(distance<100000)
                            {
                                testNotifications(geoFire);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {

                    }
                });
            }

            @Override
            public void onKeyExited(String key)
            {
                //System.out.println(String.format("Key %s is no longer in the search area", key));
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location)
            {
                //System.out.println(String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude));
            }

            @Override
            public void onGeoQueryReady()
            {
            }

            @Override
            public void onGeoQueryError(DatabaseError error)
            {
                //Toast.makeText(MainActivity.this, "There was an error with this query " + error, Toast.LENGTH_SHORT).show();
            }
        });

        //(new Handler()).postDelayed(this::GeoFireTest, 10000);


    }

    private boolean nearAWifi(GeoFire geoFire)
    {
        return true;
    }

    private void testNotifications(GeoFire geoFire)
    {

        // prepare intent which is triggered if the
        // notification is selected

        Intent intent = new Intent(this, MapsActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(this)
                .setContentTitle("You're next to a wifi.")
                .setContentText("SSID: Pending \n " +
                                "Password: Pending")
                .setSmallIcon(R.drawable.quantum_ic_keyboard_arrow_down_white_36)
                .setContentIntent(pIntent)
                .setAutoCancel(true).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
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
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng ActualLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ActualLocation, 12));

        // Location Zoom
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();


        //final Marker mwifi1 = mMap.addMarker(new MarkerOptions().position(wifi1).title("Wifi n#1532").snippet("Usuarios conectados: 3\n Velocidad: 23 Mb"));
        //circle = mMap.addCircle(new CircleOptions() .center(new LatLng(41.4090553, 2.1616994)).radius(200).strokeWidth(5).strokeColor(Color.GREEN).clickable(false));
        //final Marker mwifi2 = mMap.addMarker(new MarkerOptions().position(wifi2).title("Wifi n#213").snippet("Usuarios conectados: 0\n Velocidad: 10 Mb"));
        //circle = mMap.addCircle(new CircleOptions() .center(new LatLng(41.3817109, 2.1437715)).radius(200).strokeWidth(5).strokeColor(Color.GREEN).clickable(false));
        //final Marker mwifi3 = mMap.addMarker(new MarkerOptions().position(wifi3).title("Wifi n#23").snippet("Usuarios conectados: 8\n Velocidad: 100 Mb"));
        //circle = mMap.addCircle(new CircleOptions() .center(new LatLng(41.4129246, 2.2036036)).radius(200).strokeWidth(5).strokeColor(Color.GREEN).clickable(false));
        //final Marker mwifi4 = mMap.addMarker(new MarkerOptions().position(wifi4).title("Wifi n#7563").snippet("Usuarios conectados: 1\n Velocidad: 5 Mb"));
        //circle = mMap.addCircle(new CircleOptions().center(new LatLng(41.4481092, 2.1770949)).radius(200).strokeWidth(5).strokeColor(Color.GREEN).clickable(false));

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
        {

            @Override
            public View getInfoWindow(Marker arg0)
            {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker)
            {

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
