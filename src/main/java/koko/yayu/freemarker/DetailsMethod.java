package koko.yayu.freemarker;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import freemarker.ext.beans.GenericObjectModel;
import freemarker.template.TemplateMethodModelEx;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import org.apache.commons.lang3.time.DurationFormatUtils;

@Component
public class DetailsMethod implements TemplateMethodModelEx {

  @Override
  public Object exec(List arguments) {
    GenericObjectModel genericObjectModel = (GenericObjectModel) arguments.get(0);
    JSONObject json = (JSONObject) genericObjectModel.getAdaptedObject(JSONObject.class);
    return json.keySet().stream().sorted().map(key -> createTableLine(key, json.get(key)))
      .collect(Collectors.joining("\n"));
  }

  private String createTableLine(String key, Object value) {
    return String.format("<tr> <td> %s </td> <td> %s </td> </tr>", key,
      createValueField(key, value));
  }

  private String createValueField(String key, Object value) {
    if (value instanceof String && value.toString().startsWith("http")) {
      return String.format("<a href=\"%s\"> %s </a>", value, value);
    }
    if (value instanceof JSONObject) {
      return printJSONObject((JSONObject) value);
    }
    if (List.of("finishedTime", "launchTime", "startedTime", "lastHealthUpdate").contains(key)) {
      Instant instant = Instant.ofEpochMilli(Long.parseLong(value.toString()));
      LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      return dateTime.format(formatter);
    }
    if ("elapsedTime".equals(key)) {
      return DurationFormatUtils.formatDuration(Long.parseLong(value.toString()), "HH:MM:SS", true);
    }
    return value.toString();
  }

  private String printJSONObject(JSONObject value) {
    //It is a transformed map
    if (value.keySet().size() == 1 && value.has("entry")) {
      return value.getJSONArray("entry").toList().stream().map(o -> (HashMap) o)
        .map(o -> String.format("<span class=\"tag\">%s: %s</span> ", o.get("key"), o.get("value")))
        .sorted().collect(Collectors.joining("\n"));
    }
    //return String.format("<span class=\"tag\"> %s </span>", value);
    return value.toString();
  }

}