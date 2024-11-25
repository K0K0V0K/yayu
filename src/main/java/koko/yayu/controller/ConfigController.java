package koko.yayu.controller;

import java.util.Comparator;
import java.util.List;

import koko.yayu.service.apiservice.NativeApiService;
import koko.yayu.service.apiservice.RestApiService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfigController {

  private final NativeApiService nativeApiService;

  public ConfigController(NativeApiService nativeApiService) {
    this.nativeApiService = nativeApiService;
  }

  @GetMapping("/config")
  public String scheduler(Model model) {
    List<JSONObject> resp = nativeApiService.getConfig();
    resp.sort(Comparator.comparing(o -> o.getString("name")));
    model.addAttribute("props",resp);
    return "configs";
  }
}
