package com.audiapp.inicial;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.audiapp.Audiapp;
import com.audiapp.R;
import com.audiapp.apisgu.API_SGU;
import com.audiapp.globales.Strings;
import com.audiapp.modelo.InfoDBAudiappi;
import com.audiapp.modelo.usuarios.Usuario;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.inputmethod.InputMethodManager.RESULT_UNCHANGED_SHOWN;


public class RegistroFragment extends Fragment {
    // Obtener referencias
    //      Email
    @Nullable
    @BindView(R.id.inputlayout_reg_email)
    TextInputLayout ref_til_email;
    @Nullable
    @BindView(R.id.edit_reg_email)
    EditText ref_edit_email;
    //      Nick;
    @Nullable
    @BindView(R.id.inputlayout_reg_nick)
    TextInputLayout ref_til_nick;
    @Nullable
    @BindView(R.id.edit_reg_nick)
    EditText ref_edit_nick;
    //      Contraseña;
    @Nullable
    @BindView(R.id.inputlayout_reg_password)
    TextInputLayout ref_til_passw;
    @Nullable
    @BindView(R.id.edit_reg_password)
    EditText ref_edit_passw;
    //      Botón enviar registro;
    @Nullable
    @BindView(R.id.button_doRegistro)
    Button ref_button_doRegistro;
    //      Toolbar
    @Nullable
    @BindView(R.id.toolbar_registro)
    Toolbar toolbar;
    private View mView;
    // Controlar que solo se pueda clickar una vez el botón (evitar spam de registros)
    private boolean botonClickado = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_registro, container, false);
        ButterKnife.bind(this, mView);
        // Determinar NavController
        NavController mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.general_host);
        // Hacer que el mToolbar lo autogestione NavigationUI
        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph()).build();
        assert toolbar != null;
        NavigationUI.setupWithNavController(toolbar, mNavController, mAppBarConfiguration);
        definirListeners();
        return mView;
    }

    private void definirListeners() {
        // Crear listener para validar email
        assert ref_edit_email != null;
        ref_edit_email.setOnFocusChangeListener((v, hasFocus) -> {
                    // Si ya tenía el foco:
                    if (hasFocus) return;    // Finalizar validación sin hacer nada
                    String mailPasado = ref_edit_email.getText().toString();
                    // Si es válido o está vacío
                    assert ref_til_email != null;
                    if (esValidoEmail(mailPasado)) {
                        // No mostrar error
                        ref_til_email.setError(null);
                        ref_til_email.setErrorEnabled(false);
                    }
                    // Si no es válido
                    else {
                        // Si está vacío
                        if (mailPasado.equals("")) {
                            // No mostrar error
                            ref_til_email.setError(null);
                            ref_til_email.setErrorEnabled(false);
                        }
                        // Si no (valida por incorrecto)
                        else {
                            // Mostrar error
                            ref_til_email.setError("Dirección de correo electrónico incorrecta");
                        }
                    }

                    // Si se puede activar el botón doRegistro
                    assert ref_button_doRegistro != null;
                    if (esRegistrable()) {
                        // Activarlo
                        ref_button_doRegistro.setEnabled(true);
                    }
                    // Si no
                    else {
                        // Desactivarlo
                        ref_button_doRegistro.setEnabled(false);
                    }
                }
        );

        // Crear listener para validar nick
        assert ref_edit_nick != null;
        ref_edit_nick.setOnFocusChangeListener((v, hasFocus) -> {
                    // Si ya tenía el foco:
                    if (hasFocus) return;    // Finalizar validación sin hacer nada
                    String nickPasado = ref_edit_nick.getText().toString();
                    // Si no es válido
                    assert ref_til_nick != null;
                    if (!esValidoNick(nickPasado)) {
                        // Si está vacío
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
                        ref_til_nick.setError(null);
                        ref_til_nick.setErrorEnabled(false);
                    }

                    // Si se puede activar el botón doRegistro
                    assert ref_button_doRegistro != null;
                    if (esRegistrable()) {
                        // Activarlo
                        ref_button_doRegistro.setEnabled(true);
                    }
                    // Si no
                    else {
                        // Desactivarlo
                        ref_button_doRegistro.setEnabled(false);
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
                    assert ref_til_passw != null;
                    if (!esValidoPassword(passwPasado)) {
                        // Si está vacío
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
                        ref_til_passw.setError(null);
                        ref_til_passw.setErrorEnabled(false);
                    }

                    // Si se puede activar el botón doRegistro
                    assert ref_button_doRegistro != null;
                    if (esRegistrable()) {
                        // Activarlo
                        ref_button_doRegistro.setEnabled(true);
                    }
                    // Si no
                    else {
                        // Desactivarlo
                        ref_button_doRegistro.setEnabled(false);
                    }
                }
        );

        // Listener especial para el último edit (contraseña)
        LinearLayout grupoEdits = mView.findViewById(R.id.linearLayout_registro);
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
                            InputMethodManager teclado = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
                            teclado.hideSoftInputFromWindow(mView.getWindowToken(), RESULT_UNCHANGED_SHOWN);
                            return true;
                        // Por defecto
                        default:
                            return false;
                    }
                }
        );

        assert ref_button_doRegistro != null;
        ref_button_doRegistro.setOnClickListener(v -> {
            if (botonClickado) return;   // Si el botón ya ha sido pulsado: salir
            botonClickado = true;
            Toast registrandoMsg = Toast.makeText(getContext(), "Creando la cuenta...", Toast.LENGTH_SHORT);
            registrandoMsg.show();
            assert (ref_edit_email != null && ref_edit_nick != null && ref_edit_passw != null);
            Usuario datosUsuario = new Usuario(ref_edit_email.getText().toString(), ref_edit_nick.getText().toString(), ref_edit_passw.getText().toString());
            // Definir contexto para los intents
            final RegistroFragment instancia = this;
            // Crear runnable
            Runnable navegar = () -> NavHostFragment.findNavController(instancia).navigate(R.id.action_registro_to_login);
            API_SGU apiSGU = Audiapp.getInstancia().getRetroAudiappFit().getCliente().create(API_SGU.class);
            apiSGU.hacerRegistro(datosUsuario).
                    enqueue(new Callback<InfoDBAudiappi>() {
                        @Override
                        public void onResponse(@NonNull Call<InfoDBAudiappi> llamada, @NonNull Response<InfoDBAudiappi> respuesta) {
                            // Sacar el mensaje de información de la respuesta
                            InfoDBAudiappi mensaje = respuesta.body();
                            // Informar al usuario
                            assert mensaje != null;     // Siempre va a ir un InfoDBAudiappi, puesto por seguridad
                            Toast burbuja = Toast.makeText(getContext(), mensaje.getDescripcion(), Toast.LENGTH_SHORT);
                            burbuja.show();
                            // Si el servidor informa de fallo
                            if (mensaje.getTag().equals("FALLO")) {
                                // Si el fallo es porque hay algo repetido
                                if (mensaje.getMotivo().equals("REPETIDO")) {
                                    // Si el usuario ya está registrado
                                    switch (mensaje.getDescripcion()) {
                                        case Strings.usuarioYaEnBD:
                                            botonClickado = false;  // Desclickar botón

                                            // Hacer que inicie sesión a mitad del toast
                                            new Handler().postDelayed(navegar, 1000);
                                            break;
                                        // Si el email está repetido
                                        case Strings.emailYaEnBD:
                                            botonClickado = false;  // Desclickar botón
                                            // Resetear edit del email
                                            ref_edit_email.setText("");
                                            break;
                                        // Si el nick está repetido
                                        case Strings.nickYaEnBD:
                                            botonClickado = false;  // Desclickar botón
                                            // Resetear edit del nick
                                            ref_edit_nick.setText("");
                                            break;
                                    }
                                }
                                // Si no (motivo = ERROR_DB), no hacer nada
                                else botonClickado = false;  // Desclickar botón
                            }
                            // Si el servidor informa de que se ha realizado el registro con éxito
                            if (mensaje.getTag().equals("OK")) {
                                botonClickado = false;  // Desclickar botón
                                // Verificar que es el INSERT lo recibido (puede ser redundante)
                                if (mensaje.getMotivo().equals("INSERT")) {
                                    // Mandar a actividad de inicio de sesión a mitad del toast
                                    new Handler().postDelayed(navegar, 1000);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<InfoDBAudiappi> llamada, @NonNull Throwable t) {
                            // No se ha podido contactar con el servidor: sólo informar al usuario
                            Toast burbuja = Toast.makeText(getContext(), Strings.errorConexionServidor, Toast.LENGTH_SHORT);
                            burbuja.show();
                            botonClickado = false;  // Desclickar botón
                        }
                    });
        });

    }

    private boolean esValidoEmail(String param_direccion) {
        // Pensar si usar mejor JavaMail
        return Patterns.EMAIL_ADDRESS.matcher(param_direccion).matches();
    }

    private boolean esValidoNick(String param_nick) {
        return param_nick.length() >= 3;
    }

    private boolean esValidoPassword(String param_passw) {
        return param_passw.length() >= 8;
    }

    private boolean esRegistrable() {
        // Si alguno tiene error: no es registrable
        assert (ref_til_email != null && ref_til_nick != null && ref_til_passw != null);
        if (ref_til_email.isErrorEnabled() || ref_til_nick.isErrorEnabled() || ref_til_passw.isErrorEnabled()) {
            return false;
        }
        // Si no, si algún edit está vacío: no es registrable
        else {
            assert (ref_edit_email != null && ref_edit_nick != null && ref_edit_passw != null);
            return !TextUtils.isEmpty(ref_edit_email.getText().toString()) && !TextUtils.isEmpty(ref_edit_nick.getText().toString())
                    && !TextUtils.isEmpty(ref_edit_passw.getText().toString());
        }
    }
}

