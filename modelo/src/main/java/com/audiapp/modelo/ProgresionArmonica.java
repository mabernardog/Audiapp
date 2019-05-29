package com.audiapp.modelo;

import java.util.ArrayList;

public class ProgresionArmonica {
    private ArrayList<Acorde> mAcordeArrayList;
    private Tempo mTempo;
    private String mArchivo;

    public ArrayList<Acorde> getAcordeArrayList() {
        return this.mAcordeArrayList;
    }

    public void setAcordeArrayList(ArrayList<Acorde> listaAcordes) {
        this.mAcordeArrayList = listaAcordes;
    }

    public Tempo getTempo() {
        return mTempo;
    }

    public void setTempo(Tempo mTempo) {
        this.mTempo = mTempo;
    }

    public String getArchivo() {
        return this.mArchivo;
    }

    public void setArchivo(String archivo) {
        this.mArchivo = archivo;
    }
}
