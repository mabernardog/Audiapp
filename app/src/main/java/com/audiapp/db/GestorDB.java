package com.audiapp.db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GestorDB
{
public GestorDB()
    {
    }

@Nullable
public GestorTabla acceder(@NonNull String tabla)
    {
    switch (tabla)
        {
        case "Usuario":
            return new GestorUsuarioDB();
        case "CrearDB":
            return new CreadorDB();
        default:
            return null;
        }
    }

}
