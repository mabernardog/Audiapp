package com.audiapp.modelo.daos;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.audiapp.modelo.progresiones.Acorde;

import java.util.List;

@Dao
// Todo: implementar recuperación de datos
public interface AcordesDAO extends InterfazDAO<Acorde> {
    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void crear(Acorde a);

    @Nullable
    @Override
    @Query("SELECT * FROM ACORDES")
    List<Acorde> leerTodos();

    @Override
    @Update
    void actualizar(Acorde a);

    @Override
    @Delete
    void borrar(Acorde a);

    @Override
    @Query("DELETE FROM ACORDES")
    void borrarTodos();
}
