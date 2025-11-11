package oogasalad.database;

public class Thought {

  private final int id;
  private final String username;
  private final String text;
  private final String time;

  public Thought(int id, String username, String text, String time) {
    this.id = id;
    this.username = username;
    this.text = text;
    this.time = time;
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getText() {
    return text;
  }

  public String getTime() {
    return time;
  }
}