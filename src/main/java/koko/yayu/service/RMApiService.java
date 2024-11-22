package koko.yayu.service;

import java.util.List;
import java.util.function.Function;

import koko.yayu.config.YayuConfig;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class RMApiService {

  private JSONObject scheduler;
  private JSONObject clusterInfo;
  private List<JSONObject> nodes;
  private List<JSONObject> apps;

  private final WebClient client;
  private final WebClient rootClient;

  public RMApiService(YayuConfig config) {
    this.client = WebClient.builder()
      .baseUrl(config.getMrUrl() + "/ws/v1/")
      .exchangeStrategies(
        ExchangeStrategies.builder()
          .codecs(configurer ->
            configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build())
      .build();
    this.rootClient =  WebClient.builder()
      .baseUrl(config.getMrUrl())
      .exchangeStrategies(
        ExchangeStrategies.builder()
          .codecs(configurer ->
            configurer.defaultCodecs().maxInMemorySize(512 * 1024 * 1024)).build())
      .build();
  }

  @Scheduled(fixedRate = 1000)
  private void pollScheduler() {
    scheduler = get("cluster/scheduler",
      YayuUtil.jsonObjectMapper("scheduler", "schedulerInfo"));
    nodes = get("cluster/nodes",
      YayuUtil.jsonListMapper("nodes", "node"));
    apps = get("cluster/apps",
      YayuUtil.jsonListMapper("apps", "app"));
    apps = get("cluster/apps",
      YayuUtil.jsonListMapper("apps", "app"));
    clusterInfo = get("cluster",
      YayuUtil.jsonObjectMapper("clusterInfo"));
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
    return clusterInfo;
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
    String raw = rootClient.get().uri("/logs/")
      .retrieve().bodyToMono(String.class).block();
    return raw.substring(raw.indexOf("<tbody>"), raw.indexOf("</tbody"));
  }

  public Flux<String> getLog(String fileName) {
    return rootClient.get()
      .uri("/logs/" + fileName)
      .retrieve()
      .bodyToFlux(String.class)
      .map(s -> s.concat(System.lineSeparator()));
  }

  public List<JSONObject> getConfig() {
    return get(rootClient, "/conf",
      YayuUtil.jsonListMapper("configuration", "property"));
  }

  private <T> T get(String path, Function<JSONObject, T> map) {
    return get(client, path, map);
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
