package koko.yayu.controller;

import koko.yayu.service.apiservice.NativeApiService;
import koko.yayu.service.apiservice.RestApiService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class LogDetailsController {

  private final NativeApiService nativeApiService;

  public LogDetailsController(NativeApiService nativeApiService) {
    this.nativeApiService = nativeApiService;
  }

  @GetMapping(value = "/logs/{fileName}", produces = MediaType.TEXT_PLAIN_VALUE)
  public Flux<String> file(@PathVariable String fileName) {
    return nativeApiService.getLog(fileName);
  }
}
