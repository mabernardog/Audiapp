package com.audiapp.progresiones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.audiapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;


class CrearProgresionTonalFragment extends Fragment {
    @Nullable
    @BindView(R.id.bottom_nav_progresionTonal)
    BottomNavigationView mBottomNavigationView;
    @Nullable
    @BindView(R.id.floatingActionButton)
    FloatingActionButton mFloatingActionButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgresionTonalViewModel mViewModel = ViewModelProviders.of(this).get(ProgresionTonalViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_crear_progresion_tonal, container, false);
        ButterKnife.bind(this, vistaFragmento);
        // Determinar NavController
        NavController mNavController = Navigation.findNavController(vistaFragmento.findViewById(R.id.progresion_tonal_host));
        // Hacer que el BottomNavigation lo autogestione NavigationUI
        assert mBottomNavigationView != null;
        NavigationUI.setupWithNavController(mBottomNavigationView, mNavController);
        // Añadir onClick al botón flotante
        assert mFloatingActionButton != null;
        mFloatingActionButton.setOnClickListener(v -> {
            // Todo: generar MIDI
            // Todo: navegar solo si se genera el MIDI
        });
        return vistaFragmento;
    }

}
