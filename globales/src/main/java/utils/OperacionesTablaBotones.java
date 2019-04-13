package utils;

import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public final class OperacionesTablaBotones {
    private OperacionesTablaBotones() { }

    public static ArrayList<String> getBotonesActivos(TableLayout tabla) {
        ArrayList<CheckBox> botones = getBotonesDeTabla(tabla);

        ArrayList<String> textos = new ArrayList<>();
        for(CheckBox boton : botones) {
            if(boton.isChecked()) {
                textos.add((String) boton.getText());
            }
        }
        return textos;
    }
    public static ArrayList<CheckBox> getBotonesDeTabla(TableLayout tabla) {
        ArrayList<CheckBox> botones = new ArrayList<>();
        for(int i=0; i<tabla.getChildCount(); i++) {
            botones.add((CheckBox) ((TableRow) tabla.getChildAt(i)).getChildAt(0));
        }
        return botones;
    }
}
