package com.audiapp.modelo.progresiones;

public class Tempo {
    private int ppm;
    private String clasificacion;

    // Constructor: dados los PPMs, le da la clasificación
    public Tempo(int ppm) {
        this.ppm = ppm;
        if (ppm >= 10 && ppm < 30) clasificacion = "Largo";
        else if (ppm >= 30 && ppm < 40) clasificacion = "Grave";
        else if (ppm >= 40 && ppm < 66) clasificacion = "Lento";
        else if (ppm >= 66 && ppm < 76) clasificacion = "Adagio";
        else if (ppm >= 76 && ppm < 108) clasificacion = "Andante";
        else if (ppm == 109) clasificacion = "Allegretto";
        else if (ppm >= 110 && ppm < 168) clasificacion = "Allegro";
        else if (ppm >= 168 && ppm < 200) clasificacion = "Presto";
        else if (ppm >= 200 && ppm < 240) clasificacion = "Prestissimo";
    }

    // Constructor: dada la clasificación, le da los PPMs
    public Tempo(String clasificacion) {
        this.clasificacion = clasificacion;
        switch (clasificacion) {
            case "Largo":
                ppm = 20;
                break;
            case "Grave":
                ppm = 40;
                break;
            case "Lento":
                ppm = 50;
                break;
            case "Adagio":
                ppm = 71;
                break;
            case "Andante":
                ppm = 92;
                break;
            case "Allegretto":
                ppm = 116;
                break;
            case "Allegro":
                ppm = 139;
                break;
            case "Presto":
                ppm = 184;
                break;
            case "Prestissimo":
                ppm = 200;
                break;
        }

    }

    public int getPpm() {
        return ppm;
    }

    public String getClasificacion() {
        return clasificacion;
    }
}
