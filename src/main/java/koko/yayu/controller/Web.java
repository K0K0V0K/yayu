package koko.yayu.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Web {

  String url = "TODO";

  @GetMapping("/")
  public String index(
      Model model,
      @RequestParam(defaultValue = "") String order
  ) {
    List<JSONObject> resp = YarnApiRequest.get(
        url + "/ws/v1/cluster/apps",
        jsonObject -> jsonArrayToList(jsonObject.getJSONObject("apps").getJSONArray("app"))
    );

    if (StringUtils.hasLength(order)) {
      String[] params = order.split("-");
      resp.sort(Comparator.comparing(o -> o.get(params[0]).toString()));
      if ("desc".equals(params[1])) {
        Collections.reverse(resp);
      }
    }

    resp = resp.stream().limit(25).collect(Collectors.toList());
    model.addAttribute("apps", resp);
    return "index";
  }

  @GetMapping("/app/{appId}")
  public String appDetails(
      Model model,
      @PathVariable String appId
  ) {
    JSONObject resp = YarnApiRequest.get(
        url + "/ws/v1/cluster/apps/" + appId,
        jsonObject -> jsonObject.getJSONObject("app")
    );
    model.addAttribute("app", resp);
    return "app-details";
  }

  private List<JSONObject> jsonArrayToList(JSONArray jsonArray) {
    return IntStream.range(0, jsonArray.length())
        .mapToObj(jsonArray::getJSONObject)
        .collect(Collectors.toList());
  }
}
