package koko.yayu.service.apiservice;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import koko.yayu.config.YayuConfig;
import koko.yayu.util.YayuCache;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RestApiService extends AbstractApiService {

  LoadingCache<URI, JSONObject> clusterInfoCache = CacheBuilder.newBuilder()
    .expireAfterWrite(10, TimeUnit.SECONDS)
    .build(CacheLoader.from(uri ->
      get(uri, "cluster", YayuUtil.jsonObjectMapper("clusterInfo"))));

  YayuCache<JSONObject> clusterUserCache = new YayuCache<>(Duration.ofMinutes(15), 1,
    () -> get("cluster/userinfo", YayuUtil.jsonObjectMapper("clusterUserInfo")));

  YayuCache<List<JSONObject>> appsCache = new YayuCache<>(Duration.ofSeconds(10), 6,
    () -> get("cluster/apps", YayuUtil.jsonListMapper("apps", "app")));

  YayuCache<JSONObject> schedulerCache = new YayuCache<>(Duration.ofSeconds(10), 6,
    () -> get("cluster/scheduler", YayuUtil.jsonObjectMapper("scheduler", "schedulerInfo")));

  public RestApiService(YayuConfig config) {
    super(config.getMrUrl(), "/ws/v1/", 16);
  }

  public JSONObject getClusterInfo(URI uri) {
    return clusterInfoCache.getUnchecked(uri);
  }

  public JSONObject getClusterUser() {
    return clusterUserCache.get();
  }

  public JSONObject getScheduler() {
    return schedulerCache.get();
  }

  public List<JSONObject> getNodes() {
    return get("cluster/nodes",
      YayuUtil.jsonListMapper("nodes", "node"));
  }

  public List<JSONObject> getApps() {
    return appsCache.get();
  }

  public JSONObject getApp(String appId) {
    return get("cluster/apps/" + appId,
      YayuUtil.jsonObjectMapper("app"));
  }

  public List<JSONObject> getAttempts(String appId) {
    return get("/cluster/apps/" + appId + "/appattempts",
      YayuUtil.jsonListMapper("appAttempts", "appAttempt"));
  }

  public JSONObject getNode(String nodeId) {
    return get("cluster/nodes/" + nodeId, YayuUtil.jsonObjectMapper("node"));
  }
}
