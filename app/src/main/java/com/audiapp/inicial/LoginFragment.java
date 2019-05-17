package com.audiapp.inicial;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
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
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.audiapp.Audiapp;
import com.audiapp.R;
import com.audiapp.apisgu.API_SGU;
import com.audiapp.db.GestorDB;
import com.audiapp.db.GestorUsuarioDB;
import com.audiapp.globales.Strings;
import com.audiapp.modelo.InfoDBAudiappi;
import com.audiapp.modelo.TipoUsuario;
import com.audiapp.modelo.Usuario;
import com.audiapp.viewmodels.AppViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.inputmethod.InputMethodManager.RESULT_UNCHANGED_SHOWN;


public class LoginFragment extends Fragment {
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
    private View mView;
    // Controlar que solo se pueda clickar una vez el botón (evitar spam de registros)
    private boolean botonClickado = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_login, container, false);
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
        LinearLayout grupoEdits = null;
        try {
            grupoEdits = mView.findViewById(R.id.linearLayout_login);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert grupoEdits != null;
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
                            try {
                                InputMethodManager teclado = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
                                teclado.hideSoftInputFromWindow(mView.getWindowToken(), RESULT_UNCHANGED_SHOWN);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return true;
                        // Por defecto
                        default:
                            return false;
                    }
                }
        );

        // Listener para el botón
        assert ref_button_doLogin != null;
        ref_button_doLogin.setOnClickListener(v -> {
            if (botonClickado) return;   // Si el botón ya ha sido pulsado: salir
            botonClickado = true;
            Toast logeandoMsg = Toast.makeText(getContext(), "Iniciando sesión...", Toast.LENGTH_SHORT);
            logeandoMsg.show();
            assert (ref_edit_nick != null && ref_edit_passw != null);
            final Usuario datosUsuario = new Usuario(ref_edit_nick.getText().toString(), ref_edit_passw.getText().toString());
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
                                Toast burbuja = Toast.makeText(getContext(), Strings.loginFallido, Toast.LENGTH_SHORT);
                                burbuja.show();
                                // Desclickar botón
                                botonClickado = false;
                            }
                            // Si no falla (datos correctos)
                            else {
                                // Informar
                                Toast burbuja = Toast.makeText(getContext(), Strings.loginOk, Toast.LENGTH_SHORT);
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
                                // Una vez se actualiza la DB, pasar a actividad de trabajo tras actualizar el AppViewModel
                                ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class).setTipoAcceso(TipoUsuario.NORMAL);
                                Navigation.findNavController(mView).navigate(R.id.action_login_to_funcionalidadApp);
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
}
