package koko.yayu.config;

import java.util.HashMap;
import java.util.Map;

import freemarker.template.TemplateMethodModelEx;
import koko.yayu.freemarker.AppStateMethod;
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

  public static final Map<String, Object> methods = Map.of(
    "time", new TimeMethod(),
    "progress", new ProgressMethod(),
    "tableHead", new TableHeadMethod(),
    "linkTag", new LinkTagMethod(),
    "details", new DetailsMethod(),
    "appState", new AppStateMethod()
  );

  @Bean
  public FreeMarkerConfigurer freeMarkerConfigurer() {
    FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
    configurer.setTemplateLoaderPath("classpath:/templates");
    configurer.setFreemarkerVariables(methods);
    return configurer;
  }
}