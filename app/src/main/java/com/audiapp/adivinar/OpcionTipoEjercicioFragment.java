package com.audiapp.adivinar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.audiapp.R;
import com.audiapp.viewmodels.OpcionesEjercicioViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.OperacionesTablaBotones;


class OpcionTipoEjercicioFragment extends Fragment {

    @Nullable
    @BindView(R.id.tablaTiposProgresionLineal)
    TableLayout mTableLayout;
    @Nullable
    @BindView(R.id.radioGroup_hayInversiones)
    RadioGroup mRadioGroup;
    private OpcionesEjercicioViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getParentFragment() != null;
        assert getParentFragment().getParentFragment() != null;
        mViewModel = ViewModelProviders.of(getParentFragment().getParentFragment()).get(OpcionesEjercicioViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_opcion_tipo_ejercicio, container, false);
        ButterKnife.bind(this, vistaFragmento);
        // Crear el listener para los botones
        ToggleButton.OnCheckedChangeListener mOnCheckedChangeListener = (buttonView, isChecked) -> {
            // Si estará activo
            if (isChecked) {
                // Añadirlo al view model si no estaba ya
                boolean loContiene = false;
                for (String texto : mViewModel.getTiposProgresion()) {
                    if (texto.contentEquals(buttonView.getText())) {
                        loContiene = true;
                        break;
                    }
                }
                if (!loContiene) mViewModel.getTiposProgresion().add((String) buttonView.getText());
            } else {    // Si no
                // Buscarlo en el view model y eliminarlo
                int i = 0;
                for (String tipo : mViewModel.getTiposProgresion()) {
                    if (tipo.contentEquals(buttonView.getText())) {
                        mViewModel.getTiposProgresion().remove(i);
                        break;
                    }
                    i++;
                }
            }
        };

        assert mTableLayout != null;
        ArrayList<CheckBox> botones = OperacionesTablaBotones.getBotonesDeTabla(mTableLayout);
        for (CheckBox boton : botones) {
            boton.setOnCheckedChangeListener(mOnCheckedChangeListener);
        }
        // Recorrer el view model y activar los botones que toquen
        for (String tipo : mViewModel.getTiposProgresion()) {
            for (CheckBox boton : botones) {
                if (tipo.contentEquals(boton.getText())) {
                    boton.setChecked(true);
                    break;
                }
            }
        }

        assert mRadioGroup != null;
        // Establecer listener para el RadioGroup
        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // Si las habrá
            if (checkedId == R.id.radioButton_siInversiones) {
                mViewModel.setHayInversiones(true);
            } else if (checkedId == R.id.radioButton_noInversiones) {    // Si no
                mViewModel.setHayInversiones(false);
            } else {    // Si se desasigna
                mViewModel.nulificarHayInversiones();
            }
        });
        // Activar lo que toque
        Boolean hayInversionesVM = mViewModel.getHayInversiones();
        if (hayInversionesVM != null && hayInversionesVM) {
            mRadioGroup.check(R.id.radioButton_siInversiones);
        } else if (hayInversionesVM != null) {
            mRadioGroup.check(R.id.radioButton_noInversiones);
        }

        return vistaFragmento;
    }


}
