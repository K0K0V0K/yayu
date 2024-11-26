package koko.yayu.config;

import java.util.List;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YayuConfig {
  private static final Logger LOG = LoggerFactory.getLogger(YayuConfig.class);

  @Value("${yayu.mr-url}")
  private List<String> mrUrl;

  @PostConstruct
  public void init() {
    LOG.info("YayuConfig init for mrUrl: {}", mrUrl);
  }

  public List<String> getMrUrl() {
    return mrUrl;
  }
}
