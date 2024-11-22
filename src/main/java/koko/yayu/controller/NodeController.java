package koko.yayu.controller;

import java.util.List;

import koko.yayu.service.RMApiService;
import koko.yayu.util.YayuUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NodeController {

  private final RMApiService RMApiService;

  public NodeController(RMApiService RMApiService) {
    this.RMApiService = RMApiService;
  }

  @GetMapping("/nodes")
  public String nodes(Model model, @RequestParam(defaultValue = "") String order) {
    List<JSONObject> resp = RMApiService.getNodes();
    YayuUtil.order(order, resp);
    model.addAttribute("nodes", resp);
    return "nodes";
  }

  @GetMapping("/node/{nodeId}")
  public String nodeDetails(Model model, @PathVariable String nodeId) {
    JSONObject resp = RMApiService.getNode(nodeId);
    model.addAttribute("props", resp);
    return "details";
  }

}
