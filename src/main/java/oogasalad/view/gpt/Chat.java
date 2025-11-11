package oogasalad.view.gpt;

import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Chat {

  private VBox messagesContainer;
  private TextField messageField;
  private Button sendButton, backButton;
  private String threadId;
  private final OpenAI openAIConnector = new OpenAI();
  private final Stage primaryStage;

  public Chat(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public void start() {
    messagesContainer = new VBox(10);
    messagesContainer.setPadding(new Insets(10));

    ScrollPane scrollPane = new ScrollPane(messagesContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setVvalue(1.0);
    scrollPane.setPrefHeight(560);

    messageField = new TextField();
    messageField.setPromptText("Type your message here...");

    sendButton = new Button("Send");
    sendButton.setDefaultButton(true);
    sendButton.setOnAction(event -> handleUserInput(messageField.getText()));

    backButton = new Button("Back");
    backButton.setOnAction(event -> {
      primaryStage.close();
    });

    HBox inputBox = new HBox(10, messageField, sendButton, backButton);
    inputBox.setPadding(new Insets(10));
    inputBox.setAlignment(Pos.BOTTOM_CENTER);

    VBox layout = new VBox(10, scrollPane, inputBox);
    layout.setPadding(new Insets(10));

    Scene scene = new Scene(layout, 400, 600);
    primaryStage.setTitle("OpenAI Chatbot");
    primaryStage.setScene(scene);
    primaryStage.show();

    messageField.requestFocus();
  }

  private void handleUserInput(String userInput) {
    Platform.runLater(() -> {
      messageField.setDisable(true);
      sendButton.setDisable(true);
      displayMessage("Fetching response...", false);
    });

    CompletableFuture.runAsync(() -> {
      try {
        if (threadId == null || threadId.isEmpty()) {
          threadId = openAIConnector.createThread();
        }

        String runId = openAIConnector.executeThread(threadId, "asst_Syy6KcnglTgt0T3jlsh0OwTZ");

        while (!openAIConnector.checkCompletion(threadId)) {
          Thread.sleep(1000);
        }

        String responseText = openAIConnector.getMessages(threadId);
        Platform.runLater(() -> {
          messagesContainer.getChildren().remove(messagesContainer.getChildren().size() - 1);
          displayMessage(userInput, true);
          displayMessage(responseText, false);
          messageField.clear();
          messageField.setDisable(false);
          sendButton.setDisable(false);
        });
      } catch (Exception e) {
        e.printStackTrace();
        Platform.runLater(() -> {
          displayMessage("Error: " + e.getMessage(), false);
          messageField.setDisable(false);
          sendButton.setDisable(false);
        });
      }
    });
  }


  private void displayMessage(String text, boolean isUser) {
    Label label = new Label(text);
    label.setWrapText(true);
    label.setMaxWidth(300);
    label.setStyle("-fx-background-color: " + (isUser ? "lightblue" : "lightgrey")
        + "; -fx-padding: 5px; -fx-label-padding: 10; "
        + "-fx-border-radius: 5; -fx-background-radius: 5;");

    HBox messageBox = new HBox();
    messageBox.setPadding(new Insets(5));
    messageBox.getChildren().add(label);

    if (isUser) {
      messageBox.setAlignment(Pos.CENTER_RIGHT);
    } else {
      messageBox.setAlignment(Pos.CENTER_LEFT);
    }

    Platform.runLater(() -> messagesContainer.getChildren().add(messageBox));
  }

}
