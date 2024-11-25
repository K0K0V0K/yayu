package koko.yayu.controller;

import koko.yayu.service.apiservice.NativeApiService;
import koko.yayu.service.apiservice.RestApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogController {

  private final NativeApiService nativeApiService;

  public LogController(NativeApiService nativeApiService) {
    this.nativeApiService = nativeApiService;
  }

  @GetMapping("/logs")
  public String scheduler(Model model) {
    String resp = nativeApiService.getLogs();
    model.addAttribute("props", resp);
    return "logs";
  }
}
