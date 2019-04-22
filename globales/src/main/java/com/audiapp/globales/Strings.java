package com.audiapp.globales;

import androidx.annotation.NonNull;

/**
 * Strings globales de la aplicación
 * Strings de uso general de la aplicación y que pueden ser modificados en un futuro
 *
 * @author Miguel Ángel Bernardo García
 * @version 1.0, 2019/02/22
 */
public class Strings {
    // Strings de acceso directo (público)
    public final static String errorConexionServidor = "Error al conectar con el servidor: revisa tu conexión a internet";
    public final static String usuarioYaEnBD = "Ya estás registrado, inicia sesión";
    public final static String emailYaEnBD = "Ya hay un usuario registrado con ese e-mail";
    public final static String nickYaEnBD = "Ya hay un usuario registrado con ese nombre de usuario";
    public final static String loginOk = "Sesión iniciada correctamente";
    public final static String loginFallido = "Fallo al iniciar sesión: revisa tus datos de acceso";
    /**
     * Devuelve la URL base para formar la URI de la conexión con el servidor
     *
     * @param localizacion "L" si servidor local, "E" si servidor externo
     * @return La URL
     */

// Privados
    private final static String ipDebug = "192.168.1.39";
    private final static String ipLocal = "192.168.1.40";
    private final static String ipExt = "83.40.219.84";

    public static String UrlBase(String localizacion) {
        int puertoDebug = 8080;
        int puertoLocal = 8443;
        int puertoExt = 49999;

        switch (localizacion) {
            case "L":
                return "https://" + ipLocal + ":" + puertoLocal;
            case "E":
                return "https://" + ipExt + ":" + puertoExt;
            case "D":
                return "http://" + ipDebug + ":" + puertoDebug;
            default:
                return "";
        }
    }

    @NonNull
    public static String DirIP(String localizacion) {
        switch (localizacion) {
            case "L":
                return ipLocal;
            case "E":
                return ipExt;
            case "D":
                return ipDebug;
            default:
                return "";
        }
    }
}
