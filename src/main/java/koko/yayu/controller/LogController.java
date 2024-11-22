package koko.yayu.controller;

import koko.yayu.service.RMApiService;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LogController {

  private final RMApiService RMApiService;

  public LogController(RMApiService RMApiService) {
    this.RMApiService = RMApiService;
  }

  @GetMapping("/logs")
  public String scheduler(Model model) {
    String resp = RMApiService.getLogs();
    model.addAttribute("props", resp);
    return "logs";
  }
}
