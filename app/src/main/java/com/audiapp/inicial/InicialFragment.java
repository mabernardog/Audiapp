package com.audiapp.inicial;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.audiapp.Audiapp;
import com.audiapp.R;
import com.audiapp.apisgu.API_SGU;
import com.audiapp.db.GestorDB;
import com.audiapp.db.GestorUsuarioDB;
import com.audiapp.modelo.InfoDBAudiappi;
import com.audiapp.modelo.TipoUsuario;
import com.audiapp.modelo.Usuario;
import com.audiapp.viewmodels.AppViewModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.audiapp.R.color.colorBackgroundInicial;

public class InicialFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("App", "Aplicación iniciada");
        // Cambiar tema de la actividad contenedora
        Objects.requireNonNull(getActivity()).setTheme(R.style.AudiappTheme_OpeningTheme);
        // Ocultar barra de título
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Poner en pantalla completa
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Asociar actividad al diseño
        View vistaFragmento = inflater.inflate(R.layout.fragment_inicial, container, false);
        // Cambiar colores
        vistaFragmento.setBackgroundColor(getResources().getColor(colorBackgroundInicial));
        /* Decidir siguiente actividad a lanzar */
        // Leer la tabla Usuario para ver si hay alguno ya creado
        List<Usuario> listaUsuarios = ((GestorUsuarioDB) Objects.requireNonNull(new GestorDB().acceder("Usuario"))).leerTodos();
        // Si hay usuarios en la lista
        assert listaUsuarios != null;
        if (listaUsuarios.size() > 0) {
            final InicialFragment instancia = this;
            // Intentar logear con el primer usuario que haya (en teoría, es el único que hay)
            API_SGU apiSGU = Audiapp.getInstancia().getRetroAudiappFit().getCliente().create(API_SGU.class);
            apiSGU.hacerLogin(listaUsuarios.get(0)).
                    enqueue(new Callback<InfoDBAudiappi>() {
                        @Override
                        public void onResponse(@NonNull Call<InfoDBAudiappi> llamada, @NonNull Response<InfoDBAudiappi> respuesta) {
                            // Si se recibe una respuesta
                            InfoDBAudiappi mensaje = respuesta.body();
                            // Si la respuesta es que hay un fallo al logear
                            assert mensaje != null;
                            if (mensaje.getTag().equals("FALLO")) {
                                // Se irá a la actividad de login
                                new Handler().postDelayed(new SiguienteActivity(instancia, "DECIDIR"), 2000);
                            }
                            // Si no (se ha logeado)
                            else {
                                // Poner el token en retrofit
                                Audiapp.getInstancia().getRetroAudiappFit().agregarJWT(mensaje.getDescripcion());
                                // Se irá a la primera actividad del usuario
                                new Handler().postDelayed(new SiguienteActivity(instancia, "TRABAJO"), 2000);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<InfoDBAudiappi> llamada, @NonNull Throwable t) {
                            // No se ha podido contactar con el servidor: se irá a actividad de login
                            new Handler().postDelayed(new SiguienteActivity(instancia, "DECIDIR"), 2000);
                        }
                    });
        }
        // Si no los hay, ir a ELEGIR si login o registro
        else {
            new Handler().postDelayed(new SiguienteActivity(this, "DECIDIR"), 5000);
        }
        return vistaFragmento;
    }

    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(getActivity()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}

class SiguienteActivity implements Runnable {
    private final InicialFragment instancia;
    private final String actALanzar;

    // Constructor en el caso de que no haya una lista de usuarios
    SiguienteActivity(InicialFragment param_instancia, String param_actALanzar) {
        instancia = param_instancia;
        actALanzar = param_actALanzar;
    }

    @Override
    public void run() {
        // Usar el tema anterior
        Objects.requireNonNull(instancia.getActivity()).setTheme(R.style.AudiappTheme);
        // Ver qué hay que lanzar
        switch (actALanzar) {
            // Si se lanzará LOGIN crear su intent
            case "LOGIN":
                Navigation.findNavController(Objects.requireNonNull(instancia.getView())).navigate(R.id.action_inicial_to_login);
                break;
            // Si no, si se lanzará TRABAJO crear su intent
            case "TRABAJO":
                try {
                    //Navigation.findNavController(Objects.requireNonNull(instancia.getView())).popBackStack(R.id.inicialActivity, true);
                    // Actualizar el AppViewModel y navegar
                    ViewModelProviders.of(instancia.getActivity()).get(AppViewModel.class).setTipoAcceso(TipoUsuario.NORMAL);
                    Navigation.findNavController(Objects.requireNonNull(instancia.getView())).navigate(R.id.action_inicial_to_funcionalidadApp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            // Si no, se lanzará crear intent para lanzar intent de DECIDIR
            default:
                Navigation.findNavController(Objects.requireNonNull(instancia.getView())).navigate(R.id.action_inicial_to_elegirInicio);
                break;
        }
    }
}
