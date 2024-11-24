package koko.yayu.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YayuConfig {

  @Value("${yayu.mr-url}")
  private List<String> mrUrl;

  public List<String> getMrUrl() {
    return mrUrl;
  }
}
