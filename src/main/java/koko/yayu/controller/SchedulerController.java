package koko.yayu.controller;

import koko.yayu.service.RMApiService;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SchedulerController {

  private final RMApiService RMApiService;

  public SchedulerController(RMApiService RMApiService) {
    this.RMApiService = RMApiService;
  }

  @GetMapping("/scheduler")
  public String scheduler(Model model) {
    JSONObject resp = RMApiService.getScheduler();
    model.addAttribute("props", resp);
    model.addAttribute("health",
      YayuUtil.mapJsonList(resp, "health", "operationsInfo"));
    model.addAttribute("details",
      YayuUtil.mapJsonList(resp, "health", "lastRunDetails"));
    model.addAttribute("partitions",
      YayuUtil.mapJsonList(resp, "capacities", "queueCapacitiesByPartition"));
    model.addAttribute("queueAcls",
      YayuUtil.mapJsonList(resp, "queueAcls", "queueAcl"));
    model.addAttribute("queueCapacityVectorInfo",
      YayuUtil.mapJsonList(resp, "queueCapacityVectorInfo", "capacityVectorEntries"));
    return "scheduler";
  }
}
