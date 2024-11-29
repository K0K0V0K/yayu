package koko.yayu.generator;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import koko.yayu.config.FreeMarkerConfig;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import org.apache.commons.text.WordUtils;

public class TableGenerator {

  private final Map<String, String> displayNames = new LinkedHashMap<>();
  private final Map<String, TemplateMethodModelEx> propertyMappers = new HashMap<>();

  public static TableGenerator create() {
    return new TableGenerator();
  }

  public TableGenerator addField(String propertyName) {
    String cleared = propertyName.replaceAll("/", "");
    String capitalize = WordUtils.capitalize(cleared);
    String[] words = org.apache.commons.lang3.StringUtils.splitByCharacterTypeCamelCase(capitalize);
    String displayName = org.apache.commons.lang3.StringUtils.join(words, " ");
    return addField(propertyName, displayName);
  }

  public TableGenerator addField(
    String propertyName,
    String displayName
  ) {
    return addField(propertyName, displayName, null);
  }

  public TableGenerator addField(
    String propertyName,
    String displayName,
    String propertyMapper
  ) {
    displayNames.put(propertyName, displayName);
    if (propertyMapper != null) {
      propertyMappers.put(
        propertyName,
        (TemplateMethodModelEx) FreeMarkerConfig.methods.get(propertyMapper)
      );
    }
    return this;
  }

  public String generate(List<JSONObject> data, String sort) {
    return String.format("<table class=\"table\">%s%s</tbody></table>",
      heads(), body(order(sort, data)));
  }

  private String heads() {
    return String.format("<thead><tr>%s</tr></thead>", displayNames.keySet().stream()
      .map(this::head)
      .collect(Collectors.joining()));
  }

  private String head(String propertyName) {
    return String.format("<th> %s %s %s </th>",
      displayNames.get(propertyName),
      linkAsc(propertyName),
      linkDesc(propertyName)
    );
  }

  private String linkAsc(String propertyName) {
    return String.format("<a href=\"?order=%s-asc\"> &#8595; </a>",
      propertyName.replaceAll("/", ""));
  }

  private String linkDesc(String propertyName) {
    return String.format("<a href=\"?order=%s-desc\"> &#8593; </a>",
      propertyName.replaceAll("/", ""));
  }

  private String body(List<JSONObject> data) {
    return String.format("<tbody>%s</tbody>",
      data.stream().map(this::toLine).collect(Collectors.joining()));
  }

  private String toLine(JSONObject jsonObject) {
    return String.format("<tr>%s</tr>", displayNames.keySet().stream()
      .map(prop -> getField(jsonObject, prop))
      .collect(Collectors.joining()));
  }

  private String getField(JSONObject jsonObject, String propertyName) {
    return String.format("<td>%s</td>", getValue(jsonObject, propertyName));
  }

  private String getValue(JSONObject data, String key) {
    String[] split = key.split("#");
    Object re = data.query(split[0]);
    TemplateMethodModelEx mapper = propertyMappers.get(key);
    if (mapper != null) {
      try {
        re = mapper.exec(Stream.concat(
          Stream.of(split).skip(1),
          Stream.of(re)
        ).toList());
      } catch (TemplateModelException e) {
        throw new RuntimeException(e);
        //TODO
      }
    }
    return String.valueOf(re);
  }

  private List<JSONObject> order(String order, List<JSONObject> list) {
    if (StringUtils.hasLength(order)) {
      String[] params = order.split("-");
      list.sort(Comparator.comparing(o -> o.get(params[0]).toString()));
      if ("desc".equals(params[1])) {
        Collections.reverse(list);
      }
    }
    return list;
  }
}
