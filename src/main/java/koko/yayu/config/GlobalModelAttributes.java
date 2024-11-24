package koko.yayu.config;

import koko.yayu.service.ActiveRMService;
import koko.yayu.service.RMApiService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

  private final ActiveRMService activeRMService;

  public GlobalModelAttributes(ActiveRMService activeRMService) {
    this.activeRMService = activeRMService;
  }

  @ModelAttribute
  public void addGlobalAttributes(Model model) {
    model.addAttribute("clusterInfo", activeRMService.getActiveStatus());
    model.addAttribute("haStatuses", activeRMService.getStatuses());
  }
}