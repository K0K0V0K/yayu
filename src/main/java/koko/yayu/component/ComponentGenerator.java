package koko.yayu.component;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import koko.yayu.config.FreeMarkerConfig;
import org.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

public class ComponentGenerator {

  private String title;
  private String color;
  private final Map<String, String> displayNames = new LinkedHashMap<>();
  private final Map<String, TemplateMethodModelEx> propertyMappers = new HashMap<>();

  public static ComponentGenerator create() {
    return new ComponentGenerator();
  }

  public ComponentGenerator setTitle(String title) {
    this.title = title;
    return this;
  }

  public ComponentGenerator setColor(String color) {
    this.color = color;
    return this;
  }

  public ComponentGenerator addField(String propertyName) {
    String cleared = propertyName.replaceAll("/", "");
    String capitalize = WordUtils.capitalize(cleared);
    String[] words = StringUtils.splitByCharacterTypeCamelCase(capitalize);
    String displayName = StringUtils.join(words, " ");
    return addField(propertyName, displayName);
  }

  public ComponentGenerator addField(String propertyName, String displayName) {
    return addField(propertyName, displayName, null);
  }

  public ComponentGenerator addField(
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

  public String generate(JSONObject data) {
    String re = String.format("<article class=\"panel %s\">\n", color);
    re += String.format("<div class=\"panel-heading\">%s</div>\n", title);
    for (Map.Entry<String, String> field : displayNames.entrySet()) {
      re += String.format("<a class=\"panel-block\">%s: %s</a>\n",
        field.getValue(), getValue(data, field.getKey()));
    }
    re += "</article>\n";
    return re;
  }

  public String generate(List<JSONObject> data) {
    String re = String.format("<article class=\"panel %s\">\n", color);
    re += String.format("<div class=\"panel-heading\">%s</div>\n", title);
    re += "<a class=\"panel-block\">";
    re += "<table class=\"table\"><thead><tr>\n";
    for (Map.Entry<String, String> field : displayNames.entrySet()) {
      re += String.format("<th>%s</th>\n", field.getValue());
    }
    re += " </tr></thead><tbody>\n";
    for (JSONObject line : data) {
      re += "<tr>\n";
      for (Map.Entry<String, String> field : displayNames.entrySet()) {
        re += String.format("<td>%s</td>\n", getValue(line, field.getKey()));
      }
      re += "</tr>\n";
    }
    re += "</tbody></table></a></article>\n";
    return re;
  }

  private String getValue(JSONObject data, String key) {
    Object re = data.query(key);
    TemplateMethodModelEx mapper = propertyMappers.get(key);
    if (mapper != null) {
      try {
        re = mapper.exec(Collections.singletonList(re));
      } catch (TemplateModelException e) {
        throw new RuntimeException(e);
        //TODO
      }
    }
    return String.valueOf(re);
  }
}
