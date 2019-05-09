package com.audiapp.progresiones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.audiapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


class OpcionTonalidadEjercicioFragment extends Fragment {
    @Nullable
    @BindView(R.id.tablaTonalidadesProgresionLineal)
    TableLayout mTableLayout;
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
        View vistaFragmento = inflater.inflate(R.layout.fragment_opcion_tonalidad_ejercicio, container, false);
        ButterKnife.bind(this, vistaFragmento);
        // Crear los listeners para los botones
        assert mTableLayout != null;
        for (int i = 0; i < mTableLayout.getChildCount(); i++) {
            TableRow columna = (TableRow) mTableLayout.getChildAt(i);
            ((CheckBox) columna.getChildAt(0)).setOnCheckedChangeListener((buttonView, isChecked) -> {
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
                    int j = 0;
                    for (String tipo : mViewModel.getTonalidadesMayoresProgresion()) {
                        if (tipo.contentEquals(buttonView.getText())) {
                            mViewModel.getTonalidadesMayoresProgresion().remove(j);
                            break;
                        }
                        j++;
                    }
                }
            });
            ((CheckBox) columna.getChildAt(1)).setOnCheckedChangeListener((buttonView, isChecked) -> {
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
                    int j = 0;
                    for (String tipo : mViewModel.getTonalidadesMenoresProgresion()) {
                        if (tipo.contentEquals(buttonView.getText())) {
                            mViewModel.getTonalidadesMenoresProgresion().remove(j);
                            break;
                        }
                        j++;
                    }
                }
            });
        }
        // Recorrer el view model y activar los botones que toquen
        for (String tipo : mViewModel.getTonalidadesMayoresProgresion()) {
            for (int i = 0; i < mTableLayout.getChildCount(); i++) {
                CheckBox checkBox = (CheckBox) ((TableRow) mTableLayout.getChildAt(i)).getChildAt(0);
                if (tipo.contentEquals(checkBox.getText())) {
                    checkBox.setChecked(true);
                    break;
                }
            }
        }
        for (String tipo : mViewModel.getTonalidadesMenoresProgresion()) {
            for (int i = 0; i < mTableLayout.getChildCount(); i++) {
                CheckBox checkBox = (CheckBox) ((TableRow) mTableLayout.getChildAt(i)).getChildAt(1);
                if (tipo.contentEquals(checkBox.getText())) {
                    checkBox.setChecked(true);
                    break;
                }
            }
        }

        return vistaFragmento;
    }

}
