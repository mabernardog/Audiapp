
package com.audiapp.progresiones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.audiapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


public class CrearProgresionLibreFragment extends Fragment {
    private ElegirProgresionViewModel mViewModel;

    public static CrearProgresionLibreFragment newInstance() {
        return new CrearProgresionLibreFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ElegirProgresionViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_crear_progresion_libre, container, false);
        // Cambiar título
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_crear_progresionLibre);
        return vistaFragmento;
    }

}
