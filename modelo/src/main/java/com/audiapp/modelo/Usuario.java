package com.audiapp.modelo;

import com.google.gson.annotations.SerializedName;

public class Usuario
{
@SerializedName("email-registro") private String email;
@SerializedName("nick-registro" ) private String nick;
@SerializedName("passw-registro") private String password;

public Usuario(String param_email, String param_nick, String param_password)
    {
    email    = param_email;
    nick     = param_nick;
    password = param_password;
    }
}
