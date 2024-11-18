package koko.yayu.controller;

import koko.yayu.util.YarnApiRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QueueController {

  @GetMapping("/queues")
  public String nodes(Model model) {
    JSONObject resp = YarnApiRequest.get(
        "/ws/v1/cluster/scheduler",
        jsonObject -> jsonObject.getJSONObject("scheduler").getJSONObject("schedulerInfo")
    );
    model.addAttribute("props", resp);
    return "details";
  }

}
