package koko.yayu.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import koko.yayu.config.YayuConfig;
import koko.yayu.service.apiservice.RestApiService;
import koko.yayu.util.WebClientFactory;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ActiveRMService {

  private final List<URI> rmUrls;
  private final Map<URI, JSONObject> statuses = new HashMap<>();
  private long lastRefresh = 0;
  private final RestApiService restApiService;

  public ActiveRMService(YayuConfig yayuConfig, RestApiService restApiService) {
    this.restApiService = restApiService;
    this.rmUrls = yayuConfig.getMrUrl().stream().map(url -> {
      try {
        return new URI(url);
      } catch (URISyntaxException e) {
        throw new Error(e);
      }
    }).toList();
  }

  public void refresh() {
    if (System.currentTimeMillis() - lastRefresh < 10_000) {
      return;
    }
    lastRefresh = System.currentTimeMillis();
    for (URI rmUrl : rmUrls) {
      try {
        statuses.put(rmUrl, restApiService.getClusterInfo(rmUrl));
      } catch (Throwable e) {
        statuses.put(rmUrl, null);
      }
    }
  }

  public URI getActive() {
    return statuses.entrySet().stream()
      .filter(entry -> entry.getValue() != null)
      .filter(entry -> "ACTIVE".equals(entry.getValue().getString("haState")))
      .findFirst().orElseThrow(RuntimeException::new).getKey();
  }

  public JSONObject getActiveStatus() {
    return statuses.get(getActive());
  }

  public Map<URI, String> getStatuses() {
    return statuses.entrySet().stream().collect(Collectors.toMap(
      Map.Entry::getKey,
      entry -> entry.getValue() == null
        ? "OFFLINE"
        : entry.getValue().getString("haState")
    ));
  }
}
