package com.audiapp.progresiones;

import java.util.ArrayList;

import androidx.lifecycle.ViewModel;

public class ProgresionTonalViewModel extends ViewModel {
    private ArrayList<String> tiposProgresion;
    private ArrayList<String> inversionesProgresion;

    public ArrayList<String> getTiposProgresion() {
        if (tiposProgresion == null) {
            tiposProgresion = new ArrayList<>();
        }
        return tiposProgresion;
    }

    public ArrayList<String> getInversionesProgresion() {
        if (inversionesProgresion == null) {
            inversionesProgresion = new ArrayList<>();
        }
        return inversionesProgresion;
    }
}
