package koko.yayu.controller;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class YarnApiRequest {

  public static <T> T get(String uri, Function<JSONObject, T> map){
    return WebClient.builder()
        .baseUrl(uri)
        .build()
        .get()
        .accept(MediaType.APPLICATION_XML)
        .retrieve()
        .bodyToMono(String.class)
        .map(XML::toJSONObject)
        .map(map)
        .block();
  }
}
