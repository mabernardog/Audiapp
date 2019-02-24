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
public static String urlBase(String localizacion)
    {
    String ipLocal     = "192.168.1.46";
    String ipExt       = "192.168.1.46";
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
}
