package com.audiapp.inicial;


import com.audiapp.R;

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

    // Todo: Comprobar si hay datos de login y dar valor al flag
    boolean hayDatosLogin = false;
    // Cambiar de actividad, inicialmente por defecto a Login, modificar según criterios de desarrollo tomados
    new Handler().postDelayed(new SiguienteActivity(this, hayDatosLogin), 5000);
    }
}

class SiguienteActivity implements Runnable
{
private InicialActivity instancia;
private boolean         hayDatosLogin;

SiguienteActivity(InicialActivity param_instancia, boolean param_hayDatosLogin)
    {
    instancia       =   param_instancia;
    hayDatosLogin   =   param_hayDatosLogin;
    }

@Override
public void run()
    {
    Intent i;
    if(hayDatosLogin)
        {
        // Todo: modificar siguiente línea según esté desarrollada la funcionalidad necesaria
        i = new Intent(instancia, InicialActivity.class);
        }
    else
        {
        i = new Intent(instancia, ElegirInicioActivity.class);
        }

    instancia.startActivity(i);
    instancia.finish();
    }
}
