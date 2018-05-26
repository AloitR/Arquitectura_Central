package com.wiffieverywhere.mattd.wiffi_everywhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class ConsultaActivity extends AppCompatActivity
{

    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        progressbar = (ProgressBar) findViewById(R.id.progressBar4);
        progressbar.setScaleY(3f);
        progressbar.setMax(100);
        progressbar.setProgress(70);
        // 70% de datos disponibles de un tope de... 100 GB?
    }
}
