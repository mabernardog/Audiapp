package com.audiapp.progresiones;


import android.os.Bundle;

import com.audiapp.R;

import androidx.appcompat.app.AppCompatActivity;


public class GeneradorProgresionesActivity extends AppCompatActivity
{

@Override
protected void onCreate(Bundle savedInstanceState)
    {
    super.onCreate(savedInstanceState);
    try {
        setContentView(R.layout.activity_generador_progresiones);
    }
    catch(Exception e)
    {
        return;
    }
    }

}
