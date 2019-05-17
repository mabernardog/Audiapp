package com.audiapp.adivinar;

import android.os.Bundle;
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
import com.audiapp.viewmodels.OpcionesEjercicioViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


class EjercicioPorOpcionesFragment extends Fragment {
    @Nullable
    @BindView(R.id.bottom_nav_progresionTonal)
    BottomNavigationView mBottomNavigationView;
    @Nullable
    @BindView(R.id.floatingActionButton)
    FloatingActionButton mFloatingActionButton;
    @Nullable
    @BindView(R.id.toolbar_ejPorOpciones)
    Toolbar toolbar;

    private OpcionesEjercicioViewModel mViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OpcionesEjercicioViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_ejercicio_por_opciones, container, false);
        ButterKnife.bind(this, vistaFragmento);
        // Determinar NavController
        //SIN SINGLEACTIVITY NavController mNavController = Navigation.findNavController(vistaFragmento.findViewById(R.id.progresion_tonal_host));
        NavController mNavControllerToolbar = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.general_host);
        // Hacer que el mToolbar lo autogestione NavigationUI
        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(mNavControllerToolbar.getGraph()).build();
        assert toolbar != null;
        NavigationUI.setupWithNavController(toolbar, mNavControllerToolbar, mAppBarConfiguration);
        // Hacer que el BottomNavigation lo autogestione NavigationUI
        assert mBottomNavigationView != null;
        NavController mNavControllerBotNav = Navigation.findNavController(vistaFragmento.findViewById(R.id.ejercicioPorOpcioneshost));
        NavigationUI.setupWithNavController(mBottomNavigationView, mNavControllerBotNav);
        // Añadir onClick al botón flotante
        assert mFloatingActionButton != null;
        mFloatingActionButton.setOnClickListener(v -> {
            // Todo: contactar con el servidor para pedir ejercicio
            // Todo: generar el MIDI del ejercicio
            MidiGen.generarTestEn((new File(Objects.requireNonNull(getContext()).getFilesDir(), "teste.mid").getPath()));
            // Todo: navegar al destino
            Navigation.findNavController(vistaFragmento).navigate(0);
        });
        return vistaFragmento;
    }

}
