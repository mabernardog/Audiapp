package com.audiapp.modelo.progresiones;

import android.text.TextUtils;

import androidx.room.TypeConverter;

import java.util.Collections;
import java.util.HashSet;

public class ConversorTonalidades {
    @TypeConverter
    public static String tonalidadesAstring(HashSet<String> tonalidades) {
        return TextUtils.join(",", tonalidades);
    }

    @TypeConverter
    public static HashSet<String> stringAtonalidades(String tonalidades) {
        HashSet<String> set = new HashSet<>();
        String[] contenido = tonalidades.split(",");
        Collections.addAll(set, contenido);
        return set;
    }
}
