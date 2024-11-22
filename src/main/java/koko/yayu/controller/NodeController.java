package koko.yayu.controller;

import java.util.List;

import koko.yayu.service.ResourceManagerMonitor;
import koko.yayu.util.YarnApiRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NodeController {

  private final ResourceManagerMonitor resourceManagerMonitor;

  public NodeController(ResourceManagerMonitor resourceManagerMonitor) {
    this.resourceManagerMonitor = resourceManagerMonitor;
  }

  @GetMapping("/nodes")
  public String nodes(Model model, @RequestParam(defaultValue = "") String order) {
    List<JSONObject> resp = resourceManagerMonitor.getNodes();
    YarnApiRequest.order(order, resp);
    model.addAttribute("nodes", resp);
    return "nodes";
  }

  @GetMapping("/node/{node}")
  public String nodeDetails(Model model, @PathVariable String node) {
    JSONObject resp = YarnApiRequest.get("/ws/v1/cluster/nodes/" + node,
      jsonObject -> jsonObject.getJSONObject("node"));
    model.addAttribute("props", resp);
    return "details";
  }

}
