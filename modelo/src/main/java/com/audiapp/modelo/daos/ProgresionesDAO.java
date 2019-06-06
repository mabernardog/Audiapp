package com.audiapp.modelo.daos;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.audiapp.modelo.progresiones.ProgresionArmonica;

import java.util.List;

@Dao
// Todo: implementar recuperación de datos
public interface ProgresionesDAO extends InterfazDAO<ProgresionArmonica> {
    @Override
    @Insert
    void crear(ProgresionArmonica p);

    @Insert
    long crearPK(ProgresionArmonica p);

    @Nullable
    @Override
    @Query("SELECT * FROM PROGRESIONES")
    List<ProgresionArmonica> leerTodos();

    @Query("SELECT COUNT(*) FROM PROGRESIONES WHERE nombre = :arg0")
    long cuentaProgresionesDeNombre(String arg0);

    @Override
    @Update
    void actualizar(ProgresionArmonica p);

    @Override
    @Delete
    void borrar(ProgresionArmonica p);

    @Override
    @Query("DELETE FROM PROGRESIONES")
    void borrarTodos();
}
