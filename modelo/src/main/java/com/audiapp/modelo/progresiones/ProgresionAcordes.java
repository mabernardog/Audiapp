package com.audiapp.modelo.progresiones;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.TypeConverters;

@Entity(tableName = "PROGRESION_ACORDES",
        primaryKeys = {"idProgresion", "idAcorde"},
        foreignKeys = {
                @ForeignKey(entity = ProgresionArmonica.class,
                        parentColumns = "id",
                        childColumns = "idProgresion"),
                @ForeignKey(entity = Acorde.class,
                        parentColumns = "id",
                        childColumns = "idAcorde")
        })
@TypeConverters({ConversorTempo.class})
public class ProgresionAcordes {
    private int idProgresion;
    private int idAcorde;
}
