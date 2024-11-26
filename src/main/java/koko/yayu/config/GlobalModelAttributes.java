package koko.yayu.config;

import koko.yayu.service.ActiveRMService;
import koko.yayu.service.apiservice.NativeApiService;
import koko.yayu.service.apiservice.RestApiService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

  private final ActiveRMService activeRMService;
  private final RestApiService restApiService;
  private final NativeApiService nativeApiService;

  public GlobalModelAttributes(ActiveRMService activeRMService, RestApiService restApiService,
    NativeApiService nativeApiService) {
    this.activeRMService = activeRMService;
    this.restApiService = restApiService;
    this.nativeApiService = nativeApiService;
  }

  @ModelAttribute
  public void addGlobalAttributes(Model model) {
    activeRMService.refresh();
    restApiService.setActive(activeRMService.getActive());
    nativeApiService.setActive(activeRMService.getActive());
    model.addAttribute("clusterInfo", activeRMService.getActiveStatus());
    model.addAttribute("haStatuses", activeRMService.getStatuses());
    model.addAttribute("user", restApiService.getClusterUser());
  }
}