package com.audiapp.modelo;


import com.google.gson.annotations.SerializedName;

public class InfoDBAudiappi {
    @SerializedName("tag")
    private final String tag;
    @SerializedName("motivo")
    private final String motivo;
    @SerializedName("descripcion")
    private final String descripcion;


    public InfoDBAudiappi(String param_tag, String param_motivo, String param_descripcion) {
        tag = param_tag;
        motivo = param_motivo;
        descripcion = param_descripcion;
    }

    public String getTag() {
        return tag;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }


}
