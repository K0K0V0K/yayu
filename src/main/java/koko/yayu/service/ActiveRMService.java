package koko.yayu.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import koko.yayu.config.YayuConfig;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ActiveRMService {

  private final List<URI> rmUrls;
  private final Map<URI, JSONObject> statuses = new HashMap<>();


  public ActiveRMService(YayuConfig yayuConfig) {
    this.rmUrls = yayuConfig.getMrUrl().stream().map(url -> {
      try {
        return new URI(url);
      } catch (URISyntaxException e) {
        throw new Error(e);
      }
    }).toList();
  }

  public void refresh() {
    for (URI rmUrl : rmUrls) {
      try {
        statuses.put(rmUrl, WebClient
          .builder()
          .baseUrl(rmUrl.toString())
          .build()
          .get()
          .uri("/ws/v1/cluster")
          .accept(MediaType.APPLICATION_XML)
          .retrieve()
          .bodyToMono(String.class)
          .map(XML::toJSONObject)
          .map(jsonObject -> jsonObject.getJSONObject("clusterInfo"))
          .block());
      } catch (Exception e) {
        //throw new RuntimeException(e);
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
