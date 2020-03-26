import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Constants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This class implements the functionality to flatten a json.
 * Author: Mudit Mehrotra
 */
public class JsonFlattener {
  private static final Logger LOG = LoggerFactory.getLogger(JsonFlattener.class);
  private ObjectMapper mapper;

  public JsonFlattener() {
    mapper = new ObjectMapper();
  }

  /**
   * This method implements the functionality to flatten a json map. It ignores json arrays and recursively calls itself if json value is a map.
   * @param jsonMap
   * @return
   */
  public Map<String, Object> flattenJsonMap(LinkedHashMap<String, Object> jsonMap) {
    if (MapUtils.isEmpty(jsonMap))
      return MapUtils.EMPTY_SORTED_MAP;
    Map<String, Object> flattenedJsonMap = new FlatLinkedHashMap<>();
    for(Map.Entry<String, Object> entry : jsonMap.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if(value instanceof ArrayList) {
        LOG.warn("Flattening json that contain an array of values is not supported by this library. Skipping flattening key " + key);
        continue;
      }
      if(value instanceof LinkedHashMap){
        Map<String, Object> flatNestedJsonMap = flattenJsonMap((LinkedHashMap<String, Object>) value);
        flatNestedJsonMap.forEach((k,v) -> flattenedJsonMap.put(key + Constants.DOT + k, v));
      }
      else
        flattenedJsonMap.put(key, value);
    }
    return flattenedJsonMap;
  }

  /**
   * This method implements the functionality to flatten a json string. It parses the json, creates k-v pairs and invokes the method to flatten a json map
   * @param json
   * @return
   * @throws JsonProcessingException
   */
  public String flatten(String json) throws JsonProcessingException {
    Map<String, Object> flattenedJsonMap = new FlatLinkedHashMap<>();
    if(StringUtils.isEmpty(StringUtils.trimToEmpty(json)))
      return flattenedJsonMap.toString();
    Map<String, Object> jsonInputAsMap;
    try {
      jsonInputAsMap = mapper.readValue(json, LinkedHashMap.class);
      flattenedJsonMap = flattenJsonMap((LinkedHashMap<String, Object>)jsonInputAsMap);
    } catch (JsonMappingException e) {
      LOG.error(e.getMessage());
      throw new JsonMappingException(e.getMessage());
    } catch (JsonProcessingException e) {
      LOG.error(e.getMessage());
      throw (new JsonProcessingException(e.getMessage()){});
    }
    return flattenedJsonMap.toString();
  }

  public static void main(String[] args) {
    BasicConfigurator.configure();
    Scanner scanner = new Scanner(System.in);
    StringBuilder json = new StringBuilder();
    while(scanner.hasNextLine()) {
      json.append(scanner.nextLine());
    }
    try {
      String result = new JsonFlattener().flatten(json.toString());
      System.out.println(result);
    } catch(Exception e) {
      LOG.error("Encountered exception while flattening json." + e.getMessage());
    }
  }
}
