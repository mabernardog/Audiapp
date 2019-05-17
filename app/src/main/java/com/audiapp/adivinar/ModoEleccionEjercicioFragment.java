package com.audiapp.adivinar;


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

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment with a Google +1 button.
 */
public class ModoEleccionEjercicioFragment extends Fragment {
    @Nullable
    @BindView(R.id.button_goEjercicioPorNiveles)
    Button botonGoPorNivel;
    @Nullable
    @BindView(R.id.button_goEjercicioPorOpciones)
    Button botonGoPorOpciones;
    @Nullable
    @BindView(R.id.toolbar_modoEleccionEjercicio)
    Toolbar toolbar;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_modo_eleccion_ejercicio, container, false);
        // Bindear Butterknife
        ButterKnife.bind(this, vistaFragmento);
        // Determinar NavController
        NavController mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.general_host);
        // Hacer que el mToolbar lo autogestione NavigationUI
        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph()).build();
        assert toolbar != null;
        NavigationUI.setupWithNavController(toolbar, mNavController, mAppBarConfiguration);
        // Añadir navegación
        assert botonGoPorNivel != null;
        botonGoPorNivel.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_modoEleccionEjercicio_to_ejercicioPorOpciones));    // Todo: cambiar por la acción adecuada
        assert botonGoPorOpciones != null;
        botonGoPorOpciones.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_modoEleccionEjercicio_to_ejercicioPorOpciones));

        return vistaFragmento;
    }

}
