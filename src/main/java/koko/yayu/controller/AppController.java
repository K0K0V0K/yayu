package koko.yayu.controller;

import java.util.List;

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
    return "index";
  }

  @GetMapping("/app/{appId}")
  public String appDetails(Model model, @PathVariable String appId) {
    JSONObject resp = RMApiService.getApp(appId);
    model.addAttribute("props", resp);
    return "details";
  }
}
