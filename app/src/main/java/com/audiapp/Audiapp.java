package com.audiapp;

import android.app.Application;

import com.audiapp.db.CreadorDB;
import com.audiapp.db.GestorDB;
import com.audiapp.retrofit.RetroAudiappFit;

import java.util.Objects;

public class Audiapp extends Application {
    private static Audiapp instancia;
    private RetroAudiappFit retrofit;

    public static Audiapp getInstancia() {
        return instancia;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instancia = this;
        retrofit = new RetroAudiappFit(getApplicationContext().getResources().openRawResource(R.raw.audiapp));
        ((CreadorDB) Objects.requireNonNull(new GestorDB().acceder("CrearDB"))).crearDB(getApplicationContext());
    }

    public RetroAudiappFit getRetroAudiappFit() {
        return retrofit;
    }

    public void resetRetroAudiappFit() {
        retrofit = new RetroAudiappFit(getApplicationContext().getResources().openRawResource(R.raw.audiapp));
    }
}

