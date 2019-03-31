/**
 *
 */
package com.audiapp.modelo.daos;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author Miguel Ángel Bernardo García
 *
 */
interface InterfazDAO<T>
{
void crear(T t);
@Nullable
List<T> leerTodos();
void actualizar(T t);
void borrar(T t);
void borrarTodos();
}
