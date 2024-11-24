package koko.yayu.controller;

import java.util.Comparator;
import java.util.List;

import koko.yayu.service.ApiService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfigController {

  private final ApiService apiService;

  public ConfigController(ApiService apiService) {
    this.apiService = apiService;
  }

  @GetMapping("/config")
  public String scheduler(Model model) {
    List<JSONObject> resp = apiService.getConfig();
    resp.sort(Comparator.comparing(o -> o.getString("name")));
    model.addAttribute("props",resp);
    return "configs";
  }
}
