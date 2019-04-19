package com.audiapp.progresiones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.audiapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.OperacionesTablaBotones;


public class TonalidadProgresionTonalFragment extends Fragment {
    public ProgresionTonalViewModel mViewModel;
    @Nullable
    @BindView(R.id.tablaTonalidadesMaProgresionLineal)
    TableLayout mTableLayout_mayores;
    @Nullable
    @BindView(R.id.tablaTonalidadesMeProgresionLineal)
    TableLayout mTableLayout_menores;
    private ToggleButton.OnCheckedChangeListener mOnMayoresCheckedChangeListener;
    private ToggleButton.OnCheckedChangeListener mOnMenoresCheckedChangeListener;

    public static TonalidadProgresionTonalFragment newInstance() {
        return new TonalidadProgresionTonalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getParentFragment() != null;
        assert getParentFragment().getParentFragment() != null;
        mViewModel = ViewModelProviders.of(getParentFragment().getParentFragment()).get(ProgresionTonalViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_tonalidad_progresion_tonal, container, false);
        ButterKnife.bind(this, vistaFragmento);
        // Crear los listeners para los botones
        mOnMayoresCheckedChangeListener = (buttonView, isChecked) -> {
            // Si estará activo
            if (isChecked) {
                // Añadirlo al view model si no estaba ya
                boolean loContiene = false;
                for (String texto : mViewModel.getTonalidadesMayoresProgresion()) {
                    if (texto.contentEquals(buttonView.getText())) {
                        loContiene = true;
                        break;
                    }
                }
                if (!loContiene)
                    mViewModel.getTonalidadesMayoresProgresion().add((String) buttonView.getText());
            } else {    // Si no
                // Buscarlo en el view model y eliminarlo
                int i = 0;
                for (String tipo : mViewModel.getTonalidadesMayoresProgresion()) {
                    if (tipo.contentEquals(buttonView.getText())) {
                        mViewModel.getTonalidadesMayoresProgresion().remove(i);
                        break;
                    }
                    i++;
                }
            }
        };
        mOnMenoresCheckedChangeListener = (buttonView, isChecked) -> {
            // Si estará activo
            if (isChecked) {
                // Añadirlo al view model si no estaba ya
                boolean loContiene = false;
                for (String texto : mViewModel.getTonalidadesMenoresProgresion()) {
                    if (texto.contentEquals(buttonView.getText())) {
                        loContiene = true;
                        break;
                    }
                }
                if (!loContiene)
                    mViewModel.getTonalidadesMenoresProgresion().add((String) buttonView.getText());
            } else {    // Si no
                // Buscarlo en el view model y eliminarlo
                int i = 0;
                for (String tipo : mViewModel.getTonalidadesMenoresProgresion()) {
                    if (tipo.contentEquals(buttonView.getText())) {
                        mViewModel.getTonalidadesMenoresProgresion().remove(i);
                        break;
                    }
                    i++;
                }
            }
        };
        assert mTableLayout_mayores != null;
        ArrayList<CheckBox> botones = OperacionesTablaBotones.getBotonesDeTabla(mTableLayout_mayores);
        for (CheckBox boton : botones) {
            boton.setOnCheckedChangeListener(mOnMayoresCheckedChangeListener);
        }
        // Recorrer el view model y activar los botones que toquen
        for (String tipo : mViewModel.getTonalidadesMayoresProgresion()) {
            for (CheckBox boton : botones) {
                if (tipo.contentEquals(boton.getText())) {
                    boton.setChecked(true);
                    break;
                }
            }
        }
        assert mTableLayout_menores != null;
        botones = OperacionesTablaBotones.getBotonesDeTabla(mTableLayout_menores);
        for (CheckBox boton : botones) {
            boton.setOnCheckedChangeListener(mOnMenoresCheckedChangeListener);
        }
        // Recorrer el view model y activar los botones que toquen
        for (String tipo : mViewModel.getTonalidadesMenoresProgresion()) {
            for (CheckBox boton : botones) {
                if (tipo.contentEquals(boton.getText())) {
                    boton.setChecked(true);
                    break;
                }
            }
        }
        return vistaFragmento;
    }

}
