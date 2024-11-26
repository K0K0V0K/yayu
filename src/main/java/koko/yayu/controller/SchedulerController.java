package koko.yayu.controller;

import koko.yayu.generator.ComponentGenerator;
import koko.yayu.service.apiservice.RestApiService;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SchedulerController {

  private final RestApiService restApiService;

  public SchedulerController(RestApiService restApiService) {
    this.restApiService = restApiService;
  }

  @GetMapping("/scheduler")
  public String scheduler(Model model) {
    JSONObject resp = restApiService.getScheduler();
    model.addAttribute("props", resp);

    model.addAttribute("info", ComponentGenerator.create()
      .setTitle("Scheduler")
      .addField("/capacity")
      .addField("/usedCapacity")
      .addField("/maxCapacity")
      .addField("/queueName")
      .generate(resp)
    );

    model.addAttribute("healthRun", ComponentGenerator.create()
      .setTitle("Health - Last Run Details")
      .addField("/operation")
      .addField("/count")
      .addField("/resources/memory", "Memory")
      .addField("/resources/vCores", "vCores")
      .generate(YayuUtil.mapJsonList(resp, "health", "lastRunDetails"))
    );

    model.addAttribute("healthInfo", ComponentGenerator.create()
      .setTitle("Health - Operation Info")
      .addField("/operation")
      .addField("/containerId")
      .addField("/nodeId")
      .addField("/queue")
      .generate(YayuUtil.mapJsonList(resp, "health", "operationsInfo"))
    );

    model.addAttribute("partitions", ComponentGenerator.create()
      .setTitle("Partitions")
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
      .addField("/resourceName")
      .addField("/resourceValue")
      .generate(YayuUtil.mapJsonList(resp, "queueCapacityVectorInfo", "capacityVectorEntries"))
    );

    model.addAttribute("acls", ComponentGenerator.create()
      .setTitle("ACLs")
      .addField("/accessType")
      .addField("/accessControlList")
      .generate(YayuUtil.mapJsonList(resp, "queueAcls", "queueAcl"))
    );

    return "scheduler";
  }
}
