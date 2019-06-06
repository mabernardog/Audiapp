package com.audiapp.gestionprogresiones;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.audiapp.R;
import com.audiapp.db.GestorAcordesDB;
import com.audiapp.db.GestorDB;
import com.audiapp.db.GestorProgresionesDB;
import com.audiapp.modelo.progresiones.Acorde;
import com.audiapp.modelo.progresiones.ProgresionArmonica;
import com.audiapp.viewmodels.AppViewModel;
import com.audiapp.viewmodels.GestionProgresionesViewModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerProgresionesFragment extends Fragment implements ListaProgresionesAdaptador.OnProgresionClickListener {
    @Nullable
    @BindView(R.id.toolbar_verProgresiones)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.recycler_listaProgresiones)
    RecyclerView mRecyclerView;
    private ArrayList<ProgresionArmonica> listaProgresiones;
    private NavController mNavController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_ver_progresiones, container, false);
        ButterKnife.bind(this, vistaFragmento);
        // Determinar NavController
        mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.general_host);
        // Hacer que el mToolbar lo autogestione NavigationUI
        AppViewModel appViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class);
        assert toolbar != null;
        NavigationUI.setupWithNavController(toolbar, mNavController, appViewModel.getAppBarConfiguration());
        // Configurar el RecyclerView
        assert mRecyclerView != null;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listaProgresiones = (ArrayList<ProgresionArmonica>) ((GestorProgresionesDB) Objects.requireNonNull(new GestorDB().acceder("Progresiones"))).leerTodos();
        for (ProgresionArmonica p : Objects.requireNonNull(listaProgresiones)) {
            p.setAcordeArrayList((ArrayList<Acorde>) ((GestorAcordesDB) Objects.requireNonNull(new GestorDB().acceder("Acordes"))).leerDeProgresion(p.getId()));
        }
        mRecyclerView.setAdapter(new ListaProgresionesAdaptador(listaProgresiones, this));

        return vistaFragmento;
    }

    @Override
    public void onProgresionClick(int pos) {
        ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(GestionProgresionesViewModel.class).setProgresion(listaProgresiones.get(pos));
        mNavController.navigate(R.id.action_verProgresiones_to_gestorProgresion);
    }
}
