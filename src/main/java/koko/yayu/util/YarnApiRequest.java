package koko.yayu.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import koko.yayu.config.YayuConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

public class YarnApiRequest {

  public static <T> T get(String uri, Function<JSONObject, T> map) {
    return WebClient.builder().baseUrl(YayuConfig.URL + uri).exchangeStrategies(
        ExchangeStrategies.builder()
          .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build())
      .build().get().accept(MediaType.APPLICATION_XML).retrieve().bodyToMono(String.class)
      .map(XML::toJSONObject).map(map).block();
  }

  public static List<JSONObject> getJsonArray(JSONObject jsonObject, String... path) {
    JSONObject current = jsonObject;
    for (int i = 0; i < path.length - 1; ++i) {
      current = current.getJSONObject(path[i]);
    }
    JSONArray array = current.optJSONArray(path[path.length - 1]);
    return array != null ?
      jsonArrayToList(array) :
      Collections.singletonList(current.getJSONObject(path[path.length - 1]));
  }

  public static List<JSONObject> jsonArrayToList(JSONArray jsonArray) {
    return IntStream.range(0, jsonArray.length()).mapToObj(jsonArray::getJSONObject)
      .collect(Collectors.toList());
  }

  public static List<JSONObject> order(String order, List<JSONObject> list) {
    if (StringUtils.hasLength(order)) {
      String[] params = order.split("-");
      list.sort(Comparator.comparing(o -> o.get(params[0]).toString()));
      if ("desc".equals(params[1])) {
        Collections.reverse(list);
      }
    }
    return list;
  }
}
