package com.audiapp.progresiones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.audiapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class ElegirProgresionFragment extends Fragment {
    @Nullable
    @BindView(R.id.button_goProgresionTonal)
    Button botonTonal;
    @Nullable
    @BindView(R.id.button_goProgresionLibre)
    Button botonLibre;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_elegir_progresion, container, false);
        // Bindear Butterknife
        ButterKnife.bind(this, vistaFragmento);
        // Añadir navegación
        assert botonTonal != null;
        botonTonal.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.crearProgresionTonalFragment));
        assert botonLibre != null;
        botonLibre.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.crearLibreFragment));
        return vistaFragmento;
    }

}
