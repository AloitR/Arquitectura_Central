package com.wiffieverywhere.mattd.wiffi_everywhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Group;
import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class MainActivity extends AppCompatActivity
{

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    public static User usuario;
    private FirebaseUser user;
    private ArrayList<User> cart;
    private Map<String, Object> cartItems;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String mail = getIntent().getStringExtra("EXTRA_SESSION_ID");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        usuario = new User(mail);
        user = FirebaseAuth.getInstance().getCurrentUser();

        getInfo(mail);
        writeMail(mail);

        if (currentUser == null)
        {
            Toast.makeText(MainActivity.this, "Null user.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(MainActivity.this, currentUser.toString(), Toast.LENGTH_SHORT).show();
        }

    }


    public static void writeNewUser(DatabaseReference databaseReference, String userId, String name, String email, int accountType)
    {
        //User user = new User(email);
        //databaseReference.child("users").child(userId).setValue(user);
    }

    public static User getUserData()
    {
        return usuario;
    }


    private void writeMail(String mail)
    {
        /*
        Log.d("TAG ", "writemail");


        if (user != null)
        {

            mDatabase.child("userData").runTransaction(new com.google.firebase.database.Transaction.Handler()
            {
                @Override
                public com.google.firebase.database.Transaction.Result doTransaction(com.google.firebase.database.MutableData mutableData)
                {
                    boolean value = mutableData.child(user.getUid()).hasChildren();
                    if (!value)
                    {
                        mDatabase.child("userData").child(user.getUid()).setValue(usuario);
                        return com.google.firebase.database.Transaction.success(mutableData);
                    }
                    return null;
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot)
                {

                }
            });
            */

            cart = new ArrayList<>();
            cart.add(usuario);
            cartItems = new HashMap<>();
            cartItems.put("cartItems", cart);

            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference myRef = database.child("userData/" + user.getUid());

            myRef.child("email").setValue(usuario.email);
            //myRef.updateChildren(cartItems);
    }

    private void getInfo(String mail)
    {


        /*
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("userData/"+ user.getUid() );

        ref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                usuario = dataSnapshot.getValue(User.class);
                //System.out.println(usuario);
               // System.out.println("Pass: " + usuario.wifi_pass);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        */
    }

    public void onClickButton1(View v)
    {
        //Toast.makeText(this, "Clicked on Button1", Toast.LENGTH_LONG).show();

        //Intent intentWifi = new Intent(this, ConfigWifi.class);
        //startActivity(intentWifi);

        Intent intentWifi = new Intent(getBaseContext(), ConfigWifi.class);
        intentWifi.putExtra("MyArray", cart);

        startActivity(intentWifi);
    }

    public void onClickButton2(View v)
    {
        //Toast.makeText(this, "Clicked on Button2", Toast.LENGTH_LONG).show();
        Intent intentMaps = new Intent(this, MapsActivity.class);
        startActivity(intentMaps);
    }

    public void onClickButton3(View v)
    {
        //Toast.makeText(this, "Clicked on Button3", Toast.LENGTH_LONG).show();
        Intent consultaActivity = new Intent(this, ConsultaActivity.class);
        consultaActivity.putExtra("data", usuario.aviable_data);
        startActivity(consultaActivity);
    }

    public void onClickButton4(View v)
    {
        //Toast.makeText(this, "Clicked on Button4", Toast.LENGTH_LONG).show();
        Intent incrementActivity = new Intent(this, IncrementActivity.class);
        startActivity(incrementActivity);
    }

    public void onClickButton5(View v)
    {
        //Toast.makeText(this, "Clicked on Button5", Toast.LENGTH_LONG).show();
        Intent settingsActivity = new Intent(this, SettingsActivity_.class);
        startActivity(settingsActivity);
    }

    public void onClickButton6(View v)
    {
        //Toast.makeText(this, "Clicked on Button6", Toast.LENGTH_LONG).show();
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

