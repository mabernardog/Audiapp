package com.audiapp.apisgu;

import com.audiapp.globales.Strings;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

// Solo usado en testeos
public class AudiappHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        if(hostname.equals(Strings.DirIP("L")) || hostname.equals(Strings.DirIP("E")) )
            {
            return true;
            }
        else
            {
            return false;
            }
    }
}
