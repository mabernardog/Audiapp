package com.audiapp.inicial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.audiapp.R;
import com.audiapp.modelo.TipoUsuario;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

class ElegirModuloFragment extends Fragment {
    @Nullable
    @BindView(R.id.button_goCrearProgresion)
    Button botonCrear;
    @Nullable
    @BindView(R.id.button_goAdivinar)
    Button botonAdivinar;
    @Nullable
    @BindView(R.id.toolbar_elegirModulo)
    Toolbar toolbar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_elegir_modulo, container, false);
        // Bindear Butterknife
        ButterKnife.bind(this, vistaFragmento);
        // Determinar NavController
        NavController mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.general_host);
        // Hacer que el mToolbar lo autogestione NavigationUI
        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph()).build();
        assert toolbar != null;
        NavigationUI.setupWithNavController(toolbar, mNavController, mAppBarConfiguration);
        // Obtener argumentos pasados
        TipoUsuario tipoUsuario = ElegirModuloFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getTipoUsuario();
        // Añadir navegación
        assert botonCrear != null;
        botonCrear.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.ejercicioPorOpcionesFragment));
        assert botonAdivinar != null;
        botonAdivinar.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.crearLibreFragment));
        if (tipoUsuario == TipoUsuario.ANONIMO) {
            botonAdivinar.setEnabled(false);
        }
        return vistaFragmento;
    }

}
