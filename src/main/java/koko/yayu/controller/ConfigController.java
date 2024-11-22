package koko.yayu.controller;

import java.util.Comparator;
import java.util.List;

import koko.yayu.service.RMApiService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfigController {

  private final RMApiService RMApiService;

  public ConfigController(RMApiService RMApiService) {
    this.RMApiService = RMApiService;
  }

  @GetMapping("/config")
  public String scheduler(Model model) {
    List<JSONObject> resp = RMApiService.getConfig();
    resp.sort(Comparator.comparing(o -> o.getString("name")));
    model.addAttribute("props",resp);
    return "configs";
  }
}
