package com.audiapp.inicial;


import com.audiapp.R;
import com.audiapp.globales.Strings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.os.Handler;
import android.content.Intent;

public class InicialActivity extends Activity
{

@Override
protected void onCreate(Bundle savedInstanceState)
    {
    super.onCreate(savedInstanceState);
    Log.i("App", "Aplicación iniciada");
    // Ocultar barra de título
    //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    // Poner en pantalla completa
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    // Asociar actividad al diseño
    setContentView(R.layout.activity_inicial);
    // Esconder barra de acción
    try
        {
        getActionBar().hide();
        }
    catch(NullPointerException npe)
        {
        Log.wtf("InicialActivity", "No hay barra de acción");
        }

    // Cambiar de actividad, inicialmente por defecto a Login, modificar según criterios de desarrollo tomados
    new Handler().postDelayed(new NextActivity(this), 5000);
    Strings.urlBase("L");
    }
}

class NextActivity implements Runnable
{
private InicialActivity instancia;

NextActivity(InicialActivity param)
    {
    instancia = param;
    }

@Override
public void run()
    {
    Intent i = new Intent(instancia, InicialActivity.class);

    instancia.startActivity(i);
    instancia.finish();
    }
}
