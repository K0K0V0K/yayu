package koko.yayu.generator;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import koko.yayu.config.FreeMarkerConfig;
import org.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.WordUtils;

public class ComponentGenerator {

  private String title;
  private final Map<String, String> displayNames = new LinkedHashMap<>();
  private final Map<String, TemplateMethodModelEx> propertyMappers = new HashMap<>();

  public static ComponentGenerator create() {
    return new ComponentGenerator();
  }

  public ComponentGenerator setTitle(String title) {
    this.title = title;
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
    String re = "<article class=\"message\"><div class=\"message-header\"><p>"
      + title
      + "</p></div><div class=\"message-body\"><table class=\"table is-striped is-fullwidth\">";
    for (Map.Entry<String, String> field : displayNames.entrySet()) {
      re += String.format("<tr><td>%s</td><td>%s</td></tr>\n",
        field.getValue(), getValue(data, field.getKey()));
    }
    re += "</table></div></article>";
    return re;
  }

  public String generate(List<JSONObject> data) {
    String re = "<article class=\"message\"><div class=\"message-header\"><p>"
      + title
      + "</p></div><div class=\"message-body\">"
      + "<table class=\"table is-striped is-fullwidth\"><thead><tr>";
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
    re += "</tbody></table></div></article>\n";
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
