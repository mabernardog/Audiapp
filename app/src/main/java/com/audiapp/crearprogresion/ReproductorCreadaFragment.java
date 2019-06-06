package com.audiapp.crearprogresion;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.audiapp.modelo.usuarios.TipoUsuario;
import com.audiapp.reproductor.ControladorReproductor;
import com.audiapp.reproductor.ServicioReproductor;
import com.audiapp.reproductor.WidgetReproductor;
import com.audiapp.viewmodels.AppViewModel;
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
    @Nullable
    @BindView(R.id.text_num_acordes_creada)
    TextView catacteristicaNumAcordes;
    @Nullable
    @BindView(R.id.text_tempo_creada)
    TextView caracteristicaTempo;
    @Nullable
    @BindView(R.id.text_tonalidades_creada)
    TextView caracteristicaTonalidades;
    private WidgetReproductor mWidgetReproductor;
    private ServiceConnection mServiceConnection;
    private Intent intentServicio;
    private ServicioReproductor mServicioReproductor;
    private CrearProgresionViewModel mViewModel;
    private NavController mNavController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(CrearProgresionViewModel.class);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vistaFragmento = inflater.inflate(R.layout.fragment_reproductor_creada, container, false);
        ButterKnife.bind(this, vistaFragmento);
        // Determinar NavController
        mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.general_host);
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
        if ((ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class)).getTipoAcceso() == TipoUsuario.ANONIMO) {
            buttonGuardar.setOnClickListener(v -> (Toast.makeText(getContext(), "Opción solo para usuarios registrados", Toast.LENGTH_SHORT)).show());
        } else {
            buttonGuardar.setOnClickListener(v -> crearNombreProgresionDialog());
        }
        buttonGuardar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_save_white_24dp, 0, 0, 0);

        // Mostrar características de la progresión
        String str = String.format(getResources().getString(R.string.info_num_acordes_creada), mViewModel.getProgresion().getAcordeArrayList().size());
        Objects.requireNonNull(catacteristicaNumAcordes).setText(str);
        str = String.format(getResources().getString(R.string.info_tempo_creada), mViewModel.getProgresion().getTempo().getClasificacion());
        Objects.requireNonNull(caracteristicaTempo).setText(str);
        str = String.format(getResources().getString(R.string.info_tonalidades_creada), TextUtils.join(", ", mViewModel.getProgresion().getTonalidades()));
        Objects.requireNonNull(caracteristicaTonalidades).setText(str);

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
        @SuppressLint("InflateParams") View vistaDialogo = LayoutInflater.from(getContext()).inflate(R.layout.dialog_nombre_progresion, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        builder.setMessage("Nombre de la progresión:");
        builder.setView(vistaDialogo);
        builder.setPositiveButton("Aceptar", null);
        builder.setNegativeButton("Cancelar", ((dialog, which) -> dialog.dismiss()));
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            EditText editText = vistaDialogo.findViewById(R.id.nombre_progresion_ET);
            String nombre = editText.getText().toString();
            GestorProgresionesDB gestorProgresionesDB = ((GestorProgresionesDB) Objects.requireNonNull(new GestorDB().acceder("Progresiones")));
            // Comprobar que el nombre no existe
            if (gestorProgresionesDB.cuentaProgresionesDeNombre(nombre) == 0) {
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
                // Todo: enviar datos al servidor
                alertDialog.dismiss();
                (Toast.makeText(getContext(), "Progresión creada con éxito", Toast.LENGTH_SHORT)).show();
                mNavController.navigate(R.id.elegirModuloFragment);
            } else {
                (Toast.makeText(getContext(), "Nombre ya existente", Toast.LENGTH_SHORT)).show();
            }
        });
    }
}
