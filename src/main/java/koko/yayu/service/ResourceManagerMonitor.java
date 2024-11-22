package koko.yayu.service;

import java.util.List;

import koko.yayu.util.YarnApiRequest;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ResourceManagerMonitor {

  private JSONObject scheduler;
  private List<JSONObject> nodes;
  private List<JSONObject> apps;

  @Scheduled(fixedRate = 1000)
  public void pollScheduler() {
    scheduler = YarnApiRequest.get("/ws/v1/cluster/scheduler",
      jsonObject -> jsonObject.getJSONObject("scheduler").getJSONObject("schedulerInfo"));
    nodes = YarnApiRequest.get("/ws/v1/cluster/nodes", jsonObject -> YarnApiRequest.jsonArrayToList(
      jsonObject.getJSONObject("nodes").getJSONArray("node")));
    apps = YarnApiRequest.get("/ws/v1/cluster/apps", jsonObject -> YarnApiRequest.jsonArrayToList(
      jsonObject.getJSONObject("apps").getJSONArray("app")));
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
}
