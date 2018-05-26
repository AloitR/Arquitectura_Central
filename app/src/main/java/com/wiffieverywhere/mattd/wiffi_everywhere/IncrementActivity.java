package com.wiffieverywhere.mattd.wiffi_everywhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class IncrementActivity extends AppCompatActivity
{

    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_increment);

        progressbar = (ProgressBar) findViewById(R.id.progressBar4);
        progressbar.setScaleY(3f);
        progressbar.setMax(100);
        progressbar.setProgress(70);
    }

    public void onClickButton1(View v)
    {
        Toast.makeText(this, "Redireccionando...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(IncrementActivity.this, ConfigActivity.class);
        Bundle b = new Bundle();
        b.putString("key", "https://www.paypal.com/es/home");
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);

    }

    public void onClickButton2(View v)
    {
        Toast.makeText(this, "Redireccionando...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(IncrementActivity.this, ConfigActivity.class);
        Bundle b = new Bundle();
        b.putString("key", "https://www.paypal.com/es/home");
        intent.putExtras(b);
        startActivity(intent);

    }

    public void onClickButton3(View v)
    {
        Toast.makeText(this, "Redireccionando...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(IncrementActivity.this, ConfigActivity.class);
        Bundle b = new Bundle();
        b.putString("key", "https://www.paypal.com/es/home");
        intent.putExtras(b);
        startActivity(intent);

    }
}
