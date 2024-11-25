package koko.yayu.service.apiservice;

import java.util.List;

import koko.yayu.config.YayuConfig;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RestApiService extends AbstractApiService {

  private JSONObject scheduler;
  private List<JSONObject> nodes;
  private List<JSONObject> apps;

  public RestApiService(YayuConfig config) {
    super(config.getMrUrl(), "/ws/v1/", 16);
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
}
