package com.audiapp.crearprogresion;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.audiapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReferenciaAcordesFragment extends Fragment {


    public ReferenciaAcordesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_referencia_acordes, container, false);
    }

}
