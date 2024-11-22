package koko.yayu.freemarker;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import freemarker.template.TemplateMethodModelEx;
import org.springframework.stereotype.Component;

@Component
public class TimeMethod implements TemplateMethodModelEx {

  @Override
  public Object exec(List arguments) {
    String input = arguments.get(0).toString();
    Instant instant = Instant.ofEpochMilli(Long.parseLong(input));
    LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return dateTime.format(formatter);
  }
}