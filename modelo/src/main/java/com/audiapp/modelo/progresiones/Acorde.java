package com.audiapp.modelo.progresiones;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jfugue.theory.Chord;
import org.jfugue.theory.Note;

import jm.constants.Durations;
import jm.music.data.CPhrase;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "ACORDES",
        indices = {/*@Index(value = {"cadena"}, unique = true),*/ @Index(value = {"id", "idProgresion"})},
        foreignKeys = {
                @ForeignKey(entity = ProgresionArmonica.class,
                        parentColumns = "id",
                        childColumns = "idProgresion",
                        onDelete = CASCADE)
        })
public class Acorde {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "cadena")
    private String mCadena;
    @ColumnInfo(name = "figura")
    private String mFigura;
    @ColumnInfo(name = "tipo")
    private String mTipo;
    @ColumnInfo(name = "tonalidad")
    private String mTonalidad;
    @ColumnInfo(name = "inversion")
    private int mInversion;
    @ColumnInfo(name = "idProgresion")
    private int idProgresion;
    @Ignore
    private CPhrase mAcorde;


    public Acorde(String acorde, String figura) throws InstantiationException {
        // Si el acorde es válido
        if (Chord.isValidChord(acorde)) {
            // Generar acorde de jFugue
            Chord chord;
            try {
                chord = new Chord(acorde);
            } catch (NullPointerException e) {
                // Evita crashes por poner s en vez de #
                throw new InstantiationException("Acorde incorrecto: " + acorde);
            }
            // Caracterizar acorde e inicializar los atributos
            this.mCadena = chord.toHumanReadableString();
            this.mFigura = figura;
            this.mTipo = chord.getChordType();  // DOM7, MAJ...
            this.mInversion = chord.getInversion();  // 0, 1, 2, 3 (sin, 1ª, 2ª y 3ª inversión)
            this.mTonalidad = chord.getRoot().getOriginalString();
            // Obtener las notas de jFugue
            Note[] chordNotes = chord.getNotes();
            // Transformarlo a notas de jMusic
            int[] notas = new int[chordNotes.length];
            for (int i = 0; i < chordNotes.length; i++)
                notas[i] = chordNotes[i].getValue() /*+ 12*/; // No sumar 12 si hay que restar 12 a la nota inicial
            mAcorde = new CPhrase();
            switch (figura) {
                case "Negra":
                    this.mAcorde.addChord(notas, Durations.CROTCHET);
                    break;
                case "Corchea":
                    this.mAcorde.addChord(notas, Durations.QUAVER);
                    break;
                case "Blanca":
                    this.mAcorde.addChord(notas, Durations.MINIM);
                    break;
                case "Redonda":
                    this.mAcorde.addChord(notas, Durations.SEMIBREVE);
                    break;
                case "Semicor.":
                    this.mAcorde.addChord(notas, Durations.SEMI_QUAVER);
                    break;
                default:
                    throw new InstantiationException("Error con la figura: " + figura);
            }
        } else {
            // Si el acorde es inválido tirar excepción
            throw new InstantiationException("Acorde incorrecto: " + acorde);
        }
    }

    // Todo: implementar constructores para Room
    public Acorde(String cadena, String figura, String tipo, String tonalidad, int inversion, int idProgresion) {
        this.idProgresion = idProgresion;
        this.mCadena = cadena;
        this.mFigura = figura;
        this.mTipo = tipo;
        this.mTonalidad = tonalidad;
        this.mInversion = inversion;

        // Generar acorde de jFugue
        Chord chord = new Chord(cadena);
        // Obtener las notas de jFugue
        Note[] chordNotes = chord.getNotes();
        // Transformarlo a notas de jMusic
        int[] notas = new int[chordNotes.length];
        for (int i = 0; i < chordNotes.length; i++)
            notas[i] = chordNotes[i].getValue() /*+ 12*/; // No sumar 12 si hay que restar 12 a la nota inicial
        mAcorde = new CPhrase();
        switch (figura) {
            case "Negra":
                this.mAcorde.addChord(notas, Durations.CROTCHET);
                break;
            case "Corchea":
                this.mAcorde.addChord(notas, Durations.QUAVER);
                break;
            case "Blanca":
                this.mAcorde.addChord(notas, Durations.MINIM);
                break;
            case "Redonda":
                this.mAcorde.addChord(notas, Durations.SEMIBREVE);
                break;
            case "Semicor.":
                this.mAcorde.addChord(notas, Durations.SEMI_QUAVER);
                break;
            default:
                break;
        }
    }

    public CPhrase getAcorde(String cadena, String figura, String masCosasAquiParaRoom) {
        return mAcorde;
    }

    public CPhrase getAcorde() {
        return this.mAcorde;
    }

    public String getCadena() {
        return mCadena;
    }

    public String getFigura() {
        return mFigura;
    }

    public String getTipo() {
        return mTipo;
    }

    public String getTonalidad() {
        return mTonalidad;
    }

    public int getInversion() {
        return mInversion;
    }

    public int getIdProgresion() {
        return idProgresion;
    }

    public void setIdProgresion(int idProgresion) {
        this.idProgresion = idProgresion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
