package com.audiapp.crearprogresion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.audiapp.R;
import com.audiapp.modelo.Acorde;
import com.audiapp.viewmodels.AppViewModel;
import com.audiapp.viewmodels.CrearProgresionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


class CrearProgresionAcordesFragment extends Fragment {
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

    private CrearProgresionViewModel mViewModel;

    private View.OnClickListener eliminarAcorde = v -> {
        TableRow filaaRef = (TableRow) v.getParent();
        assert mTableLayout != null;
        mTableLayout.removeView(filaaRef);
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(CrearProgresionViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_crear_progresion_acordes, container, false);
        // Bindear Butterknife
        ButterKnife.bind(this, vistaFragmento);
        // Determinar NavController
        NavController mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.general_host);
        // Hacer que el mToolbar lo autogestione NavigationUI
        AppViewModel appViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class);
        assert toolbar != null;
        NavigationUI.setupWithNavController(toolbar, mNavController, appViewModel.getAppBarConfiguration());
        toolbar.getMenu().getItem(0).setOnMenuItemClickListener(v -> {
            mNavController.navigate(R.id.action_crearProgresionAcordesFragment_to_referenciaAcordesDialog);
            return true;
        });
        // Crear el OnClick de eliminar fila
        assert mTableLayout != null;
        View.OnClickListener eliminarAcorde = v -> {
            TableRow filaaRef = (TableRow) v.getParent();
            mTableLayout.removeView(filaaRef);
        };

        // Si no hay acordes en la progresión
        ArrayList<Acorde> listaAcordesVM = mViewModel.getProgresion().getAcordeArrayList();
        if (listaAcordesVM == null || listaAcordesVM.isEmpty()) {
            // Añadir un acorde al inicio por defecto
            TableRow rowInicial = new TableRow(getContext());
            rowInicial.addView(new AcordeView(getContext(), null));
            Button boton0 = (Button) View.inflate(getContext(), R.layout.plantilla_boton, null);
            boton0.setOnClickListener(eliminarAcorde);
            boton0.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_delete, 0, 0, 0);
            boton0.setText(R.string.text_borrar);
            rowInicial.addView(boton0);
            mTableLayout.addView(rowInicial);
        } else {
            inicializarConViewModel();  // Si los hay, usar estos acordes
        }

        // Añadir el onClick de añadir AcordeView nuevo
        assert botonAdd != null && mScrollView != null;
        botonAdd.setOnClickListener(v -> {
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
        mFab.setOnClickListener(v -> {
            /* Validar acordes */
            ArrayList<Acorde> listaAcordes = new ArrayList<>();
            for (int i = 0; i < mTableLayout.getChildCount(); i++) {
                AcordeView vistaAcorde = (AcordeView) ((TableRow) mTableLayout.getChildAt(i)).getChildAt(0);
                Acorde acorde = vistaAcorde.getAcorde();
                // Si el acorde sacado es nulo
                if (acorde == null) {
                    vistaAcorde.setError(); // Marcarlo como erróneo
                } else {
                    listaAcordes.add(acorde); // Añadirlo a la lista
                }
            }
            // Si todos son válidos, los meto en la progresión del ViewModel y navego
            if (mTableLayout.getChildCount() == listaAcordes.size()) {
                mViewModel.getProgresion().setAcordeArrayList(listaAcordes);
                mNavController.navigate(R.id.action_crearProgresionAcordes_to_crearProgresionTempo);
            } else {
                // Si no, pido al usuario que corrija los acordes
                Toast toast = Toast.makeText(getContext(), R.string.toast_corregir_acordes_crear_progresion, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return vistaFragmento;
    }

    private void inicializarConViewModel() {
        for (Acorde acorde : mViewModel.getProgresion().getAcordeArrayList()) {
            TableRow tableRow = new TableRow(getContext());
            AcordeView vistaAcorde = new AcordeView(getContext(), null);
            vistaAcorde.setAcorde(acorde);
            tableRow.addView(vistaAcorde);
            Button botonNuevo = (Button) View.inflate(getContext(), R.layout.plantilla_boton, null);
            botonNuevo.setOnClickListener(eliminarAcorde);
            botonNuevo.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_delete, 0, 0, 0);
            botonNuevo.setText(R.string.text_borrar);
            tableRow.addView(botonNuevo);
            Objects.requireNonNull(mTableLayout).addView(tableRow);
        }
    }

}
