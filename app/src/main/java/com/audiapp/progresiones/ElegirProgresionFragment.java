package com.audiapp.progresiones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.audiapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class ElegirProgresionFragment extends Fragment {

    private ElegirProgresionViewModel mViewModel;

    public static ElegirProgresionFragment newInstance() {
        return new ElegirProgresionFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
        {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ElegirProgresionViewModel.class);
        // TODO: Use the ViewModel
        }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
        {
        return inflater.inflate(R.layout.fragment_elegir_progresion, container, false);
        }

public void onClickGoTonal(View v)
    {
    Navigation.findNavController(v.getRootView()).navigate(R.id.fragment_crearProgresionTonal);
    // Navigation.createNavigateOnClickListener(R.id.fragment_crearTonal, null);
    }

public void onClickGoLibre(View v)
    {
    Navigation.findNavController(v.getRootView()).navigate(R.id.fragment_crearProgresionLibre);
    }


}
