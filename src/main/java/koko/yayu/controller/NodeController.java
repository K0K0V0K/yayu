package koko.yayu.controller;

import java.util.List;

import koko.yayu.service.ApiService;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NodeController {

  private final ApiService apiService;

  public NodeController(ApiService apiService) {
    this.apiService = apiService;
  }

  @GetMapping("/nodes")
  public String nodes(Model model, @RequestParam(defaultValue = "") String order) {
    List<JSONObject> resp = apiService.getNodes();
    YayuUtil.order(order, resp);
    model.addAttribute("nodes", resp);
    return "nodes";
  }

  @GetMapping("/node/{nodeId}")
  public String nodeDetails(Model model, @PathVariable String nodeId) {
    JSONObject resp = apiService.getNode(nodeId);
    model.addAttribute("props", resp);
    return "details";
  }

}
