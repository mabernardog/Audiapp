package com.audiapp.modelo.progresiones;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@Entity(tableName = "PROGRESIONES",
        indices = {@Index(value = {"nombre"}, unique = true)})
public class ProgresionArmonica {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "nombre")
    private String mNombre;
    @TypeConverters({ConversorTempo.class})
    @ColumnInfo(name = "tempo")
    private Tempo mTempo;
    @ColumnInfo(name = "ruta")
    private String mArchivo;
    @TypeConverters({ConversorFecha.class})
    @ColumnInfo(name = "fecha_creacion")
    private Date mFecha;
    @TypeConverters({ConversorDificultadProgresion.class})
    @ColumnInfo(name = "dificultad")
    private DificultadProgresion dificultad;
    @TypeConverters({ConversorTonalidades.class})
    @ColumnInfo(name = "tonalidades")
    private HashSet<String> tonalidades;
    @ColumnInfo
    @Ignore
    private ArrayList<Acorde> mAcordeArrayList;

    public ArrayList<Acorde> getAcordeArrayList() {
        return this.mAcordeArrayList;
    }

    public void setAcordeArrayList(ArrayList<Acorde> listaAcordes) {
        this.mAcordeArrayList = listaAcordes;
        /* Determinar dificultad */
        // Por defecto, se entenderá que es fácil
        this.setDificultad(DificultadProgresion.FACIL);
        // Recorrer la lista de acordes
        for (Acorde acorde : listaAcordes) {
            // Si es 7ª dominante
            if (acorde.getTipo().toLowerCase().equals("dom7")) {
                this.setDificultad(DificultadProgresion.INTERMEDIO2); // La dificultad es intermedio2
                break;                                              // No me interesa saber más => salir del for
            } else if (acorde.getInversion() > 0) {
                // Si hay alguna inversión, es dificultad intermedio1 (seguir iterando por si hay alguna 7ª dominante)
                this.setDificultad(DificultadProgresion.INTERMEDIO1);
            }
        }
        /* Determinar tonalidades (añadirlas a un Set: no tendrá repetidos y simplifica implementación) */
        tonalidades = new HashSet<>();
        for (Acorde acorde : listaAcordes) {
            tonalidades.add(acorde.getTonalidad());
        }
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

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String nombre) {
        this.mNombre = nombre;
    }

    public void setNuevoNombre(String nombre) {
        // Determinar la ruta sin nombre
        StringBuilder parteInicial = new StringBuilder();
        String[] partes = mArchivo.split("/");
        for (int i = 0; i < partes.length; i++) {
            if (i != (partes.length - 1)) parteInicial.append(partes[i]).append("/");
        }
        // Renombrar
        File file = new File(mArchivo);
        if (file.renameTo(new File(parteInicial + nombre + ".mid"))) {
            this.mNombre = nombre;
            this.mArchivo = parteInicial + nombre + ".mid";
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DificultadProgresion getDificultad() {
        return dificultad;
    }

    public void setDificultad(DificultadProgresion dificultad) {
        this.dificultad = dificultad;
    }

    public HashSet<String> getTonalidades() {
        return tonalidades;
    }

    public void setTonalidades(HashSet<String> tonalidades) {
        this.tonalidades = tonalidades;
    }

    public Date getFecha() {
        return mFecha;
    }

    public void setFecha(Date fecha) {
        this.mFecha = fecha;
    }

    public void setFecha() {
        this.mFecha = new Date();
    }
}
