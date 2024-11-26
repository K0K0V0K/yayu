package koko.yayu.controller;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

import koko.yayu.generator.ComponentGenerator;
import koko.yayu.service.ActiveRMService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClusterController {

  private final ActiveRMService activeRMService;

  public ClusterController(ActiveRMService activeRMService) {
    this.activeRMService = activeRMService;
  }

  @GetMapping("/cluster")
  public String cluster(Model model) {
    Map<URI, JSONObject> statuses = activeRMService.getAllStatuses();
    System.err.println(statuses);
    model.addAttribute("rms", activeRMService.getAllStatuses().entrySet().stream()
      .map(entry -> entry.getValue() == null
        ? crtOffline(entry.getKey())
        : ComponentGenerator.create()
          .setTitle(entry.getKey().toString())
          .addField("/hadoopBuildVersion")
          .addField("/resourceManagerVersionBuiltOn")
          .addField("/resourceManagerVersion")
          .addField("/resourceManagerBuildVersion")
          .addField("/hadoopVersion")
          .addField("/haZooKeeperConnectionState")
          .addField("/id")
          .addField("/state")
          .addField("/startedOn", "Started", "time")
          .addField("/haState")
          .addField("/rmStateStoreName")
          .addField("/hadoopVersionBuiltOn")
          .generate(entry.getValue())
      ).collect(Collectors.toList()));

    return "cluster";
  }

  private String crtOffline(URI key) {
    return "<article class=\"message\"><div class=\"message-header\"><p>"
      + key
      + "</p></div><div class=\"message-body\">OFFLINE</div></article>";
  }
}
