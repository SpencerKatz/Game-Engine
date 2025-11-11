package oogasalad.model.api.exception;

/**
 * Thrown when failed trying to parse a String value into some different type.
 *
 * @author Jason Qiu
 */
public class BadValueParseException extends RuntimeException {

  public BadValueParseException(String badValue, String parseType) {
    super(BadValueParseException.class.getSimpleName());
    this.badValue = badValue;
    this.parseType = parseType;
  }

  public BadValueParseException(String badValue, String parseType, Exception exception) {
    super(BadValueParseException.class.getSimpleName(), exception);
    this.badValue = badValue;
    this.parseType = parseType;
  }

  public String getBadValue() {
    return badValue;
  }

  public String getParseType() {
    return parseType;
  }

  private final String badValue;
  private final String parseType;
}
