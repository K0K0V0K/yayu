package koko.yayu.service.apiservice;

import java.util.List;

import koko.yayu.config.YayuConfig;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class NativeApiService extends AbstractApiService {

  public NativeApiService(YayuConfig config) {
    super(config.getMrUrl(), "", 512);
  }

  public String getLogs() {
    String raw = getActiveClient().get()
      .uri("/logs/")
      .retrieve()
      .bodyToMono(String.class)
      .block();
    return raw.substring(raw.indexOf("<tbody>"), raw.indexOf("</tbody"));
  }

  public Flux<String> getLog(String fileName) {
    return getActiveClient().get()
      .uri("/logs/" + fileName)
      .retrieve()
      .bodyToFlux(String.class)
      .map(s -> s.concat(System.lineSeparator()));
  }

  public List<JSONObject> getConfig() {
    return get( "/conf", YayuUtil.jsonListMapper("configuration", "property"));
  }
}
