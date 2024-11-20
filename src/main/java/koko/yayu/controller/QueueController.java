package koko.yayu.controller;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import koko.yayu.util.YarnApiRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QueueController {

  @GetMapping("/scheduler")
  public String scheduler(Model model) {
    JSONObject resp = YarnApiRequest.get(
        "/ws/v1/cluster/scheduler",
        jsonObject -> jsonObject.getJSONObject("scheduler").getJSONObject("schedulerInfo")
    );
    model.addAttribute("props", resp);

    model.addAttribute("health",
      YarnApiRequest.jsonArrayToList(resp.getJSONObject("health").getJSONArray("operationsInfo")));
    model.addAttribute("details",
      YarnApiRequest.jsonArrayToList(resp.getJSONObject("health").getJSONArray("lastRunDetails")));
    model.addAttribute("partitions",
      YarnApiRequest.jsonArrayToList(resp.getJSONObject("capacities").getJSONArray("queueCapacitiesByPartition")));
    YarnApiRequest.jsonArrayToList(resp.getJSONObject("health").getJSONArray("lastRunDetails"));
    model.addAttribute("queueAcls",
      YarnApiRequest.jsonArrayToList(resp.getJSONObject("queueAcls").getJSONArray("queueAcl")));
    model.addAttribute("queueCapacityVectorInfo",
      YarnApiRequest.jsonArrayToList(resp.getJSONObject("queueCapacityVectorInfo").getJSONArray("capacityVectorEntries")));
    return "scheduler";
  }

  @GetMapping("/queues")
  public String queues(Model model) {
    JSONObject resp = YarnApiRequest.get(
      "/ws/v1/cluster/scheduler",
      jsonObject -> jsonObject.getJSONObject("scheduler").getJSONObject("schedulerInfo")
    );
    model.addAttribute("props", resp);
    return "details";
  }

}
