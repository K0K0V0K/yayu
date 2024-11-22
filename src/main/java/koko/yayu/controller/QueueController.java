package koko.yayu.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import koko.yayu.service.RMApiService;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.apache.commons.lang3.StringUtils;

@Controller
public class QueueController {

  private final RMApiService RMApiService;

  public QueueController(RMApiService RMApiService) {
    this.RMApiService = RMApiService;
  }

  @GetMapping("/queues")
  public String queues(Model model) {
    JSONObject resp = RMApiService.getScheduler();
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
    JSONObject resp = RMApiService.getScheduler();
    List<JSONObject> topQueues = YayuUtil.mapJsonList(resp, "queues", "queue");
    JSONObject found = flatten(topQueues)
      .filter(queue -> queuePath.equals(queue.getString("queuePath")))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("queue not found"));
    model.addAttribute("props", found);
    return "details";
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
