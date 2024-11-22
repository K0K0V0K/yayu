package koko.yayu.freemarker;

import java.util.List;
import java.util.Objects;

import freemarker.template.TemplateMethodModelEx;
import org.springframework.stereotype.Component;

@Component
public class AppStateMethod implements TemplateMethodModelEx {

  @Override
  public Object exec(List arguments) {
    String appState = arguments.get(0).toString();
    String color = "";
    if (Objects.equals(appState, "FAILED") || Objects.equals(appState, "ERROR") || Objects.equals(appState, "KILLED")) {
      color = "is-danger";
    } else if (Objects.equals(appState, "FINISHED") ) {
      color = "is-success";
    } else if (Objects.equals(appState, "SUBMITTED") || Objects.equals(appState, "ACCEPTED")) {
      color = "is-warning";
    } else if (Objects.equals(appState, "RUNNING") ) {
      color = "is-primary";
    }
    return String.format("<span class=\"tag %s\">%s</span>\n", color, appState);
  }
}