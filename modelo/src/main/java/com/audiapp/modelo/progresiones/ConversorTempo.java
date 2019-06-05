package com.audiapp.modelo.progresiones;

import androidx.room.TypeConverter;

public class ConversorTempo {
    @TypeConverter
    public static int tempoAint(Tempo tempo) {
        return tempo.getPpm();
    }

    @TypeConverter
    public static Tempo intAtempo(int ppm) {
        return new Tempo(ppm);
    }
}
