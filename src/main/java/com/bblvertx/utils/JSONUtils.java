package com.bblvertx.utils;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections.MapUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bblvertx.SeConstants;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utils class for marshall and unmarshall JSON expression.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class JSONUtils {
  private static final Logger LOGGER = LogManager.getLogger(JSONUtils.class);

  /**
   * Convert map to json.
   * 
   * @param map
   * @return String
   */
  public static String map2jsonQuietly(Map<?, ?> map) {
    if (!isNotEmpty(map)) {
      return SeConstants.EMPTY_STRING;
    }

    String rtn = SeConstants.EMPTY_STRING;

    try {
      ObjectMapper mapper = new ObjectMapper();
      rtn = mapper.writeValueAsString(map);
    } catch (JsonGenerationException e) {
      LOGGER.error("Erreur de parsing", e);
    } catch (JsonMappingException e) {
      LOGGER.error("Erreur de parsing", e);
    } catch (IOException e) {
      LOGGER.error("Erreur de parsing", e);
    }

    return rtn;
  }

  /**
   * Convert object to JSON.
   * 
   * @param obj
   * @param clazz
   * @return String
   */
  public static String objectTojsonQuietly(Object obj, Class<?> clazz) {
    ObjectMapper mapper = new ObjectMapper();
    String json = null;
    try {
      json = mapper.writeValueAsString(clazz.cast(obj));
    } catch (JsonProcessingException e) {
      LOGGER.error("Erreur de parsing", e);
    }

    return json;
  }

  /**
   * Convert List to JSON.
   * 
   * @param list
   * @return String
   */
  public static String list2jsonQuietly(List<? extends List<String>> list) {
    if (!isNotEmpty(list)) {
      return SeConstants.EMPTY_JSON;
    }

    String rtn = SeConstants.EMPTY_JSON;

    try {
      ObjectMapper mapper = new ObjectMapper();
      rtn = mapper.writeValueAsString(list);
    } catch (JsonGenerationException e) {
      LOGGER.error("Erreur de parsing", e);
    } catch (JsonMappingException e) {
      LOGGER.error("Erreur de parsing", e);
    } catch (IOException e) {
      LOGGER.error("Erreur de parsing", e);
    }

    return rtn;
  }

  /**
   * Convert generic list to JSON.
   * 
   * @param list
   * @return String
   */
  public static String genericList2jsonQuietly(List<?> list) {
    if (!isNotEmpty(list)) {
      return SeConstants.EMPTY_JSON;
    }

    String rtn = SeConstants.EMPTY_JSON;

    try {
      ObjectMapper mapper = new ObjectMapper();
      rtn = mapper.writeValueAsString(list);
    } catch (JsonGenerationException e) {
      LOGGER.error("Erreur de parsing", e);
    } catch (JsonMappingException e) {
      LOGGER.error("Erreur de parsing", e);
    } catch (IOException e) {
      LOGGER.error("Erreur de parsing", e);
    }

    return rtn;
  }

  /**
   * Convert String JSON in a map.
   * 
   * @param json
   * @return the map
   */
  public static Map<String, String> json2mapQuietly(String json) {
    Map<String, String> rtn = null;

    if (isNotEmpty(json)) {
      try {
        ObjectMapper mapper = new ObjectMapper();
        rtn = mapper.readValue(json, new TypeReference<HashMap<String, String>>() {});
      } catch (Exception e) {
        LOGGER.error("Erreur de parsing", e);
      }
    }

    return rtn;
  }

  /**
   * Utils class => private constructor.
   */
  private JSONUtils() {}
}
