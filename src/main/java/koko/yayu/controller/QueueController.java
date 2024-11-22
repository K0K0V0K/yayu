package koko.yayu.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import koko.yayu.service.ResourceManagerMonitor;
import koko.yayu.util.YarnApiRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import org.apache.commons.lang3.StringUtils;

@Controller
public class QueueController {

  private final ResourceManagerMonitor resourceManagerMonitor;

  public QueueController(ResourceManagerMonitor resourceManagerMonitor) {
    this.resourceManagerMonitor = resourceManagerMonitor;
  }

  @GetMapping("/scheduler")
  public String scheduler(Model model) {
    JSONObject resp = resourceManagerMonitor.getScheduler();
    model.addAttribute("props", resp);

    model.addAttribute("health",
      YarnApiRequest.getJsonArray(resp,"health", "operationsInfo"));

    model.addAttribute("details",
      YarnApiRequest.getJsonArray(resp,"health", "lastRunDetails"));

    model.addAttribute("partitions",
      YarnApiRequest.getJsonArray(resp, "capacities", "queueCapacitiesByPartition"));

    model.addAttribute("queueAcls",
      YarnApiRequest.getJsonArray(resp, "queueAcls", "queueAcl"));

    model.addAttribute("queueCapacityVectorInfo",
      YarnApiRequest.getJsonArray(resp, "queueCapacityVectorInfo", "capacityVectorEntries"));

    return "scheduler";
  }

  @GetMapping("/queues")
  public String queues(Model model) {
    JSONObject resp = resourceManagerMonitor.getScheduler();

    List<JSONObject> topQueues = YarnApiRequest.getJsonArray(resp, "queues", "queue");
    Map<Integer, List<JSONObject>> queues = flatten(topQueues)
      .collect(Collectors.groupingBy(
        queue -> StringUtils.countMatches(queue.getString("queuePath"), ".")));
    queues.replaceAll((level, lists) -> lists.stream()
      .sorted(Comparator.comparing(o -> o.getString("queuePath")))
      .toList());
    model.addAttribute("queues", queues);
    return "queues";
  }

  public Stream<JSONObject> flatten(List<JSONObject> queues) {
    return queues.stream().flatMap(queue -> {
      JSONObject leafs = queue.optJSONObject("queues");
      if (leafs != null && !leafs.isEmpty()) {
        queue.put("isLeaf", "NO");
        return Stream.concat(
          flatten(YarnApiRequest.jsonArrayToList(leafs.getJSONArray("queue"))),
          Stream.of(queue)
        );
      }
      queue.put("isLeaf", "YES");
      return Stream.of(queue);
    });
  }
}
