package com.audiapp.db;

import android.util.Log;

import androidx.annotation.Nullable;

import com.audiapp.excepciones.DBNotCreatedExcepction;
import com.audiapp.modelo.daos.AcordesDAO;
import com.audiapp.modelo.progresiones.Acorde;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GestorAcordesDB extends GestorTabla implements AcordesDAO {
    @Override
    public void crear(Acorde a) {
        Executor ejecutor = Executors.newSingleThreadExecutor();
        ejecutor.execute(() -> {
            try {
                AudiappDB.getDB().acordesDAO().crear(a);
            } catch (DBNotCreatedExcepction dbnce) {
                Log.wtf("DB", "Error fatal: uso incorrecto de AudiappDB" + Arrays.toString(dbnce.getStackTrace()));
            }
        });
    }

    @Nullable
    @Override
    public List<Acorde> leerTodos() {
        return null;
    }

    @Override
    public void actualizar(Acorde acorde) {

    }

    @Override
    public void borrar(Acorde acorde) {

    }

    @Override
    public void borrarTodos() {

    }
}
