package com.wiffieverywhere.mattd.wiffi_everywhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.webkit.WebView;


public class ConfigActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        WebView webView = new WebView(this);
        setContentView(webView);

        Bundle b = getIntent().getExtras();
        String value = "http://www.columbia.edu/~fdc/sample.html"; // or other values

        if(b != null)
            value = b.getString("key");

        webView.loadUrl(value);
    }
}
