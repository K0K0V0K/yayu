package koko.yayu.config;

import koko.yayu.service.RMApiService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    private final RMApiService rmApiService;

  public GlobalModelAttributes(RMApiService rmApiService) {
    this.rmApiService = rmApiService;
  }

  @ModelAttribute
  public void addGlobalAttributes(Model model) {
    System.err.println(rmApiService.getClusterInfo());
    model.addAttribute("clusterInfo", rmApiService.getClusterInfo());
  }
}