package koko.yayu.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModelEx;
import org.springframework.stereotype.Component;

@Component
public class HaStateMethod implements TemplateMethodModelEx {

  @Override
  public Object exec(List arguments) {
    String url = arguments.get(0).toString();
    String state = arguments.get(1).toString();
    String color = "is-";
    if (state.equals("ACTIVE")) {
      color += "success";
    } else if (state.equals("STANDBY")) {
      color += "warning";
    } else if (state.equals("OFFLINE")) {
      color += "error";
    }
    return String.format("<span class=\"tag %s\">%s</span>", color, url);
  }
}