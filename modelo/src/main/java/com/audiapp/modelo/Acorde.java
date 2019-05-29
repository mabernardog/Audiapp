package com.audiapp.modelo;

import org.jfugue.theory.Chord;
import org.jfugue.theory.Note;

import jm.constants.Durations;
import jm.music.data.CPhrase;

// Todo: parametrizar para Room como Entidad con sus atributos
public class Acorde {
    private CPhrase mAcorde;
    private String mCadena;
    private String mFigura;
    private String mTipo;
    private String mTonalidad;
    private int mInversion;


    public Acorde(String acorde, String figura) throws InstantiationException {
        // Si el acorde es válido
        if (Chord.isValidChord(acorde)) {
            // Generar acorde de jFugue
            Chord chord = new Chord(acorde);
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
}
