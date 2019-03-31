package com.audiapp.excepciones;

public class DBNotCreatedExcepction extends Exception
{

public DBNotCreatedExcepction()
    {
    super("Acceso a BD sin la BD creada");
    }
}
