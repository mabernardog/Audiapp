package com.audiapp.apisgu;

import com.audiapp.modelo.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API_SGU
{
@POST("registrarUsuario")
Call<String> hacerRegistro(@Body Usuario usuario/*, @Query("usuario") Usuario usuario*/);

}
