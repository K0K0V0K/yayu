package koko.yayu.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import koko.yayu.generator.ComponentGenerator;
import koko.yayu.generator.StatisticsGenerator;
import koko.yayu.service.ApiService;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

  private final ApiService apiService;

  public AppController(ApiService apiService) {
    this.apiService = apiService;
  }

  @GetMapping("/")
  public String index(Model model, @RequestParam(defaultValue = "") String order) {
    List<JSONObject> resp = apiService.getApps();
    YayuUtil.order(order, resp);
    model.addAttribute("apps", resp);
    Map<String, Long> counts = resp.stream().collect(Collectors.groupingBy(
      jsonObject -> jsonObject.getString("state"),
      Collectors.counting()));

    model.addAttribute("topUser",
      new StatisticsGenerator("User", 5, "user", resp).generate());
    model.addAttribute("topQueue",
      new StatisticsGenerator("Queue", 5, "queue", resp).generate());
    model.addAttribute("topAppType",
      new StatisticsGenerator("Application Type", 5, "applicationType", resp).generate());

    model.addAttribute("counts", new TreeMap<>(counts));
    return "index";
  }

  @GetMapping("/app/{appId}")
  public String appDetails(Model model, @PathVariable String appId) {
    JSONObject resp = apiService.getApp(appId);
    model.addAttribute("props", resp);

    model.addAttribute("info", ComponentGenerator.create()
      .setTitle("Info - " + appId)
      .setColor("is-info")
      .addField( "/applicationType")
      .addField( "/name")
      .addField( "/applicationTags")
      .addField( "/queue")
      .addField( "/user")
      .addField( "/state")
      .addField( "/finalStatus")
      .addField( "/startedTime", "Start", "time")
      .addField( "/finishedTime", "End", "time")
      .generate(resp)
    );

    model.addAttribute("capacity", ComponentGenerator.create()
      .setTitle("Capacity")
      .setColor("is-info")
      .addField("/allocatedMB")
      .addField("/allocatedVCores")
      .addField("/memorySeconds")
      .addField("/vcoreSeconds")
      .addField("/preemptedResourceVCores", "Preempted vCores")
      .addField("/preemptedResourceMB", "Preempted Memory MB")
      .addField("/preemptedVcoreSeconds")
      .addField("/preemptedMemorySeconds")
      .addField("/priority")
      .generate(resp)
    );
    model.addAttribute("appId", appId);
    List<JSONObject> attempts = apiService.getAttempts(appId);
    model.addAttribute("attempts", attempts);
    return "app-details";
  }
}
