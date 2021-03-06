package com.audiapp.gestionprogresiones;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.audiapp.R;
import com.audiapp.midigen.MidiGen;
import com.audiapp.modelo.progresiones.ProgresionArmonica;
import com.audiapp.reproductor.ControladorReproductor;
import com.audiapp.reproductor.ServicioReproductor;
import com.audiapp.reproductor.WidgetReproductor;
import com.audiapp.viewmodels.GestionProgresionesViewModel;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GestorProgresionFragment extends Fragment {
    @Nullable
    @BindView(R.id.toolbar_gestorProgresion)
    Toolbar toolbar;

    private WidgetReproductor mWidgetReproductor;
    private ServiceConnection mServiceConnection;
    private Intent intentServicio;
    private ServicioReproductor mServicioReproductor;
    private GestionProgresionesViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(GestionProgresionesViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vistaFragmento = inflater.inflate(R.layout.fragment_gestor_progresion, container, false);
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
                ProgresionArmonica p = mViewModel.getProgresion();
                if (!(new File(p.getArchivo())).exists()) {
                    MidiGen.generarProgresion(p, p.getArchivo());
                }
                mServicioReproductor.setUris(mViewModel.getProgresion().getArchivo());
                mWidgetReproductor.setMediaPlayer(new ControladorReproductor(mServicioReproductor));
                mWidgetReproductor.show();
                mServicioReproductor.play();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        // Inicializar el widget
        mWidgetReproductor = new WidgetReproductor(getContext());
        mWidgetReproductor.setAnchorView(vistaFragmento);
        mWidgetReproductor.setPrevNextListeners(v -> mServicioReproductor.siguiente(), v -> mServicioReproductor.anterior());

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

    @Override
    public void onPause() {
        super.onPause();
        mServicioReproductor.stopService(intentServicio);
        mWidgetReproductor.hideManual();
    }
}
