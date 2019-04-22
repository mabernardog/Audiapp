/**
 *
 */
package com.audiapp.modelo.daos;

import androidx.annotation.Nullable;

import java.util.List;

/**
 * @author Miguel Ángel Bernardo García
 *
 */
interface InterfazDAO<T> {
    void crear(T t);

    @Nullable
    List<T> leerTodos();

    void actualizar(T t);

    void borrar(T t);

    void borrarTodos();
}
