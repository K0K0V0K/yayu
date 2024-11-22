package koko.yayu.service;

import koko.yayu.util.YarnApiRequest;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ResourceManagerMonitor {

  private JSONObject scheduler;

  @Scheduled(fixedRate = 1000)
  public void pollScheduler() {
    scheduler = YarnApiRequest.get(
      "/ws/v1/cluster/scheduler",
      jsonObject -> jsonObject.getJSONObject("scheduler").getJSONObject("schedulerInfo")
    );
  }

  public JSONObject getScheduler() {
    return scheduler;
  }

}
