package com.audiapp.progresiones;

import java.util.ArrayList;

import androidx.lifecycle.ViewModel;

public class ProgresionTonalViewModel extends ViewModel {
    private ArrayList<String> tiposProgresion;

    public ArrayList<String> getTiposProgresion() {
        if(tiposProgresion == null) tiposProgresion = new ArrayList<>();
        return tiposProgresion;
    }

    public void setTiposProgresion(ArrayList<String> tiposProgresion) {
        this.tiposProgresion = tiposProgresion;
    }
}
