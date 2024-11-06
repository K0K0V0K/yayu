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
public class NodeController {

  @GetMapping("/nodes")
  public String nodes(
      Model model,
      @RequestParam(defaultValue = "") String order
  ) {
    List<JSONObject> resp = YarnApiRequest.get(
        "/ws/v1/cluster/nodes",
        jsonObject -> YarnApiRequest.jsonArrayToList(jsonObject.getJSONObject("nodes").getJSONArray("node"))
    );
    YarnApiRequest.order(order, resp);
    model.addAttribute("nodes", resp);
    return "nodes";
  }

  @GetMapping("/node/{node}")
  public String nodeDetails(
      Model model,
      @PathVariable String node
  ) {
    JSONObject resp = YarnApiRequest.get(
        "/ws/v1/cluster/nodes/" + node,
        jsonObject -> jsonObject.getJSONObject("node")
    );
    model.addAttribute("props", resp);
    return "details";
  }


}
