package com.audiapp.apisgu;

import com.audiapp.modelo.InfoDBAudiappi;
import com.audiapp.modelo.Usuario;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API_SGU
{
@NonNull
@POST("sgu/registrarUsuario")
Call<InfoDBAudiappi> hacerRegistro(@Body Usuario usuario);

@NonNull
@POST("sgu/logearUsuario")
Call<InfoDBAudiappi> hacerLogin(@Body Usuario usuario);

}
