package com.wiffieverywhere.mattd.wiffi_everywhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);

    }

    public void onClickButton1(View v)
    {
        //Toast.makeText(this, "Clicked on Button1", Toast.LENGTH_LONG).show();
        Intent intentConfig = new Intent(this, ConfigActivity.class);
        startActivity(intentConfig);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
