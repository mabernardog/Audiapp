package com.audiapp.db;

import android.util.Log;

import androidx.annotation.Nullable;

import com.audiapp.excepciones.DBNotCreatedExcepction;
import com.audiapp.modelo.daos.AcordesDAO;
import com.audiapp.modelo.progresiones.Acorde;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

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

    @Override
    public List<Acorde> leerDeProgresion(int idProgresion) {
        final Semaphore semaphore = new Semaphore(0, false);
        Executor ejecutor = Executors.newSingleThreadExecutor();
        final ArrayList<List<Acorde>> lista = new ArrayList<>();
        ejecutor.execute(() -> {
            try {
                lista.add(AudiappDB.getDB().acordesDAO().leerDeProgresion(idProgresion));
            } catch (DBNotCreatedExcepction dbnce) {
                Log.wtf("DB", "Error fatal: uso incorrecto de AudiappDB" + Arrays.toString(dbnce.getStackTrace()));
            } finally {
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Log.e("GestorAcordesDB", Arrays.toString(e.getStackTrace()));
        }
        return lista.get(0);
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
