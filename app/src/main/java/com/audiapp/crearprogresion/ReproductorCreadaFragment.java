package com.audiapp.crearprogresion;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.audiapp.R;
import com.audiapp.db.GestorAcordesDB;
import com.audiapp.db.GestorDB;
import com.audiapp.db.GestorProgresionesDB;
import com.audiapp.modelo.progresiones.Acorde;
import com.audiapp.modelo.progresiones.ProgresionArmonica;
import com.audiapp.viewmodels.CrearProgresionViewModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReproductorCreadaFragment extends Fragment {
    @Nullable
    @BindView(R.id.toolbar_reproductor)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.button_guardar_progresion)
    Button buttonGuardar;
    private WidgetReproductor mWidgetReproductor;
    private ServiceConnection mServiceConnection;
    private Intent intentServicio;
    private ServicioReproductor mServicioReproductor;
    private CrearProgresionViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(CrearProgresionViewModel.class);
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

        // Crear el listener para el botón de guardado
        assert buttonGuardar != null;
        buttonGuardar.setOnClickListener(v -> crearNombreProgresionDialog());
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

    private void crearNombreProgresionDialog() {
        View vistaDialogo = LayoutInflater.from(getContext()).inflate(R.layout.dialog_nombre_progresion, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        builder.setMessage("Nombre de la progresión:");
        builder.setView(vistaDialogo);
        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            EditText editText = vistaDialogo.findViewById(R.id.nombre_progresion_ET);
            String nombre = editText.getText().toString();

            GestorProgresionesDB gestorProgresionesDB = ((GestorProgresionesDB) Objects.requireNonNull(new GestorDB().acceder("Progresiones")));
            // Comprobar que el nombre no existe
            long c = gestorProgresionesDB.cuentaProgresionesDeNombre(nombre);
            if (c == 0) {
                ProgresionArmonica progresion = mViewModel.getProgresion();
                // Asignar nombre a la progresión e insertar
                progresion.setNombre(nombre);
                // Insertar en la db la progresión
                long idProgresion = gestorProgresionesDB.crearPK(progresion);
                // Si se insertó
                if (idProgresion > 0) {
                    // Insertar en la db los acordes
                    GestorAcordesDB gestorAcordesDB = ((GestorAcordesDB) Objects.requireNonNull(new GestorDB().acceder("Acordes")));
                    ArrayList<Acorde> listaAcordes = progresion.getAcordeArrayList();
                    for (Acorde acorde : listaAcordes) {
                        acorde.setIdProgresion((int) idProgresion);
                        gestorAcordesDB.crear(acorde);
                    }
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", ((dialog, which) -> dialog.dismiss()));
        (builder.create()).show();
    }
}
