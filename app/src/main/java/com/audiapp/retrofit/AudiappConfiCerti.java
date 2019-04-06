package com.audiapp.retrofit;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import androidx.annotation.NonNull;

// COPIADO DE: https://developer.android.com/training/articles/security-ssl?hl=ES#UnknownCa
class AudiappConfiCerti {
    private SSLSocketFactory sslFactoria;
    private X509TrustManager trustManager;

    public AudiappConfiCerti(@NonNull InputStream caInput) throws Exception {
        // Load CAs from an InputStream
        // (could be from a resource or ByteArrayInputStream or ...)
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        // From https://www.washington.edu/itconnect/security/ca/load-der.crt
        //InputStream caInput = new BufferedInputStream(new FileInputStream(certificadoDir));
        Certificate ca = cf.generateCertificate(caInput);
        System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
        caInput.close();

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Create an SSLContext that uses our TrustManager
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);

        // Inicializar los atributos de la clase
        sslFactoria = context.getSocketFactory();
        TrustManager[] tms = tmf.getTrustManagers();
        trustManager = (X509TrustManager) tms[0];
    }

    public SSLSocketFactory getSslFactoria() {
        return sslFactoria;
    }

    public X509TrustManager getTrustManager() {
        return trustManager;
    }
}
