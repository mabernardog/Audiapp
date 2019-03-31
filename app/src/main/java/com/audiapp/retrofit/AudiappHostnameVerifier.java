package com.audiapp.retrofit;

import com.audiapp.globales.Strings;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import androidx.annotation.NonNull;

// Solo usado en testeos
class AudiappHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(@NonNull String hostname, SSLSession session) {
        return hostname.equals(Strings.DirIP("L")) || hostname.equals(Strings.DirIP("E"));
    }
}
