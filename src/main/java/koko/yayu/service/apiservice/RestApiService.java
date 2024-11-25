package koko.yayu.service.apiservice;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import koko.yayu.config.YayuConfig;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RestApiService extends AbstractApiService {

  LoadingCache<URI, JSONObject> clusterInfoCache = CacheBuilder.newBuilder()
    .expireAfterWrite(10, TimeUnit.SECONDS)
    .build(CacheLoader.from(uri ->
      get(uri, "cluster", YayuUtil.jsonObjectMapper("clusterInfo"))));

  public RestApiService(YayuConfig config) {
    super(config.getMrUrl(), "/ws/v1/", 16);
  }

  public JSONObject getClusterInfo(URI uri) {
    return clusterInfoCache.getUnchecked(uri);
  }

  public JSONObject getClusterUser() {
    return get("cluster/userinfo",
      YayuUtil.jsonObjectMapper("clusterUserInfo"));
  }

  public JSONObject getScheduler() {
    return get("cluster/scheduler",
      YayuUtil.jsonObjectMapper("scheduler", "schedulerInfo"));
  }

  public List<JSONObject> getNodes() {
    return get("cluster/nodes",
      YayuUtil.jsonListMapper("nodes", "node"));
  }

  public List<JSONObject> getApps() {
    return get("cluster/apps",
      YayuUtil.jsonListMapper("apps", "app"));
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
