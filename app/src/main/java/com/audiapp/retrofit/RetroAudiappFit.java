package com.audiapp.retrofit;


import android.util.Log;

import com.audiapp.globales.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetroAudiappFit
{
private AudiappConfiCerti confiCerti;
private final  String url = Strings.UrlBase("L") + "/api/";
private static Retrofit cliente;

public RetroAudiappFit(@NonNull InputStream certificado)
    {
    try
        {
        confiCerti = new AudiappConfiCerti(certificado);
        }
    catch(Exception e)
        {
        Log.e("HTTPS", "Error al autorizar confianza en el certificado");
        }

    assert confiCerti != null;
    // Crear el cliente HTTP
    OkHttpClient client = new OkHttpClient.Builder().hostnameVerifier(new AudiappHostnameVerifier()).sslSocketFactory(confiCerti.getSslFactoria(), confiCerti.getTrustManager()).build();
    // Añadir interceptor al OkHttpClient para añadir header
    Gson gson = new GsonBuilder().setLenient().create();
    // Crear instancia de Retrofit
    cliente = new Retrofit.Builder().baseUrl(url).
                                addConverterFactory(ScalarsConverterFactory.create()).
                                addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();
    }

// Modifica la instancia de Retrofit con un OkHttpClient que agregue el JWT a la petición al servidor
public void agregarJWT(String token)
    {
    assert confiCerti != null;
    // Crear el cliente HTTP
    OkHttpClient client = new OkHttpClient.Builder().hostnameVerifier(new AudiappHostnameVerifier()).sslSocketFactory(confiCerti.getSslFactoria(), confiCerti.getTrustManager()).
                                                    addInterceptor(new JWTInterceptor(token)).build();
    Gson gson = new GsonBuilder().setLenient().create();
    // Crear instancia de Retrofit
    cliente = new Retrofit.Builder().baseUrl(url).
                                addConverterFactory(ScalarsConverterFactory.create()).
                                addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();
    }

public Retrofit getCliente()
    {
    return cliente;
    }

}

class JWTInterceptor implements Interceptor
{
private final String token;

public JWTInterceptor(String param_token)
    {
    token = param_token;
    }

@NonNull
@Override
public Response intercept(@NonNull Chain chain) throws IOException
    {
    Request peticion = chain.request().newBuilder().addHeader("Authorization", "Bearer" + token).build();
    return chain.proceed(peticion);
    }
}
