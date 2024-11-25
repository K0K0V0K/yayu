package koko.yayu.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import koko.yayu.service.ActiveRMService;
import koko.yayu.service.apiservice.NativeApiService;
import koko.yayu.service.apiservice.RestApiService;

@WebFilter("/*")
public class ActiveRMFilter implements Filter {
  private final ActiveRMService activeRMService;
  private final RestApiService restApiService;
  private final NativeApiService nativeApiService;

  public ActiveRMFilter(
    ActiveRMService activeRMService,
    RestApiService restApiService,
    NativeApiService nativeApiService
  ) {
    this.activeRMService = activeRMService;
    this.restApiService = restApiService;
    this.nativeApiService = nativeApiService;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    activeRMService.refresh();
    restApiService.setActive(activeRMService.getActive());
    nativeApiService.setActive(activeRMService.getActive());
    chain.doFilter(request, response);
  }
}
