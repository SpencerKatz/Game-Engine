package oogasalad.model.data;

import java.util.List;
import java.util.Map;
import oogasalad.model.api.exception.BadValueParseException;
import oogasalad.model.api.exception.InvalidRuleType;

/**
 * Class for data validation.
 */
public class DataValidation {

  private static Map<String, List<String>> types;

  /**
   * Validates the properties.
   *
   * @param properties The properties to validate.
   * @throws InvalidRuleType If an invalid rule type is encountered.
   */
  public static void validateProperties(Properties properties) throws InvalidRuleType {
    types = properties.getCopyOfPropertyTypes();
    for (Map.Entry<String, String> entry : properties.getCopyOfProperties().entrySet()) {
      validate(entry.getKey(), entry.getValue());
    }
    for (Map.Entry<String, List<String>> entry : properties.getCopyOfListProperties().entrySet()) {
      for (String str : entry.getValue()) {
        validate(entry.getKey(), str);
      }
    }
    for (Map<String, String> entryOut : properties.getCopyOfMapProperties().values()) {
      for (Map.Entry<String, String> entryIn : entryOut.entrySet()) {
        validate(entryIn.getKey(), entryIn.getValue());
      }
    }
  }

  /**
   * Validates a key-value pair.
   *
   * @param key   The key to validate.
   * @param value The value to validate.
   * @throws InvalidRuleType If the rule type is invalid.
   */
  public static void validate(String key, String value) throws InvalidRuleType {
    for (Map.Entry<String, List<String>> type : types.entrySet()) {
      if (type.getValue().contains(key)) {
        try {
          switch (type.getKey()) {
            case "int":
              validateInteger(value);
              break;
            case "boolean":
              validateBoolean(value);
              break;
            case "double":
              validateDouble(value);
              break;
          }
        } catch (BadValueParseException e) {
          throw new InvalidRuleType(key, value, e.getParseType());
        }
      }
    }
  }

  private static void validateInteger(String value) throws BadValueParseException {
    try {
      Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new BadValueParseException(value, "int");
    }
  }

  private static void validateBoolean(String value) throws BadValueParseException {
    if (!(value.equals("true") || value.equals("false"))) {
      throw new BadValueParseException(value, "boolean");
    }
  }

  private static void validateDouble(String value) throws BadValueParseException {
    try {
      Double.parseDouble(value);
    } catch (NumberFormatException e) {
      throw new BadValueParseException(value, "double");
    }
  }
}
