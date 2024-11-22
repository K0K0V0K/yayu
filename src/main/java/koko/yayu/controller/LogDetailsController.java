package koko.yayu.controller;

import koko.yayu.service.RMApiService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class LogDetailsController {

  private final RMApiService RMApiService;

  public LogDetailsController(RMApiService RMApiService) {
    this.RMApiService = RMApiService;
  }

  @GetMapping(value = "/logs/{fileName}", produces = MediaType.TEXT_PLAIN_VALUE)
  public Flux<String> file(@PathVariable String fileName) {
    return RMApiService.getLog(fileName);
  }
}
