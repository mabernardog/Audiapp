package com.audiapp.crearprogresion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.audiapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


class CrearProgresionFragment extends Fragment {
    @Nullable
    @BindView(R.id.toolbar_crearProgresion)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.listaAcordesProgTL)
    TableLayout mTableLayout;
    @Nullable
    @BindView(R.id.button2)
    Button botonAdd;
    @Nullable
    @BindView(R.id.listaAcordesProgSV)
    ScrollView mScrollView;
    @Nullable
    @BindView(R.id.fabCrearProgresion)
    FloatingActionButton mFab;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_crear_progresion, container, false);
        // Bindear Butterknife
        ButterKnife.bind(this, vistaFragmento);
        // Determinar NavController
        NavController mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.general_host);
        // Hacer que el mToolbar lo autogestione NavigationUI
        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph()).build();
        assert toolbar != null;
        NavigationUI.setupWithNavController(toolbar, mNavController, mAppBarConfiguration);


        // Crear el OnClick de eliminar fila
        assert mTableLayout != null;
        View.OnClickListener eliminarAcorde = v -> {
            TableRow filaaRef = (TableRow) v.getParent();
            mTableLayout.removeView(filaaRef);
        };


        // Añadir un acorde al inicio por defecto
        TableRow rowInicial = new TableRow(getContext());
        rowInicial.addView(new AcordeView(getContext(), null));
        Button boton0 = (Button) View.inflate(getContext(), R.layout.plantilla_boton, null);
        boton0.setOnClickListener(eliminarAcorde);
        boton0.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_delete, 0, 0, 0);
        boton0.setText(R.string.text_borrar);
        rowInicial.addView(boton0);
        mTableLayout.addView(rowInicial);

        // Añadir el onClick de añadir AcordeView nuevo
        assert botonAdd != null && mScrollView != null;
        botonAdd.setOnClickListener(v -> {
            //Acorde cho =  ((AcordeView) ((TableRow) mTableLayout.getChildAt(mTableLayout.getChildCount()-1)).getChildAt(0)).getAcorde();
            TableRow tableRow = new TableRow(getContext());
            tableRow.addView(new AcordeView(getContext(), null));
            Button botonNuevo = (Button) View.inflate(getContext(), R.layout.plantilla_boton, null);
            botonNuevo.setOnClickListener(eliminarAcorde);
            botonNuevo.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_delete, 0, 0, 0);
            botonNuevo.setText(R.string.text_borrar);
            tableRow.addView(botonNuevo);
            mTableLayout.addView(tableRow);
            mScrollView.smoothScrollTo(0, mScrollView.getBottom());
            mScrollView.post(() -> mScrollView.smoothScrollTo(0, mScrollView.getBottom()));
        });
        botonAdd.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_add, 0, 0, 0);

        // Configurar el FAB
        assert mFab != null;

        return vistaFragmento;
    }

}
