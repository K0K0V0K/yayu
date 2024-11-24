package koko.yayu.controller;

import koko.yayu.component.ComponentGenerator;
import koko.yayu.service.ApiService;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SchedulerController {

  private final ApiService apiService;

  public SchedulerController(ApiService apiService) {
    this.apiService = apiService;
  }

  @GetMapping("/scheduler")
  public String scheduler(Model model) {
    JSONObject resp = apiService.getScheduler();
    model.addAttribute("props", resp);

    model.addAttribute("info", ComponentGenerator.create()
      .setTitle("Scheduler")
      .setColor("is-link")
      .addField("/capacity")
      .addField("/usedCapacity")
      .addField("/maxCapacity")
      .addField("/queueName")
      .generate(resp)
    );

    model.addAttribute("healthRun", ComponentGenerator.create()
      .setTitle("Health - Last Run Details")
      .setColor("is-danger")
      .addField("/operation")
      .addField("/count")
      .addField("/resources/memory", "Memory")
      .addField("/resources/vCores", "vCores")
      .generate(YayuUtil.mapJsonList(resp, "health", "lastRunDetails"))
    );

    model.addAttribute("healthInfo", ComponentGenerator.create()
      .setTitle("Health - Operation Info")
      .setColor("is-danger")
      .addField("/operation")
      .addField("/containerId")
      .addField("/nodeId")
      .addField("/queue")
      .generate(YayuUtil.mapJsonList(resp, "health", "operationsInfo"))
    );

    model.addAttribute("partitions", ComponentGenerator.create()
      .setTitle("Partitions")
      .setColor("is-info")
      .addField("/partitionName")
      .addField("/capacity")
      .addField("/usedCapacity")
      .addField("/maxCapacity")
      .addField("/absoluteCapacity")
      .addField("/absoluteUsedCapacity")
      .addField("/absoluteMaxCapacity")
      .addField("/maxAMLimitPercentage")
      .addField("/weight")
      .generate(YayuUtil.mapJsonList(resp, "capacities", "queueCapacitiesByPartition"))
    );

    model.addAttribute("capacityVector", ComponentGenerator.create()
      .setTitle("Queue Capacity Vector Info")
      .setColor("is-success")
      .addField("/resourceName")
      .addField("/resourceValue")
      .generate(YayuUtil.mapJsonList(resp, "queueCapacityVectorInfo", "capacityVectorEntries"))
    );

    model.addAttribute("acls", ComponentGenerator.create()
      .setTitle("ACLs")
      .setColor("is-success")
      .addField("/accessType")
      .addField("/accessControlList")
      .generate(YayuUtil.mapJsonList(resp, "queueAcls", "queueAcl"))
    );

    return "scheduler";
  }
}
