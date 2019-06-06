package com.audiapp.midigen;

import com.audiapp.modelo.progresiones.Acorde;
import com.audiapp.modelo.progresiones.ProgresionArmonica;

import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Score;
import jm.util.Write;

import static jm.constants.Durations.C;
import static jm.constants.Durations.SB;

public final class MidiGen {
    private static final Score s = new Score("Progresión armónica");
    private static final Part p = new Part("Piano", 0, 0);

    public static void generarTestEn(String file) {
        //Let us know things have started
        System.out.println("Creating chord progression . . .");

        //choose rootPitch notes around the cycle of fifths
        int rootPitch = 60; //set start note to middle C
        for (int i = 0; i < 6; i++) {
            secondInversion(rootPitch);
            rootPitch -= 7;
            rootPosition(rootPitch);
            rootPitch += 5;
        }

        //add a final chord
        ending(rootPitch);

        // Testeo del tempo
        p.setTempo(100);

        //pack the part into a score
        s.addPart(p);

        // write the score to a MIDIfile
        Write.midi(s, file);
    }

    public static void generarProgresion(ProgresionArmonica progresionArmonica, String file) {
        s.empty();
        p.empty();
        for (Acorde acorde : progresionArmonica.getAcordeArrayList()) {
            p.addCPhrase(acorde.getAcorde());
        }
        p.setTempo(progresionArmonica.getTempo().getPpm());
        s.addPart(p);
        Write.midi(p, file);
    }

    private static void rootPosition(int rootPitch) {
        // build the chord from the rootPitch
        int[] pitchArray = new int[4];
        pitchArray[0] = rootPitch;
        pitchArray[1] = rootPitch + 4;
        pitchArray[2] = rootPitch + 7;
        pitchArray[3] = rootPitch + 10;
        //add chord to the part
        CPhrase chord = new CPhrase();
        chord.addChord(pitchArray, C);
        p.addCPhrase(chord);
    }

    private static void secondInversion(int rootPitch) {
        // build the chord from the rootPitch
        int[] pitchArray = new int[4];
        pitchArray[0] = rootPitch;
        pitchArray[1] = rootPitch + 4;
        pitchArray[2] = rootPitch - 2;
        pitchArray[3] = rootPitch - 5;
        //add chord to the part
        CPhrase chord = new CPhrase();
        chord.addChord(pitchArray, C);
        p.addCPhrase(chord);
    }

    private static void ending(int rootPitch) {
        // build the chord from the rootPitch
        int[] pitchArray = new int[4];
        pitchArray[0] = rootPitch;
        pitchArray[1] = rootPitch + 4;
        pitchArray[2] = rootPitch + 7;
        pitchArray[3] = rootPitch + 12;
        //add chord to the part
        CPhrase chord = new CPhrase();
        chord.addChord(pitchArray, SB);
        p.addCPhrase(chord);
    }
}
