package koko.yayu.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import koko.yayu.util.YarnApiRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

  @GetMapping("/")
  public String index(
      Model model,
      @RequestParam(defaultValue = "") String order
  ) {
    List<JSONObject> resp = YarnApiRequest.get(
        "/ws/v1/cluster/apps",
        jsonObject -> YarnApiRequest.jsonArrayToList(jsonObject.getJSONObject("apps").getJSONArray("app"))
    );
    YarnApiRequest.order(order, resp);
    model.addAttribute("apps", resp);
    return "index";
  }

  @GetMapping("/app/{appId}")
  public String appDetails(
      Model model,
      @PathVariable String appId
  ) {
    JSONObject resp = YarnApiRequest.get(
        "/ws/v1/cluster/apps/" + appId,
        jsonObject -> jsonObject.getJSONObject("app")
    );
    model.addAttribute("props", resp);
    return "details";
  }
}
