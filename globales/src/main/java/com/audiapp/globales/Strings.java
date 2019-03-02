package com.audiapp.globales;

/**
 * Strings globales de la aplicación
 * Strings de uso general de la aplicación y que pueden ser modificados en un futuro
 * @author Miguel Ángel Bernardo García
 * @version 1.0, 2019/02/22
 */
public class Strings
{
/**
 * Devuelve la URL base para formar la URI de la conexión con el servidor
 * @param  localizacion "L" si servidor local, "E" si servidor externo
 * @return La URL
 */
static String ipLocal     = "192.168.1.40";
static String ipExt       = "192.168.1.46";

public static String urlBase(String localizacion)
    {
    int    puertoLocal = 8443;
    int    puertoExt   = 43999;

    if(localizacion.equals("L"))
        {
        return "https://" + ipLocal + ":" + puertoLocal;
        }
    else if(localizacion.equals("E"))
        {
        return "https://" + ipExt + ":" + puertoExt;
        }

    return "";
    }
public static String DirIP(String localizacion)
    {
    if(localizacion.equals("L"))
        {
        return ipLocal;
        }
    else if(localizacion.equals("E"))
        {
        return ipExt;
        }

    return "";
    }
}
