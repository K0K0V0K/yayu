package koko.yayu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

@SpringBootApplication
@EnableScheduling
public class YayuApplication {

  public static void main(String[] args) {
    SpringApplication.run(YayuApplication.class, args);
  }

}
