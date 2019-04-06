package com.audiapp.inicial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.audiapp.Audiapp;
import com.audiapp.R;
import com.audiapp.apisgu.API_SGU;
import com.audiapp.db.GestorDB;
import com.audiapp.db.GestorUsuarioDB;
import com.audiapp.globales.Strings;
import com.audiapp.modelo.InfoDBAudiappi;
import com.audiapp.modelo.Usuario;
import com.audiapp.progresiones.GeneradorProgresionesActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.inputmethod.InputMethodManager.RESULT_UNCHANGED_SHOWN;


public class LoginActivity extends AppCompatActivity {
    // Obtener referencias
    //      Nick;
    @Nullable
    @BindView(R.id.inputlayout_login_nick)
    TextInputLayout ref_til_nick;
    @Nullable
    @BindView(R.id.edit_login_nick)
    EditText ref_edit_nick;
    //      Contraseña;
    @Nullable
    @BindView(R.id.inputlayout_login_password)
    TextInputLayout ref_til_passw;
    @Nullable
    @BindView(R.id.edit_login_password)
    EditText ref_edit_passw;
    //      Botón enviar registro;
    @Nullable
    @BindView(R.id.button_doLogin)
    Button ref_button_doLogin;
    //      Toolbar
    @Nullable
    @BindView(R.id.toolbar_login)
    Toolbar toolbar;
    // Controlar que solo se pueda clickar una vez el botón (evitar spam de registros)
    private boolean botonClickado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        definirListeners();
    }

    private void definirListeners() {
        // Crear listener para validar nick
        assert ref_edit_nick != null;
        ref_edit_nick.setOnFocusChangeListener((v, hasFocus) -> {
                    // Si ya tenía el foco:
                    if (hasFocus) return;    // Finalizar validación sin hacer nada
                    String nickPasado = ref_edit_nick.getText().toString();
                    // Si no es válido
                    if (!esValidoNick(nickPasado)) {
                        // Si está vacío
                        assert ref_til_nick != null;
                        if (nickPasado.equals("")) {
                            // No mostrar error
                            ref_til_nick.setError(null);
                            ref_til_nick.setErrorEnabled(false);
                        }
                        // Si no (valida por incorrecto)
                        else {
                            // Mostrar error
                            ref_til_nick.setError("El nombre de usuario debe tener al menos 3 caracteres");
                        }
                    }
                    // Si es válido
                    else {
                        // No mostrar error
                        assert ref_til_nick != null;
                        ref_til_nick.setError(null);
                        ref_til_nick.setErrorEnabled(false);
                    }

                    // Si se puede activar el botón doRegistro
                    assert ref_button_doLogin != null;
                    if (esRegistrable()) {
                        // Activarlo
                        ref_button_doLogin.setEnabled(true);
                    }
                    // Si no
                    else {
                        // Desactivarlo
                        ref_button_doLogin.setEnabled(false);
                    }
                }
        );

        // Crear listener para validar contraseña
        assert ref_edit_passw != null;
        ref_edit_passw.setOnFocusChangeListener((v, hasFocus) -> {
                    // Si ya tenía el foco:
                    if (hasFocus) return;    // Finalizar validación sin hacer nada
                    String passwPasado = ref_edit_passw.getText().toString();
                    // Si no es válido
                    if (!esValidoPassword(passwPasado)) {
                        // Si está vacío
                        assert ref_til_passw != null;
                        if (passwPasado.equals("")) {
                            // No mostrar error
                            ref_til_passw.setError(null);
                            ref_til_passw.setErrorEnabled(false);
                        }
                        // Si no (valida por incorrecto)
                        else {
                            // Mostrar error
                            ref_til_passw.setError("La contraseña debe tener al menos 8 caracteres");
                        }
                    }
                    // Si es válido o está vacío
                    else {
                        // No mostrar error
                        assert ref_til_passw != null;
                        ref_til_passw.setError(null);
                        ref_til_passw.setErrorEnabled(false);
                    }

                    // Si se puede activar el botón doRegistro
                    assert ref_button_doLogin != null;
                    if (esRegistrable()) {
                        // Activarlo
                        ref_button_doLogin.setEnabled(true);
                    }
                    // Si no
                    else {
                        // Desactivarlo
                        ref_button_doLogin.setEnabled(false);
                    }
                }
        );

        // Listener especial para el último edit (contraseña)
        LinearLayout grupoEdits = findViewById(R.id.linearLayout_login);
        final TextInputLayout ultimoHijo = (TextInputLayout) grupoEdits.getChildAt(grupoEdits.getChildCount() - 1);
        final EditText editUltimoHijo = ultimoHijo.getEditText();
        assert editUltimoHijo != null;
        editUltimoHijo.setOnEditorActionListener((v, actionId, evento) -> {
                    switch (actionId) {
                        // Caso: intro pulsado
                        case EditorInfo.IME_ACTION_DONE:
                            // Quitar foco
                            editUltimoHijo.clearFocus();
                            // Obtener teclado
                            InputMethodManager teclado = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            teclado.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), RESULT_UNCHANGED_SHOWN);
                            return true;
                        // Por defecto
                        default:
                            return false;
                    }
                }
        );

    }

    private boolean esValidoNick(String param_nick) {
        return param_nick.length() >= 3;
    }

    private boolean esValidoPassword(String param_passw) {
        return param_passw.length() >= 8;
    }

    private boolean esRegistrable() {
        // Si alguno tiene error: no es registrable
        assert (ref_til_nick != null && ref_til_passw != null);
        if (ref_til_nick.isErrorEnabled() || ref_til_passw.isErrorEnabled()) {
            return false;
        }
        // Si no, si algún edit está vacío: no es registrable
        else {
            assert (ref_edit_nick != null && ref_edit_passw != null);
            return !TextUtils.isEmpty(ref_edit_nick.getText().toString()) && !TextUtils.isEmpty(ref_edit_passw.getText().toString());
        }
    }

    public void onClickLogin(View v) {
        if (botonClickado) return;   // Si el botón ya ha sido pulsado: salir
        botonClickado = true;
        Toast logeandoMsg = Toast.makeText(getApplicationContext(), "Iniciando sesión...", Toast.LENGTH_SHORT);
        logeandoMsg.show();
        assert (ref_edit_nick != null && ref_edit_passw != null);
        final Usuario datosUsuario = new Usuario(ref_edit_nick.getText().toString(), ref_edit_passw.getText().toString());
        final LoginActivity instancia = this;
        API_SGU apiSGU = Audiapp.getInstancia().getRetroAudiappFit().getCliente().create(API_SGU.class);
        apiSGU.hacerLogin(datosUsuario).
                enqueue(new Callback<InfoDBAudiappi>() {
                    @Override
                    public void onResponse(@NonNull Call<InfoDBAudiappi> llamada, @NonNull Response<InfoDBAudiappi> respuesta) {
                        InfoDBAudiappi mensaje = respuesta.body();
                        // Si el login falla (datos incorrectos)
                        assert mensaje != null;
                        if (mensaje.getTag().equals("FALLO")) {
                            // Informar
                            Toast burbuja = Toast.makeText(getApplicationContext(), Strings.loginFallido, Toast.LENGTH_SHORT);
                            burbuja.show();
                            // Desclickar botón
                            botonClickado = false;
                        }
                        // Si no falla (datos correctos)
                        else {
                            // Informar
                            Toast burbuja = Toast.makeText(getApplicationContext(), Strings.loginOk, Toast.LENGTH_SHORT);
                            burbuja.show();
                            // Modificar RetroAudiappFit para que use el token devuelto
                            Audiapp.getInstancia().getRetroAudiappFit().agregarJWT(mensaje.getDescripcion());
                            // Ver si hay un usuario guardado (para actualizar datos de acceso o no)
                            List<Usuario> listaUsuarios = ((GestorUsuarioDB) Objects.requireNonNull(new GestorDB().acceder("Usuario"))).leerTodos();
                            // Si lo hay
                            assert listaUsuarios != null;
                            if (listaUsuarios.size() > 0) {
                                // Limpiar la tabla y añadir el usuario
                                ((GestorUsuarioDB) Objects.requireNonNull(new GestorDB().acceder("Usuario"))).borrarTodos();
                                ((GestorUsuarioDB) Objects.requireNonNull(new GestorDB().acceder("Usuario"))).crear(datosUsuario);
                            }
                            // Si no lo hay
                            else {
                                // Guardarlo en DB
                                ((GestorUsuarioDB) Objects.requireNonNull(new GestorDB().acceder("Usuario"))).crear(datosUsuario);
                            }
                            // Una vez se actualiza la DB, pasar a actividad de trabajo
                            Intent i = new Intent(instancia, GeneradorProgresionesActivity.class);
                            startActivity(i);
                            instancia.finish();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<InfoDBAudiappi> llamada, @NonNull Throwable t) {
                        // No se ha podido contactar con el servidor: sólo informar al usuario
                        Toast burbuja = Toast.makeText(getApplicationContext(), Strings.errorConexionServidor, Toast.LENGTH_SHORT);
                        burbuja.show();
                        botonClickado = false;  // Desclickar botón
                    }
                });
    }
}
