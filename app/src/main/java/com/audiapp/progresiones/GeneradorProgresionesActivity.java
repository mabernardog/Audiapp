package com.audiapp.progresiones;


import android.os.Bundle;

import com.audiapp.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;


public class GeneradorProgresionesActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.toolbar_generadorProgresiones)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generador_progresiones);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

}
