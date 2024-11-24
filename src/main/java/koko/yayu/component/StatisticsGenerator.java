package koko.yayu.component;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import koko.yayu.config.FreeMarkerConfig;
import org.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.WordUtils;

public class StatisticsGenerator {

  private final String title;
  private final int size;
  List<Pair<String, Integer>> stats;

  public StatisticsGenerator(String title, int size, String property, List<JSONObject> data) {
    this.title = title;
    this.size = size;
    stats =
      data.stream().collect(Collectors.groupingBy(
        jsonObject -> jsonObject.getString(property)
        )).entrySet().stream().map(
          entry -> Pair.of(entry.getKey(), entry.getValue().size()))
        .sorted(Map.Entry.comparingByValue())
        .limit(size)
        .toList();
  }

  public String generate() {
    String re = "<article class=\"message\"><div class=\"message-header\"><p>"
      + String.format("TOP %s %s", size, title)
      + "</p></div><div class=\"message-body\"><table class=\"table is-striped is-fullwidth\">";
    for (Pair<String, Integer> pair : stats) {
      re += "<tr class=\"is-dark\"><td>" + pair.getKey() + "</td><td>" + pair.getValue() + "</td></tr>";
    }
    re += "</table></div></article>";
    return re;
  }
}
