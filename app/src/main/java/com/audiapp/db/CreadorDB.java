package com.audiapp.db;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CreadorDB extends GestorTabla {
    public void crearDB(@NonNull Context context) {
        Executor ejecutor = Executors.newSingleThreadExecutor();
        ejecutor.execute(() -> AudiappDB.getDB(context));
    }
}
