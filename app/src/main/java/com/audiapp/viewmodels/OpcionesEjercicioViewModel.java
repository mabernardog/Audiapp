package com.audiapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.audiapp.modelo.progresiones.Tempo;

import java.util.ArrayList;

public class OpcionesEjercicioViewModel extends ViewModel {
    private ArrayList<String> tiposProgresion;
    private Boolean hayInversiones;
    private ArrayList<ArrayList<String>> tonalidadesProgresion; // El 1er elemento representa a las mayores, el 2º a las menores
    private Tempo tempo;

    public ArrayList<String> getTiposProgresion() {
        if (tiposProgresion == null) {
            tiposProgresion = new ArrayList<>();
        }
        return tiposProgresion;
    }

    public Boolean getHayInversiones() {
        return hayInversiones;
    }

    public void setHayInversiones(boolean hayInversiones) {
        if (this.hayInversiones == null) {
            this.hayInversiones = hayInversiones;
            return;
        }
        this.hayInversiones = hayInversiones;
    }

    public void nulificarHayInversiones() {
        hayInversiones = null;
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

    public boolean esValido() {
        // Validar el uso de cada uno una vez
        if (tiposProgresion == null || hayInversiones == null || tonalidadesProgresion == null || tempo == null) {
            return false;
        }
        // Validar que haya algo en los tipos e inversiones de la progresión
        if (tiposProgresion.isEmpty()) {
            return false;
        }
        // Validar que haya alguna tonalidad mayor o menor en la lista
        return !tonalidadesProgresion.get(0).isEmpty() || !tonalidadesProgresion.get(1).isEmpty();// Si no se sale por algún if anterior, la progresión es sintetizable
    }

    public void limpiar() {
        if (tiposProgresion != null) tiposProgresion.clear();
        if (tonalidadesProgresion != null) tonalidadesProgresion.clear();
        hayInversiones = null;
        tempo = null;
    }
}
