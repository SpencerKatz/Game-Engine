package oogasalad.database;

public class Message {

  private final int senderId;
  private final String messageText;

  public Message(int senderId, String messageText) {
    this.senderId = senderId;
    this.messageText = messageText;
  }

  public int getSenderId() {
    return senderId;
  }

  public String getMessageText() {
    return messageText;
  }
}


