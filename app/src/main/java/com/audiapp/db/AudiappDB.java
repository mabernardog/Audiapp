package com.audiapp.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.audiapp.excepciones.DBNotCreatedExcepction;
import com.audiapp.modelo.daos.AcordesDAO;
import com.audiapp.modelo.daos.ProgresionesDAO;
import com.audiapp.modelo.daos.UsuarioDAO;
import com.audiapp.modelo.progresiones.Acorde;
import com.audiapp.modelo.progresiones.ProgresionArmonica;
import com.audiapp.modelo.usuarios.Usuario;

@Database(entities = {Usuario.class, ProgresionArmonica.class, Acorde.class}, version = 1)
public abstract class AudiappDB extends RoomDatabase {
    private static AudiappDB db;

    // Constructor para crear la DB
    public static synchronized void getDB(@NonNull Context contexto) {
        try {
            if (db == null) {
                db = Room.databaseBuilder(contexto.getApplicationContext(), AudiappDB.class, "AudiappDB").build();
            }
        } catch (Exception ex) {
            Log.wtf("DB", "Muerte fatal");
        }
    }

    // Constructor para usar la db (no la crea nadie por aquí)
    public static synchronized AudiappDB getDB() throws DBNotCreatedExcepction {
        if (db == null) {
            throw new DBNotCreatedExcepction();
        }
        return db;
    }

    public abstract UsuarioDAO usuarioDAO();

    public abstract ProgresionesDAO progresionesDAO();

    public abstract AcordesDAO acordesDAO();
}
