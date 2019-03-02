package com.audiapp.apisgu;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

// COPIADO DE: https://www.ibm.com/support/knowledgecenter/en/SSYKE2_8.0.0/com.ibm.java.security.component.80.doc/security-component/jsse2Docs/creatingownx509tm.html
@Deprecated
public class AudiappTrustManager implements X509TrustManager
{
X509TrustManager trustManager;

public AudiappTrustManager(TrustManager[] param_tms) throws Exception
    {
    for (int i = 0; i < param_tms.length; i++)
        {
        if (param_tms[i] instanceof X509TrustManager)
            {
            trustManager = (X509TrustManager) param_tms[i];
            return;
            }
        }

    throw new Exception("Couldn't initialize");
    }

@Override
public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
{
    try
        {
        trustManager.checkClientTrusted(chain, authType);
        }
    catch (CertificateException excep)
        {
        return;
        }
}

@Override
public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
{
    try
        {
        trustManager.checkServerTrusted(chain, authType);
        }
    catch (CertificateException excep)
        {
        return;
        }
}

@Override
public X509Certificate[] getAcceptedIssuers()
{
    return new X509Certificate[0];
}
}
