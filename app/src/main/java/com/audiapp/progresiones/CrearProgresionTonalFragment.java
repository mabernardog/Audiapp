
package com.audiapp.progresiones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.audiapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;


public class CrearProgresionTonalFragment extends Fragment {
    @Nullable @BindView(R.id.bottom_nav_progresionTonal) BottomNavigationView mBottomNavigationView;
    private NavController mNavController;

    private ElegirProgresionViewModel mViewModel;

    public static CrearProgresionTonalFragment newInstance() {
        return new CrearProgresionTonalFragment();
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
        View vistaFragmento;
        try{
            vistaFragmento = inflater.inflate(R.layout.fragment_crear_progresion_tonal, container, false);
        } catch (Exception e)
        {
            return null;
        }
        ButterKnife.bind(this, vistaFragmento);
        try {
            // Determinar NavController
            mNavController = Navigation.findNavController(vistaFragmento.findViewById(R.id.progresion_tonal_host));
            // Hacer que el BottomNavigation lo autogestione NavigationUI
            NavigationUI.setupWithNavController(mBottomNavigationView, mNavController);
        } catch (Exception e)
        {
            return vistaFragmento;
        }
        return vistaFragmento;
    }

}
