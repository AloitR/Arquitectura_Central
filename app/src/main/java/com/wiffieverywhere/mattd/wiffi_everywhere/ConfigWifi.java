package com.wiffieverywhere.mattd.wiffi_everywhere;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.schibstedspain.leku.LocationPickerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import im.delight.android.location.SimpleLocation;

public class ConfigWifi extends AppCompatActivity implements View.OnClickListener
{
    User usuario;
    EditText ssid;
    EditText pass;
    EditText speed;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseUser user;

    private ArrayList<User> wifi;
    private Map<String, Object> wifiItems;
    private SimpleLocation location;
    private double selectedlatitude;
    private double selectedlongitude;
    private boolean locationUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        // Geting current location
        location = new SimpleLocation(this);
        if (!location.hasLocationEnabled())
        {
            SimpleLocation.openSettings(this);
        }


        // EditTexts
        ssid = (EditText) findViewById(R.id.editText1);
        pass = (EditText) findViewById(R.id.editText);
        speed = (EditText) findViewById(R.id.editText2);

        // Buttons
        Button btn_location = (Button) findViewById(R.id.button1);
        Button btn_update = (Button) findViewById(R.id.button8);

        // Firebase
        String mail = getIntent().getStringExtra("EXTRA_SESSION_ID");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        //usuario = new User(mail);
        usuario = MainActivity.getUserData();
        user = FirebaseAuth.getInstance().getCurrentUser();

        btn_location.setOnClickListener(this);
        btn_update.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();

        if (i == R.id.button1)
        {
            Toast.makeText(ConfigWifi.this, "Pressed Location", Toast.LENGTH_SHORT).show();

            final double latitude = location.getLatitude();
            final double longitude = location.getLongitude();

            Intent intent = new LocationPickerActivity.Builder()
                    .withLocation(latitude, longitude)
                    .withGeolocApiKey("AIzaSyCIrTKdqQkONlmxWuisVS-lW0vIGah4PaM")
                    .withSearchZone("es_ES")
                    .shouldReturnOkOnBackPressed()
                    .withStreetHidden()
                    .withCityHidden()
                    .withZipCodeHidden()
                    .withSatelliteViewHidden()
                    .build(getApplicationContext());
            intent.putExtra(LocationPickerActivity.LAYOUTS_TO_HIDE, "street|city|zipcode");

            startActivityForResult(intent, 1);
        }

        if(i == R.id.button8)
        {
            Toast.makeText(ConfigWifi.this, "Data saved", Toast.LENGTH_SHORT).show();
            //user.wiffi = ssid.getText().toString();
            writeConfig();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                selectedlatitude = data.getDoubleExtra(LocationPickerActivity.LATITUDE, 0);
                selectedlongitude = data.getDoubleExtra(LocationPickerActivity.LONGITUDE, 0);
                String address = data.getStringExtra(LocationPickerActivity.LOCATION_ADDRESS);
                String postalcode = data.getStringExtra(LocationPickerActivity.ZIPCODE);
                Bundle bundle = data.getBundleExtra(LocationPickerActivity.TRANSITION_BUNDLE);
                Address fullAddress = data.getParcelableExtra(LocationPickerActivity.ADDRESS);
                if(fullAddress != null)
                {
                    //Log.d("FULL ADDRESS****", fullAddress.toString());
                }

                locationUpdated = true;
            }
            if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(ConfigWifi.this, "Ubicacion no seleccionada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void writeConfig()
    {

        //wifi = (ArrayList<User>) getIntent().getSerializableExtra("MyArray");

        if(!TextUtils.isEmpty(pass.getText()))
        {
            usuario.setPassword(pass.getText().toString());
        }

        if(!TextUtils.isEmpty(speed.getText()))
        {
            usuario.setSpeed(Integer.parseInt(speed.getText().toString()));
        }

        if(!TextUtils.isEmpty(ssid.getText()))
        {
            usuario.setWiffi(ssid.getText().toString());
        }

        wifi = new ArrayList<>();
        wifi.add(usuario);
        wifiItems = new HashMap<>();
        wifiItems.put("wifiItems", wifi);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("userData/" + user.getUid());
        myRef.updateChildren(wifiItems);


        if(locationUpdated)
        {
            updateLocation();
        }
        else
        {

        }


        /*
        usuario.setWiffi(ssid.getText().toString());
        usuario.setPassword(pass.getText().toString());
        if(TextUtils.isEmpty(speed.getText()))
        {
            Toast.makeText(ConfigWifi.this, "Speed can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        usuario.setSpeed( Integer.parseInt(String.valueOf(speed.getText())) );

        if (user != null)
        {
            mDatabase.child("userData").runTransaction(new Transaction.Handler()
            {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData)
                {
                    boolean value = mutableData.child(user.getUid()).hasChildren();
                    //System.out.println("New value : " + value);
                    if (!value)
                    {
                        mDatabase.child("userData").child(user.getUid()).setValue(usuario);
                        return Transaction.success(mutableData);
                    }
                    return null;
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot)
                {

                }
            });
        }*/

    }

    private void updateLocation()
    {
        // GeoFire

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("geofire");
        GeoFire geoFire = new GeoFire(ref);

        geoFire.setLocation(user.getUid(), new GeoLocation(selectedlatitude, selectedlongitude), new GeoFire.CompletionListener()
        {
            @Override
            public void onComplete(String key, DatabaseError error)
            {
                if (error != null)
                {
                    Toast.makeText(ConfigWifi.this, "Error saving location", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ConfigWifi.this, "Location saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        location.beginUpdates();
    }

}
