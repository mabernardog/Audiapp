package com.audiapp.reproductor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.audiapp.R;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReproductorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    @Nullable
    @BindView(R.id.toolbar_reproductor)
    Toolbar toolbar;
    // TODO: Rename and change types of parameters
    private WidgetReproductor mWidgetReproductor;
    private ServiceConnection mServiceConnection;
    private Intent intentServicio;
    private ServicioReproductor mServicioReproductor;
    private boolean servicioActivo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vistaFragmento = inflater.inflate(R.layout.fragment_reproductor, container, false);
        ButterKnife.bind(this, vistaFragmento);
        // Determinar NavController
        NavController mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.general_host);
        // Hacer que el mToolbar lo autogestione NavigationUI
        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph()).build();
        assert toolbar != null;
        NavigationUI.setupWithNavController(toolbar, mNavController, mAppBarConfiguration);
        // Asociar fragment al servicio
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ServicioReproductor.ServicioReproductorBinder binder = (ServicioReproductor.ServicioReproductorBinder) service;
                mServicioReproductor = binder.getService();
                mServicioReproductor.setContext(getContext());
                // Todo: usar los midis generados por el fragment previo
                mServicioReproductor.setUris((new File(getContext().getFilesDir(), "teste.mid").getPath()));
                mWidgetReproductor.setMediaPlayer(new ControladorReproductor(mServicioReproductor));
                mWidgetReproductor.show();
                mServicioReproductor.play();
                servicioActivo = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                servicioActivo = false;
            }
        };
        // Inicializar el widget
        mWidgetReproductor = new WidgetReproductor(getContext());
        mWidgetReproductor.setAnchorView(vistaFragmento);
        mWidgetReproductor.setPrevNextListeners(v -> {
            mServicioReproductor.siguiente();
        }, v -> {
            mServicioReproductor.anterior();
        });
        return vistaFragmento;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (intentServicio == null) {
            intentServicio = new Intent(getContext(), ServicioReproductor.class);
            Objects.requireNonNull(getActivity()).bindService(intentServicio, mServiceConnection, Context.BIND_AUTO_CREATE);
            getActivity().startService(intentServicio);
        }
    }
}
