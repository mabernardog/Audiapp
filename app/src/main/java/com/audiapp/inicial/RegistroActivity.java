package com.audiapp.inicial;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.audiapp.R;
import com.audiapp.apisgu.API_SGU;
import com.audiapp.apisgu.AudiappConfiCerti;
import com.audiapp.apisgu.AudiappHostnameVerifier;
import com.audiapp.globales.Strings;
import com.audiapp.modelo.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegistroActivity extends Activity
{

// Contador de edits válidos (pos1:
private boolean[] editsValidos;
// Obtener referencias
//      Email
private TextInputLayout ref_til_email;
private EditText ref_edit_email;
//      Nick;
private TextInputLayout ref_til_nick;
private EditText ref_edit_nick;
//      Contraseña;
private TextInputLayout ref_til_passw;
private EditText ref_edit_passw;
//      Botón enviar registro;
private Button ref_button_doRegisstro;


@Override
protected void onCreate(Bundle savedInstanceState)
    {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registro);

    definirListeners();
    }

private void definirListeners()
    {
    // Inicializar array de editsValidos
    editsValidos = new boolean[3];
    Arrays.fill(editsValidos, Boolean.FALSE);
    // Obtener referencias
    //      Email
    ref_til_email  =  findViewById(R.id.inputlayout_email);
    ref_edit_email =  findViewById(R.id.edit_reg_email);
    //      Nick
    ref_til_nick   =  findViewById(R.id.inputlayout_nick);
    ref_edit_nick  =  findViewById(R.id.edit_reg_nick);
    //      Contraseña
    ref_til_passw  =  findViewById(R.id.inputlayout_password);
    ref_edit_passw =  findViewById(R.id.edit_reg_password);
    //      Botón enviar registro
    ref_button_doRegisstro = findViewById(R.id.button_doRegistro);
    // Todo: eliminar tras finalizar
    ref_button_doRegisstro.setEnabled(true);
    // Crear listener para validar email
    ref_edit_email.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
            {
            // Si ya tenía el foco:
            if(hasFocus) return;    // Finalizar validación sin hacer nada
            String mailPasado = ref_edit_email.getText().toString();
            // Si no es válido
            if(!esValidoEmail(mailPasado))
                {
                // Si está vacío
                if(mailPasado.equals(""))
                    {
                    // No mostrar error
                    ref_til_email.setError(null);
                    ref_til_email.setErrorEnabled(false);
                    }
                // Si no (valida por incorrecto)
                else
                    {
                    // Mostrar error
                    ref_til_email.setError("Dirección de correo electrónico incorrecta");
                    }
                // Anotarlo como inválido
                editsValidos[0] = false;
                }
            // Si es válido o está vacío
            else
                {
                // No mostrar error
                ref_til_email.setError(null);
                ref_til_email.setErrorEnabled(false);
                // Anotarlo como válido
                editsValidos[0] = true;
                }

            // Si se puede activar el botón doRegistro
            if(esRegistrable(editsValidos))
                {
                // Activarlo
                ref_button_doRegisstro.setEnabled(true);
                }
            // Si no
            else
                {
                // Desactivarlo
                ref_button_doRegisstro.setEnabled(false);
                }
            }
        }
    );

    // Crear listener para validar nick
    ref_edit_nick.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
            {
            // Si ya tenía el foco:
            if(hasFocus) return;    // Finalizar validación sin hacer nada
            String nickPasado = ref_edit_nick.getText().toString();
            // Si no es válido
            if(!esValidoNick(nickPasado))
                {
                // Si está vacío
                if(nickPasado.equals(""))
                    {
                    // No mostrar error
                    ref_til_nick.setError(null);
                    ref_til_nick.setErrorEnabled(false);
                    }
                // Si no (valida por incorrecto)
                else
                    {
                    // Mostrar error
                    ref_til_nick.setError("El nombre de usuario debe tener al menos 3 caracteres");
                    }
                // Anotarlo como inválido
                editsValidos[1] = false;
                }
            // Si es válido
            else
                {
                // No mostrar error
                ref_til_nick.setError(null);
                ref_til_nick.setErrorEnabled(false);
                // Anotarlo como válido
                editsValidos[1] = true;
                }

            // Si se puede activar el botón doRegistro
            if(esRegistrable(editsValidos))
                {
                // Activarlo
                ref_button_doRegisstro.setEnabled(true);
                }
            // Si no
            else
                {
                // Desactivarlo
                ref_button_doRegisstro.setEnabled(false);
                }
            }
        }
    );

    // Crear listener para validar contraseña
    ref_edit_passw.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
            {
            // Si ya tenía el foco:
            if(hasFocus) return;    // Finalizar validación sin hacer nada
            String passwPasado = ref_edit_passw.getText().toString();
            // Si no es válido
            if(!esValidoPassword(passwPasado))
                {
                // Si está vacío
                if(passwPasado.equals(""))
                    {
                    // No mostrar error
                    ref_til_passw.setError(null);
                    ref_til_passw.setErrorEnabled(false);
                    }
                // Si no (valida por incorrecto)
                else
                    {
                    // Mostrar error
                    ref_til_passw.setError("La contraseña debe tener al menos 8 caracteres");
                    }
                // Anotarlo como inválido
                editsValidos[2] = false;
                }
            // Si es válido o está vacío
            else
                {
                // No mostrar error
                ref_til_passw.setError(null);
                ref_til_passw.setErrorEnabled(false);
                // Anotarlo como válido
                editsValidos[2] = true;
                }

            // Si se puede activar el botón doRegistro
            if(esRegistrable(editsValidos))
                {
                // Activarlo
                ref_button_doRegisstro.setEnabled(true);
                }
            // Si no
            else
                {
                // Desactivarlo
                ref_button_doRegisstro.setEnabled(false);
                }
            }
        }
    );

    // Listener especial para el último edit (contraseña)
    LinearLayout grupoEdits = findViewById(R.id.linearLayout_registro);
    final TextInputLayout ultimoHijo = (TextInputLayout) grupoEdits.getChildAt(grupoEdits.getChildCount()-1);
    final EditText editUltimoHijo = ultimoHijo.getEditText();
    assert editUltimoHijo != null;
    editUltimoHijo.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent evento)
            {
            switch(actionId)
                {
                // Caso: intro pulsado
                case EditorInfo.IME_ACTION_DONE:
                    // Quitar foco
                    editUltimoHijo.clearFocus();
                    return true;
                // Por defecto
                default:
                    return false;
                }
            }
        }
    );

    }

private boolean esValidoEmail(String param_direccion)
    {
    // Pensar si usar mejor JavaMail
    return Patterns.EMAIL_ADDRESS.matcher(param_direccion).matches();
    }

private boolean esValidoNick(String param_nick)
    {
    return param_nick.length() >= 3;
    }

private boolean esValidoPassword(String param_passw)
    {
    return param_passw.length() >= 8;
    }

private boolean esRegistrable(boolean[] param_validados)
    {
    for(boolean editValido : param_validados)
        {
        if(!editValido) return false;
        }
    return true;
    }

public void onClickRegistro(View v)
    {
    Toast registrandoMsg = Toast.makeText(getApplicationContext(), "Creando la cuenta...", Toast.LENGTH_SHORT);
    registrandoMsg.show();
    Usuario datosUsuario = new Usuario(ref_edit_email.getText().toString(), ref_edit_nick.getText().toString(), ref_edit_passw.getText().toString());
    AudiappConfiCerti confiCerti = null;
    try
        {
        confiCerti = new AudiappConfiCerti(getResources().openRawResource(R.raw.audiapp));
        }
    catch(Exception e)
        {
        String ret = e.getMessage();
        }

    OkHttpClient client = new OkHttpClient.Builder().hostnameVerifier(new AudiappHostnameVerifier()).sslSocketFactory(confiCerti.getSslFactoria(), confiCerti.getTrustManager()).build();
    Gson gson = new GsonBuilder().setLenient().create();
    Retrofit clienteSGU = new Retrofit.Builder().baseUrl(Strings.urlBase("L") + "/api/sgu/").
                                addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();
    API_SGU apiSGU = clienteSGU.create(API_SGU.class);
    apiSGU.hacerRegistro(datosUsuario).
            enqueue(new Callback<String>()
                {
                @Override
                public void onResponse(Call<String> llamada, Response<String> respuesta)
                    {
                    Toast response = Toast.makeText(getApplicationContext(), respuesta.body(), Toast.LENGTH_SHORT);
                    int re = respuesta.code();
                    response.show();
                    }

                @Override
                public void onFailure(Call<String> llamada, Throwable t)
                    {
                    Toast failure = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
                    failure.show();
                    }
                });
    }

}
