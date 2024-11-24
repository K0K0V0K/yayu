package koko.yayu.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import koko.yayu.component.ComponentGenerator;
import koko.yayu.service.RMApiService;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

  private final RMApiService RMApiService;

  public AppController(RMApiService RMApiService) {
    this.RMApiService = RMApiService;
  }

  @GetMapping("/")
  public String index(Model model, @RequestParam(defaultValue = "") String order) {
    List<JSONObject> resp = RMApiService.getApps();
    YayuUtil.order(order, resp);
    model.addAttribute("apps", resp);
    Map<String, Long> counts = resp.stream().collect(Collectors.groupingBy(
      jsonObject -> jsonObject.getString("state"),
      Collectors.counting()));
    model.addAttribute("counts", new TreeMap<>(counts));
    return "index";
  }

  @GetMapping("/app/{appId}")
  public String appDetails(Model model, @PathVariable String appId) {
    JSONObject resp = RMApiService.getApp(appId);
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
    List<JSONObject> attempts = RMApiService.getAttempts(appId);
    model.addAttribute("attempts", attempts);
    return "app-details";
  }
}
