package com.audiapp.modelo.progresiones;

import androidx.room.TypeConverter;

public class ConversorDificultadProgresion {
    @TypeConverter
    public static String dificultadAstring(DificultadProgresion dificultad) {
        return dificultad.toString();
    }

    @TypeConverter
    public static DificultadProgresion stringAdificultad(String string) {
        return DificultadProgresion.valueOf(string);
    }
}
