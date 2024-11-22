package koko.yayu.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModelEx;
import org.springframework.stereotype.Component;

@Component
public class ProgressMethod implements TemplateMethodModelEx {

  @Override
  public Object exec(List arguments) {
    int progress = (int) Double.parseDouble(arguments.get(0).toString());
    String color = "is-info";
    if (progress == 0) {
      color = "is-white";
    } else if (progress == 100) {
      color = "is-success";
    }
    return String.format("<span class=\"tag %s\">%s</span>\n", color, progress);
  }
}