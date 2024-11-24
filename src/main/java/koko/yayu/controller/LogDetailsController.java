package koko.yayu.controller;

import koko.yayu.service.ApiService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class LogDetailsController {

  private final ApiService apiService;

  public LogDetailsController(ApiService apiService) {
    this.apiService = apiService;
  }

  @GetMapping(value = "/logs/{fileName}", produces = MediaType.TEXT_PLAIN_VALUE)
  public Flux<String> file(@PathVariable String fileName) {
    return apiService.getLog(fileName);
  }
}
