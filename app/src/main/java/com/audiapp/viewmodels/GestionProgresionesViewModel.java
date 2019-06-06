package com.audiapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.audiapp.modelo.progresiones.ProgresionArmonica;

public class GestionProgresionesViewModel extends ViewModel {
    private ProgresionArmonica mProgresion;

    public ProgresionArmonica getProgresion() {
        return mProgresion;
    }

    public void setProgresion(ProgresionArmonica progresion) {
        this.mProgresion = progresion;
    }

    public void limpiar() {
        mProgresion = null;
    }
}
