package com.audiapp.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Usuario {
    @SerializedName("email")
    private String email;
    @PrimaryKey
    @NonNull
    @SerializedName("nick")
    private String nick;
    @ColumnInfo(name = "password")
    @SerializedName("password")
    private String password;

    public Usuario(String email, @NonNull String nick, String password) {
        this.email = email;
        this.nick = nick;
        this.password = password;
    }

    public Usuario(String nick, String password) {
        this.nick = nick;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    public String getNick() {
        return nick;
    }

    @NonNull
    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
