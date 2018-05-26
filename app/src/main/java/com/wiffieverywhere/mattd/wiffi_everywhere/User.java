package com.wiffieverywhere.mattd.wiffi_everywhere;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class User implements Serializable
{
    public String username;
    public String email;
    public String wifi;
    public String wifi_pass;
    public LatLng wifi_location;
    public int aviable_data;
    public int shared_data;
    public int wifi_speed;

    public User()
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email)
    {
        this.email = email;
    }

    public void setWiffi(String wifi)
    {
        this.wifi = wifi;
    }

    public void setPassword(String password)
    {
        this.wifi_pass = password;
    }

    public void setSpeed(int speed)
    {
        this.wifi_speed = speed;
    }

}
