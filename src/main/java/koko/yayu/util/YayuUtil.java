package koko.yayu.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

public class YayuUtil {

  public static Function<JSONObject, JSONObject> jsonObjectMapper(String ... paths) {
    return jsonObject -> {
      if (paths == null || paths.length == 0) {
        return jsonObject;
      }
      JSONObject re = jsonObject;
      for (String path : paths) {
        re = re.getJSONObject(path);
      }
      return re;
    };
  }

  public static JSONObject mapJsonObject(JSONObject jsonObject, String ... paths) {
    return jsonObjectMapper(paths).apply(jsonObject);
  }

  public static Function<JSONObject, List<JSONObject>> jsonListMapper(String ... paths) {
    return jsonObject -> {
      JSONObject re = jsonObject;
      for (int i = 0; i < paths.length - 1; ++i) {
        re = re.optJSONObject(paths[i]);
        if (re == null) {
          return Collections.emptyList();
        }
      }
      JSONArray array = re.optJSONArray(paths[paths.length - 1]);
      return array != null
        ? jsonArrayToList(array)
        : Collections.singletonList(re.getJSONObject(paths[paths.length - 1]));
    };
  }

  public static List<JSONObject> mapJsonList(JSONObject jsonObject, String ... paths) {
    return jsonListMapper(paths).apply(jsonObject);
  }

  public static List<JSONObject> jsonArrayToList(JSONArray jsonArray) {
    return IntStream.range(0, jsonArray.length())
      .mapToObj(jsonArray::getJSONObject)
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
