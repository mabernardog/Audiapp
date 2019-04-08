
package com.audiapp.progresiones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.audiapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class TipoProgresionTonalFragment extends Fragment {

    public static TipoProgresionTonalFragment newInstance() {
        return new TipoProgresionTonalFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_tipo_progresion_tonal, container, false);
        return vistaFragmento;
    }

}
