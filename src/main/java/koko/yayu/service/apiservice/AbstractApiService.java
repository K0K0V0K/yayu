package koko.yayu.service.apiservice;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

public class AbstractApiService {

  private URI active;

  private final Map<URI, WebClient> clients = new HashMap<>();

  public AbstractApiService(List<String> urls, String basePath,  int maxInMemorySize) {
    for (String rawUrl : urls) {
      URI uri = URI.create(rawUrl);
      clients.put(uri, WebClient.builder()
        .baseUrl(uri + basePath)
        .exchangeStrategies(
          ExchangeStrategies.builder()
            .codecs(configurer ->
              configurer.defaultCodecs().maxInMemorySize(maxInMemorySize * 1024 * 1024)).build())
        .build());
    }
  }

  public void setActive(URI active) {
    this.active = active;
  }

  public WebClient getActiveClient() {
    return clients.get(active);
  }

  public <T> T get(String path, Function<JSONObject, T> map) {
    return clients.get(active)
      .get()
      .uri(path)
      .accept(MediaType.APPLICATION_XML)
      .retrieve()
      .bodyToMono(String.class)
      .map(XML::toJSONObject)
      .map(map)
      .block();
  }
}
