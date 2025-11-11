package oogasalad.model.api.exception;

public class InvalidGameObjectType extends RuntimeException {

  private final String type;

  public InvalidGameObjectType(String message, String type) {
    super(message);
    this.type = type;
  }

  public String getType() {
    return type;
  }

}
