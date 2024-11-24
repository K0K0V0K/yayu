package koko.yayu.service;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import koko.yayu.config.YayuConfig;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ApiService {

  private JSONObject scheduler;
  private List<JSONObject> nodes;
  private List<JSONObject> apps;
  private URI active;

  private final Map<URI, WebClient> clients = new HashMap<>();
  private final Map<URI, WebClient> rootClient = new HashMap<>();

  public ApiService(YayuConfig config) {
    for (String rawUrl : config.getMrUrl()) {
      URI uri = URI.create(rawUrl);
      clients.put(uri, WebClient.builder()
        .baseUrl(uri + "/ws/v1/")
        .exchangeStrategies(
          ExchangeStrategies.builder()
            .codecs(configurer ->
              configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build())
        .build());
      rootClient.put(uri, WebClient.builder()
        .baseUrl(uri.toString())
        .exchangeStrategies(
          ExchangeStrategies.builder()
            .codecs(configurer ->
              configurer.defaultCodecs().maxInMemorySize(512 * 1024 * 1024)).build())
        .build());
    }
  }

  public void setActive(URI active) {
    this.active = active;
  }

  public void refresh() {
    scheduler = get("cluster/scheduler",
        YayuUtil.jsonObjectMapper("scheduler", "schedulerInfo"));
    nodes = get("cluster/nodes",
      YayuUtil.jsonListMapper("nodes", "node"));
    apps = get("cluster/apps",
      YayuUtil.jsonListMapper("apps", "app"));
  }

  public JSONObject getScheduler() {
    return scheduler;
  }

  public List<JSONObject> getNodes() {
    return nodes;
  }

  public List<JSONObject> getApps() {
    return apps;
  }

  public JSONObject getClusterInfo() {
    return get("cluster", YayuUtil.jsonObjectMapper("clusterInfo"));
  }

  public JSONObject getApp(String appId) {
    return get("cluster/apps/" + appId, YayuUtil.jsonObjectMapper("app"));
  }

  public List<JSONObject> getAttempts(String appId) {
    return get("/cluster/apps/" + appId + "/appattempts",
      YayuUtil.jsonListMapper("appAttempts", "appAttempt"));
  }

  public JSONObject getNode(String nodeId) {
    return get("cluster/nodes/" + nodeId, YayuUtil.jsonObjectMapper("node"));
  }

  public String getLogs() {
    String raw = rootClient.get(active).get().uri("/logs/")
      .retrieve().bodyToMono(String.class).block();
    return raw.substring(raw.indexOf("<tbody>"), raw.indexOf("</tbody"));
  }

  public Flux<String> getLog(String fileName) {
    return rootClient.get(active).get()
      .uri("/logs/" + fileName)
      .retrieve()
      .bodyToFlux(String.class)
      .map(s -> s.concat(System.lineSeparator()));
  }

  public List<JSONObject> getConfig() {
    return get(rootClient.get(active), "/conf",
      YayuUtil.jsonListMapper("configuration", "property"));
  }

  private <T> T get(String path, Function<JSONObject, T> map) {
    return get(clients.get(active), path, map);
  }

  private <T> T get(WebClient client, String path, Function<JSONObject, T> map) {
    return client
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
