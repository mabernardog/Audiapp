package com.audiapp.progresiones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.ToggleButton;

import com.audiapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.OperacionesTablaBotones;


public class TipoProgresionTonalFragment extends Fragment {

    @Nullable
    @BindView(R.id.tablaTiposProgresionLineal)
    TableLayout mTableLayout;
    private ToggleButton.OnCheckedChangeListener mOnCheckedChangeListener;
    public ProgresionTonalViewModel mViewModel;

    public static TipoProgresionTonalFragment newInstance() {
        return new TipoProgresionTonalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getParentFragment() != null;
        mViewModel = ViewModelProviders.of(getParentFragment()).get(ProgresionTonalViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_tipo_progresion_tonal, container, false);
        ButterKnife.bind(this, vistaFragmento);
        // Crear el listener para los botones
        mOnCheckedChangeListener = (buttonView, isChecked) -> {
            // Si estará activo
            if (isChecked) {
                // Añadirlo al view model si no estaba ya
                boolean loContiene = false;
                for(String texto : mViewModel.getTiposProgresion()) {
                    if(texto.contentEquals(buttonView.getText())) {
                        loContiene = true;
                        break;
                    }
                }
                if(!loContiene) mViewModel.getTiposProgresion().add((String) buttonView.getText());
            } else {    // Si no
                // Buscarlo en el view model y eliminarlo
                int i = 0;
                for(String tipo : mViewModel.getTiposProgresion()) {
                    if(tipo.contentEquals(buttonView.getText())) {
                        mViewModel.getTiposProgresion().remove(i);
                        break;
                    }
                    i++;
                }
            }
        };
        assert mTableLayout != null;
        ArrayList<CheckBox> botones = OperacionesTablaBotones.getBotonesDeTabla(mTableLayout);
        for(CheckBox boton : botones) {
            boton.setOnCheckedChangeListener(mOnCheckedChangeListener);
        }
        // Recorrer el view model y activar los botones que toquen
        for(String tipo : mViewModel.getTiposProgresion()) {
            for(CheckBox boton : botones) {
                if(tipo.contentEquals(boton.getText())) {
                    boton.setChecked(true);
                    break;
                }
            }
        }
        return vistaFragmento;
    }


}
