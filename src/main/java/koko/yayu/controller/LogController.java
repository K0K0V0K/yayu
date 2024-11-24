package koko.yayu.controller;

import koko.yayu.service.ApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogController {

  private final ApiService apiService;

  public LogController(ApiService apiService) {
    this.apiService = apiService;
  }

  @GetMapping("/logs")
  public String scheduler(Model model) {
    String resp = apiService.getLogs();
    model.addAttribute("props", resp);
    return "logs";
  }
}
