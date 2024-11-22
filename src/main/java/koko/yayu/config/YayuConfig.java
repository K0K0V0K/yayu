package koko.yayu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YayuConfig {

  @Value("${yayu.mr-url}")
  private String mrUrl;

  public String getMrUrl() {
    return mrUrl;
  }
}
