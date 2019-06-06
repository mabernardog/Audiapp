package com.audiapp.modelo.progresiones;

import androidx.room.TypeConverter;

import java.util.Date;

public class ConversorFecha {
    @TypeConverter
    public static long fechaAlong(Date fecha) {
        return fecha.getTime();
    }

    @TypeConverter
    public static Date longAfecha(long fecha) {
        return new Date(fecha);
    }
}
