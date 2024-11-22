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

    List<JSONObject> topQueues = YayuUtil.jsonListMapper("queues", "queue").apply(resp);
    Map<Integer, List<JSONObject>> queues = flatten(topQueues).collect(
      Collectors.groupingBy(queue -> StringUtils.countMatches(queue.getString("queuePath"), ".")));
    queues.replaceAll(
      (_, lists) -> lists.stream().sorted(Comparator.comparing(o -> o.getString("queuePath")))
        .toList());
    model.addAttribute("queues", queues);
    return "queues";
  }

  public Stream<JSONObject> flatten(List<JSONObject> queues) {
    return queues.stream().flatMap(queue -> {
      JSONObject leafs = queue.optJSONObject("queues");
      if (leafs != null && !leafs.isEmpty()) {
        queue.put("isLeaf", "NO");
        return Stream.concat(flatten(YayuUtil.jsonArrayToList(leafs.getJSONArray("queue"))),
          Stream.of(queue));
      }
      queue.put("isLeaf", "YES");
      return Stream.of(queue);
    });
  }
}
