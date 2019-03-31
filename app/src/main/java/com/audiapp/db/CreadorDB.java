package com.audiapp.db;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

public class CreadorDB extends GestorTabla
{
public void crearDB(@NonNull Context context)
    {
    Executor ejecutor = Executors.newSingleThreadExecutor();
    ejecutor.execute(() -> AudiappDB.getDB(context));
    }
}
