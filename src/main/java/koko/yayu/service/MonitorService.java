package koko.yayu.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MonitorService {

  private final ActiveRMService activeRMService;
  private final ApiService apiService;

  public MonitorService(ActiveRMService activeRMService, ApiService apiService) {
    this.activeRMService = activeRMService;
    this.apiService = apiService;
  }

  @Scheduled(fixedRate = 1000)
  private void poll() {
    activeRMService.refresh();
    apiService.setActive(activeRMService.getActive());
    apiService.refresh();
  }
}
