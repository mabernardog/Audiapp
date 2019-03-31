package com.audiapp.inicial;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.audiapp.Audiapp;
import com.audiapp.R;
import com.audiapp.apisgu.API_SGU;
import com.audiapp.db.GestorDB;
import com.audiapp.db.GestorUsuarioDB;
import com.audiapp.modelo.InfoDBAudiappi;
import com.audiapp.modelo.Usuario;
import com.audiapp.placeholderfun.PlaceholderActivity;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Objects.requireNonNull(getActionBar()).hide();
        }
    catch(NullPointerException npe)
        {
        Log.wtf("InicialActivity", "No hay barra de acción");
        }
    /* Decidir siguiente actividad a lanzar */
    // Leer la tabla Usuario para ver si hay alguno ya creado
    List<Usuario> listaUsuarios = ((GestorUsuarioDB) Objects.requireNonNull(new GestorDB().acceder("Usuario"))).leerTodos();
    // Si hay usuarios en la lista
    assert listaUsuarios != null;
    if(listaUsuarios.size() > 0)
        {
        final InicialActivity instancia =  this;
        // Intentar logear con el primer usuario que haya (en teoría, es el único que hay)
        API_SGU apiSGU = Audiapp.getInstancia().getRetroAudiappFit().getCliente().create(API_SGU.class);
        apiSGU.hacerLogin(listaUsuarios.get(0)).
                enqueue(new Callback<InfoDBAudiappi>()
                {
                @Override
                public void onResponse(@NonNull Call<InfoDBAudiappi> llamada, @NonNull Response<InfoDBAudiappi> respuesta)
                    {
                    // Si se recibe una respuesta
                    InfoDBAudiappi mensaje = respuesta.body();
                    // Si la respuesta es que hay un fallo al logear
                    assert mensaje != null;
                    if(mensaje.getTag().equals("FALLO"))
                        {
                        // Se irá a la actividad de login
                        new Handler().postDelayed(new SiguienteActivity(instancia, "DECIDIR"), 2000);
                        }
                    // Si no (se ha logeado)
                    else
                        {
                        // Poner el token en retrofit
                        Audiapp.getInstancia().getRetroAudiappFit().agregarJWT(mensaje.getDescripcion());
                        // Se irá a la primera actividad del usuario
                        new Handler().postDelayed(new SiguienteActivity(instancia, "TRABAJO"), 2000);
                        }
                    }
                @Override
                public void onFailure(@NonNull Call<InfoDBAudiappi> llamada, @NonNull Throwable t)
                    {
                    // No se ha podido contactar con el servidor: se irá a actividad de login
                    new Handler().postDelayed(new SiguienteActivity(instancia, "LOGIN"), 2000);
                    }
                });
        }
    // Si no los hay, ir a ELEGIR si login o registro
    else
        {
        new Handler().postDelayed(new SiguienteActivity(this, "DECIDIR"), 5000);
        }
    }
}

class SiguienteActivity implements Runnable
{
private final InicialActivity instancia;
private final String          actALanzar;

// Constructor en el caso de que no haya una lista de usuarios
SiguienteActivity(InicialActivity param_instancia, String param_actALanzar)
    {
    instancia       =   param_instancia;
    actALanzar      =   param_actALanzar;
    }

@Override
public void run()
    {
    Intent i;
    // Ver qué hay que lanzar
    switch (actALanzar)
        {
        // Si se lanzará LOGIN crear su intent
        case "LOGIN":
            i = new Intent(instancia, LoginActivity.class);
            break;
        // Si no, si se lanzará TRABAJO crear su intent
        case "TRABAJO":
            i = new Intent(instancia, PlaceholderActivity.class);
            break;
        // Si no, se lanzará crear intent para lanzar intent de DECIDIR
        default:
            i = new Intent(instancia, ElegirInicioActivity.class);
            break;
        }
    instancia.startActivity(i);
    instancia.finish();
    }
}
