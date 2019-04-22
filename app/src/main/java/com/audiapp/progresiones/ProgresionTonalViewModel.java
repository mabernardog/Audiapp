package com.audiapp.progresiones;

import androidx.lifecycle.ViewModel;

import com.audiapp.modelo.Tempo;

import java.util.ArrayList;

public class ProgresionTonalViewModel extends ViewModel {
    private ArrayList<String> tiposProgresion;
    private ArrayList<String> inversionesProgresion;
    private ArrayList<ArrayList<String>> tonalidadesProgresion; // El 1er elemento representa a las mayores, el 2º a las menores
    private Tempo tempo;

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

    public ArrayList<String> getTonalidadesMayoresProgresion() {
        if (tonalidadesProgresion == null) {
            tonalidadesProgresion = new ArrayList<>();
            tonalidadesProgresion.add(new ArrayList<>());
            tonalidadesProgresion.add(new ArrayList<>());
        }
        return tonalidadesProgresion.get(0);
    }

    public ArrayList<String> getTonalidadesMenoresProgresion() {
        if (tonalidadesProgresion == null) {
            tonalidadesProgresion = new ArrayList<>();
            tonalidadesProgresion.add(new ArrayList<>());
            tonalidadesProgresion.add(new ArrayList<>());
        }
        return tonalidadesProgresion.get(1);
    }

    public Tempo getTempo() {
        return tempo;
    }

    public void setTempo(Tempo tempo) {
        this.tempo = tempo;
    }
}
