package koko.yayu.service;

import koko.yayu.service.apiservice.NativeApiService;
import koko.yayu.service.apiservice.RestApiService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MonitorService {

  private final ActiveRMService activeRMService;
  private final RestApiService restApiService;
  private final NativeApiService nativeApiService;

  public MonitorService(
    ActiveRMService activeRMService,
    RestApiService restApiService,
    NativeApiService nativeApiService
  ) {
    this.activeRMService = activeRMService;
    this.restApiService = restApiService;
    this.nativeApiService = nativeApiService;
  }

  @Scheduled(fixedRate = 1000)
  private void poll() {
    activeRMService.refresh();
    restApiService.setActive(activeRMService.getActive());
    nativeApiService.setActive(activeRMService.getActive());
    restApiService.refresh();
  }
}
