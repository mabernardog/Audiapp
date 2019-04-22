package com.audiapp.modelo.daos;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.audiapp.modelo.Usuario;

import java.util.List;

@Dao
public interface UsuarioDAO extends InterfazDAO<Usuario> {
    @Override
    @Insert
    void crear(Usuario u);

    @Nullable
    @Override
    @Query("SELECT * FROM Usuario")
    List<Usuario> leerTodos();

    @Override
    @Update
    void actualizar(Usuario u);

    @Override
    @Delete
    void borrar(Usuario u);

    @Override
    @Query("DELETE FROM Usuario")
    void borrarTodos();
}
