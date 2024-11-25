package koko.yayu.service.apiservice;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import koko.yayu.util.WebClientFactory;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

public class AbstractApiService {

  private URI active;

  protected final Map<URI, WebClient> clients = new HashMap<>();

  public AbstractApiService(List<String> urls, String basePath,  int maxInMemorySize) {
    for (String rawUrl : urls) {
      clients.put(URI.create(rawUrl), WebClientFactory.createWebClient(rawUrl + basePath, maxInMemorySize));
    }
  }

  public void setActive(URI active) {
    this.active = active;
  }

  public WebClient getActiveClient() {
    return clients.get(active);
  }

  public <T> T get(String path, Function<JSONObject, T> map) {
    return get(active, path, map);
  }

  public <T> T get(URI uri, String path, Function<JSONObject, T> map) {
    return clients.get(uri)
      .get()
      .uri(path)
      .accept(MediaType.APPLICATION_XML)
      .cookie("hadoop.auth", YayuUtil.getAuthToken())
      .retrieve()
      .bodyToMono(String.class)
      .map(XML::toJSONObject)
      .map(map)
      .block();
  }
}
