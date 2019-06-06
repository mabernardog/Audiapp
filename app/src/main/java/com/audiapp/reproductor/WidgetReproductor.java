package com.audiapp.reproductor;

import android.content.Context;
import android.widget.MediaController;

public class WidgetReproductor extends MediaController {
    public WidgetReproductor(Context context) {
        super(context, false);
    }

    // Sin ocultamiento
    public void hide() {
    }

    // Para si hiciera falta ocultarla manualmente
    public void hideManual() {
        super.hide();
    }
}
