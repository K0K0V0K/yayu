package koko.yayu.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import koko.yayu.generator.ComponentGenerator;
import koko.yayu.service.apiservice.RestApiService;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.apache.commons.lang3.StringUtils;

@Controller
public class QueueController {

  private final RestApiService restApiService;

  public QueueController(RestApiService restApiService) {
    this.restApiService = restApiService;
  }

  @GetMapping("/queues")
  public String queues(Model model) {
    JSONObject resp = restApiService.getScheduler();
    List<JSONObject> topQueues = YayuUtil.mapJsonList(resp, "queues", "queue");
    Map<Integer, List<JSONObject>> queues = flatten(topQueues)
      .collect(Collectors.groupingBy(queue ->
        StringUtils.countMatches(queue.getString("queuePath"), ".")));
    queues.values().forEach(q ->
      q.sort(Comparator.comparing(o -> o.getString("queuePath"))));
    model.addAttribute("queues", queues);
    return "queues";
  }

  @GetMapping("/queue/{queuePath}")
  public String queues(Model model, @PathVariable String queuePath) {
    JSONObject resp = restApiService.getScheduler();
    List<JSONObject> topQueues = YayuUtil.mapJsonList(resp, "queues", "queue");
    JSONObject found = flatten(topQueues)
      .filter(queue -> queuePath.equals(queue.getString("queuePath")))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("queue not found"));

    model.addAttribute("am", ComponentGenerator.create()
      .setTitle("AM Limits")
      .addField("/AMResourceLimit/memory", "AM Memory Limit")
      .addField("/AMResourceLimit/vCores", "AM vCore Limit")
      .addField("/userAMResourceLimit/memory", "User AM Memory Limit")
      .addField("/userAMResourceLimit/vCores", "User AM vCore Limit")
      .addField("/usedAMResource/memory", "Used AM memory")
      .addField("/usedAMResource/vCores", "Used AM vCore")
      .generate(found)
    );

    model.addAttribute("capacity", ComponentGenerator.create()
      .setTitle("Capacity")
      .addField("/absoluteCapacity")
      .addField("/absoluteMaxCapacity")
      .addField("/absoluteUsedCapacity")
      .addField("/usedCapacity")
      .addField("/maxCapacity")
      .generate(found)
    );

    model.addAttribute("config", ComponentGenerator.create()
      .setTitle("Config")
      .addField("/autoCreateChildQueueEnabled")
      .addField("/autoCreationEligibility")
      .addField("/configuredMaxAMResourceLimit")
      .addField("/creationMethod")
      .addField("/defaultPriority")
      .addField("/hideReservationQueues")
      .addField("/intraQueuePreemptionDisabled")
      .addField("/isAbsoluteResource")
      .addField("/isAutoCreatedLeafQueue")
      .addField("/maxApplications")
      .addField("/maxApplicationsPerUser")
      .addField("/maxApplications")
      .addField("/maxParallelApps")
      .addField("/mode")
      .addField("/orderingPolicyInfo")
      .addField("/preemptionDisabled")
      .addField("/queueType")
      .addField("/userLimit")
      .addField("/userLimitFactor")
      .generate(found)
    );

    model.addAttribute("acls", ComponentGenerator.create()
      .setTitle("ACLs")
      .addField("/accessType")
      .addField("/accessControlList")
      .generate(YayuUtil.mapJsonList(found, "queueAcls", "queueAcl"))
    );

    model.addAttribute("state", ComponentGenerator.create()
      .setTitle("State")
      .addField("/allocatedContainers")
      .addField("/children")
      .addField("/numActiveApplications")
      .addField("/numApplications")
      .addField("/numContainers")
      .addField("/pendingContainers")
      .addField("/reservedContainers")
      .addField("/state")
      .generate(found)
    );

    return "queue-details";
  }

  public Stream<JSONObject> flatten(List<JSONObject> queues) {
    return queues.stream().flatMap(queue -> {
      JSONObject leafs = queue.optJSONObject("queues");
      if (leafs != null && !leafs.isEmpty()) {
        List<JSONObject> children = YayuUtil.jsonArrayToList(leafs.getJSONArray("queue"));
        queue.put("children", children.size());
        return Stream.concat(flatten(children), Stream.of(queue));
      }
      queue.put("children", 0);
      return Stream.of(queue);
    });
  }
}
