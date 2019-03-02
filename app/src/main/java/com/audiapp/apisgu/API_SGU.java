package com.audiapp.apisgu;

import com.audiapp.modelo.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API_SGU
{
@POST("registrarUsuario/")
Call<String> hacerRegistro(@Body Usuario usuario, @Query("recibo") String recibo);
}
