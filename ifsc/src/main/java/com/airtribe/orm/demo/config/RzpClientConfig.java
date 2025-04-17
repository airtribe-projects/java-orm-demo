package com.airtribe.orm.demo.config;

import com.airtribe.orm.demo.client.RazorpayClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

@Configuration
public class RzpClientConfig {

  @Value("${razorpay.base_url}")
  private String baseUrl;

  protected ObjectMapper initObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
    return objectMapper;
  }

  protected JacksonEncoder getEncoder() {
    return new JacksonEncoder(initObjectMapper());
  }

  protected JacksonDecoder getDecoder() {
    return new JacksonDecoder(initObjectMapper());
  }

  @Bean
  RazorpayClient getRazorpayPgClient() {
    return Feign.builder()
        .client(new feign.okhttp.OkHttpClient(getUnsafeOkHttpClient()))
        .logger(new Logger.ErrorLogger())
        .logLevel(Logger.Level.NONE)
        .encoder(getEncoder())
        .decoder(getDecoder())
        .retryer(Retryer.NEVER_RETRY)
        .options(new Request.Options(4, TimeUnit.SECONDS, 4, TimeUnit.SECONDS, false))
        .target(new Target.HardCodedTarget<>(RazorpayClient.class, baseUrl));
  }

  public okhttp3.OkHttpClient getUnsafeOkHttpClient() {
    try {
      // Create a trust manager that does not validate certificate chains
      final TrustManager[] trustAllCerts = new TrustManager[]{
          new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
              return new java.security.cert.X509Certificate[]{};
            }
          }
      };

      // Install the all-trusting trust manager
      final SSLContext sslContext = SSLContext.getInstance("SSL");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
      // Create an ssl socket factory with our all-trusting manager
      final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

      okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder();
      builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
      builder.hostnameVerifier((hostname, session) -> true);
      builder.connectTimeout(4, TimeUnit.SECONDS);
      builder.readTimeout(4, TimeUnit.SECONDS);
      return builder.build();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
