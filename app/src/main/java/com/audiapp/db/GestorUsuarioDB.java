package com.audiapp.db;

import android.util.Log;

import com.audiapp.excepciones.DBNotCreatedExcepction;
import com.audiapp.modelo.Usuario;
import com.audiapp.modelo.daos.UsuarioDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class GestorUsuarioDB extends GestorTabla implements UsuarioDAO {
    @Override
    public void crear(Usuario u) {
        Executor ejecutor = Executors.newSingleThreadExecutor();
        ejecutor.execute(() -> {
            try {
                AudiappDB.getDB().usuarioDAO().crear(u);
            } catch (DBNotCreatedExcepction dbnce) {
                Log.wtf("DB", "Error fatal: uso incorrecto de AudiappDB" + Arrays.toString(dbnce.getStackTrace()));
            }
        });
    }

    @Override
    public List<Usuario> leerTodos() {
        final Semaphore semaphore = new Semaphore(0, false);
        ArrayList<List<Usuario>> listaListaUsuarios;
        listaListaUsuarios = new ArrayList<>();
        Executor ejecutor = Executors.newSingleThreadExecutor();
        ejecutor.execute(() -> {
            try {
                listaListaUsuarios.add(AudiappDB.getDB().usuarioDAO().leerTodos());
                semaphore.release();
            } catch (DBNotCreatedExcepction dbnce) {
                Log.wtf("DB", "Error fatal: uso incorrecto de AudiappDB" + Arrays.toString(dbnce.getStackTrace()));
            }
        });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Log.e("GestorUsuarioDB", Arrays.toString(e.getStackTrace()));
            return null;
        }
        return listaListaUsuarios.get(0);
    }

    @Override
    public void actualizar(Usuario u) {
        Executor ejecutor = Executors.newSingleThreadExecutor();
        ejecutor.execute(() -> {
            try {
                AudiappDB.getDB().usuarioDAO().actualizar(u);
            } catch (DBNotCreatedExcepction dbnce) {
                Log.wtf("DB", "Error fatal: uso incorrecto de AudiappDB" + Arrays.toString(dbnce.getStackTrace()));
            }
        });
    }

    @Override
    public void borrar(Usuario u) {
        Executor ejecutor = Executors.newSingleThreadExecutor();
        ejecutor.execute(() -> {
            try {
                AudiappDB.getDB().usuarioDAO().borrar(u);
            } catch (DBNotCreatedExcepction dbnce) {
                Log.wtf("DB", "Error fatal: uso incorrecto de AudiappDB" + Arrays.toString(dbnce.getStackTrace()));
            }
        });
    }

    @Override
    public void borrarTodos() {
        Executor ejecutor = Executors.newSingleThreadExecutor();
        ejecutor.execute(() -> {
            try {
                AudiappDB.getDB().usuarioDAO().borrarTodos();
            } catch (DBNotCreatedExcepction dbnce) {
                Log.wtf("DB", "Error fatal: uso incorrecto de AudiappDB" + Arrays.toString(dbnce.getStackTrace()));
            }
        });
    }
}
