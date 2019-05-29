package com.audiapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.audiapp.modelo.ProgresionArmonica;

public class CrearProgresionViewModel extends ViewModel {
    private ProgresionArmonica mProgresion;

    public ProgresionArmonica getProgresion() {
        if (mProgresion == null) {   // Crear si es nulo
            mProgresion = new ProgresionArmonica();
        }
        return mProgresion;
    }

    public void setProgresion(ProgresionArmonica progresion) {
        this.mProgresion = progresion;
    }
}
