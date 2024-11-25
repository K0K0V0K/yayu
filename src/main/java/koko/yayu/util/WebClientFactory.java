package koko.yayu.util;

import java.util.Arrays;
import java.util.stream.Collectors;
import javax.net.ssl.SSLException;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

public class WebClientFactory {

  public static WebClient createWebClient(String baseUrl, int maxInMemorySize) {
    return WebClient.builder()
      .baseUrl(baseUrl)
      .exchangeStrategies(
        ExchangeStrategies.builder()
          .codecs(configurer ->
            configurer.defaultCodecs().maxInMemorySize(maxInMemorySize * 1024 * 1024)).build())
      .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
        .secure(sslContextSpec -> {
          try {
            sslContextSpec.sslContext(SslContextBuilder.forClient()
              .trustManager(InsecureTrustManagerFactory.INSTANCE)
              .build());
          } catch (SSLException e) {
            throw new RuntimeException(e);
          }
        })))
      .build();
  }
}
