package com.audiapp.inicial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.audiapp.R;


public class ElegirInicioActivity extends Activity
{

@Override
protected void onCreate(Bundle savedInstanceState)
    {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_elegir_inicio);
    }

public void onClickRegistro(View v)
    {
    Intent i = new Intent(this, RegistroActivity.class);

    this.startActivity(i);
    this.finish();
    }

public void onClickLogin(View v)
    {
    Intent i = new Intent(this, LoginActivity.class);

    this.startActivity(i);
    this.finish();
    }
}
