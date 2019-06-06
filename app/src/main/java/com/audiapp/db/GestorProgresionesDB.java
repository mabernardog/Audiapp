package com.audiapp.db;

import android.util.Log;

import androidx.annotation.Nullable;

import com.audiapp.excepciones.DBNotCreatedExcepction;
import com.audiapp.modelo.daos.ProgresionesDAO;
import com.audiapp.modelo.progresiones.ProgresionArmonica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

// Todo: implementar recuperación de datos
public class GestorProgresionesDB extends GestorTabla implements ProgresionesDAO {
    @Deprecated
    @Override
    public void crear(ProgresionArmonica p) {
    }

    @Override
    public long crearPK(ProgresionArmonica p) {
        final Semaphore semaphore = new Semaphore(0, false);
        Executor ejecutor = Executors.newSingleThreadExecutor();
        ArrayList<Long> idInsertado = new ArrayList<>();
        p.setFecha();
        ejecutor.execute(() -> {
            try {
                idInsertado.add(AudiappDB.getDB().progresionesDAO().crearPK(p));
            } catch (DBNotCreatedExcepction dbnce) {
                Log.wtf("DB", "Error fatal: uso incorrecto de AudiappDB" + Arrays.toString(dbnce.getStackTrace()));
                idInsertado.add(-1L);
            } finally {
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Log.e("GestorProgresionesDB", Arrays.toString(e.getStackTrace()));
            return -1;
        }
        return idInsertado.get(0);
    }

    @Nullable
    @Override
    public List<ProgresionArmonica> leerTodos() {
        final Semaphore semaphore = new Semaphore(0, false);
        Executor ejecutor = Executors.newSingleThreadExecutor();
        final ArrayList<List<ProgresionArmonica>> lista = new ArrayList<>();
        ejecutor.execute(() -> {
            try {
                lista.add(AudiappDB.getDB().progresionesDAO().leerTodos());
            } catch (DBNotCreatedExcepction dbnce) {
                Log.wtf("DB", "Error fatal: uso incorrecto de AudiappDB" + Arrays.toString(dbnce.getStackTrace()));
            } finally {
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Log.e("GestorProgresionesDB", Arrays.toString(e.getStackTrace()));
        }
        return lista.get(0);
    }

    @Override
    public long cuentaProgresionesDeNombre(String nombre) {
        final Semaphore semaphore = new Semaphore(0, false);
        Executor ejecutor = Executors.newSingleThreadExecutor();
        ArrayList<Long> cuenta = new ArrayList<>();
        ejecutor.execute(() -> {
            try {
                cuenta.add(AudiappDB.getDB().progresionesDAO().cuentaProgresionesDeNombre(nombre));
            } catch (DBNotCreatedExcepction dbnce) {
                Log.wtf("DB", "Error fatal: uso incorrecto de AudiappDB" + Arrays.toString(dbnce.getStackTrace()));
                cuenta.add(-1L);
            } finally {
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Log.e("GestorProgresionesDB", Arrays.toString(e.getStackTrace()));
            return -1;
        }
        return cuenta.get(0);
    }

    @Override
    public void actualizar(ProgresionArmonica p) {

    }

    @Override
    public void borrar(ProgresionArmonica p) {

    }

    @Override
    public void borrarTodos() {

    }
}
