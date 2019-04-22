package utils;

import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public final class OperacionesTablaBotones {
    private OperacionesTablaBotones() {
    }

    public static ArrayList<CheckBox> getBotonesDeTabla(TableLayout tabla) {
        ArrayList<CheckBox> botones = new ArrayList<>();
        for (int i = 0; i < tabla.getChildCount(); i++) {
            botones.add((CheckBox) ((TableRow) tabla.getChildAt(i)).getChildAt(0));
        }
        return botones;
    }
}
