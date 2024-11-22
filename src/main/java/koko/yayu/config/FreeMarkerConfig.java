package koko.yayu.config;

import java.util.HashMap;
import java.util.Map;

import koko.yayu.freemarker.DetailsMethod;
import koko.yayu.freemarker.LinkTagMethod;
import koko.yayu.freemarker.ProgressMethod;
import koko.yayu.freemarker.TableHeadMethod;
import koko.yayu.freemarker.TimeMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
public class FreeMarkerConfig {

  @Bean
  public FreeMarkerConfigurer freeMarkerConfigurer() {
    FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
    configurer.setTemplateLoaderPath("classpath:/templates");

    Map<String, Object> variables = new HashMap<>();
    variables.put("time", new TimeMethod());
    variables.put("progress", new ProgressMethod());
    variables.put("tableHead", new TableHeadMethod());
    variables.put("linkTag", new LinkTagMethod());
    variables.put("details", new DetailsMethod());
    configurer.setFreemarkerVariables(variables);

    return configurer;
  }
}