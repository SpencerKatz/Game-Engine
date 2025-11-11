package oogasalad.view.gpt;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class OpenAI {

  private static final String API_KEY = "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";
  private static final String BASE_URL = "https://api.openai.com/v1";
  private static final String Authorization_S1 = "Bearer " + API_KEY;
  private static final String Authorization_S = "Authorization";
  private static final String OPENAI_BETA = "OpenAI-Beta";
  private static final String CONTENT_TYPE = "Content-Type";
  private static final String ASSISTANT_V1 = "assistants=v1";
  private static final String THREADS = "/threads/";

  public String createThread() throws Exception {
    HttpResponse<JsonNode> response =
        Unirest.post(BASE_URL + "/threads").header(Authorization_S, Authorization_S1)
            .header(CONTENT_TYPE, "application/json").header(OPENAI_BETA, "assistants=v1").asJson();

    if (response.getStatus() == 200) {
      return response.getBody().getObject().getString("id");
    } else {
      throw new Exception("Failed to create thread: " + response.getBody().getObject().toString());
    }
  }

  public String executeThread(String threadId, String assistantId) throws Exception {
    HttpResponse<JsonNode> response = Unirest.post(BASE_URL + THREADS + threadId + "/runs")
        .header(Authorization_S, Authorization_S1).header(CONTENT_TYPE, "application/json")
        .header(OPENAI_BETA, ASSISTANT_V1)
        .body(String.format("{\"assistant_id\": \"%s\"}", assistantId)).asJson();

    if (response.getStatus() != 200) {
      throw new Exception("Failed to execute thread: " + response.getBody().getObject().toString());
    }

    return response.getBody().getObject().getString("id");
  }

  public boolean checkCompletion(String threadId) throws Exception {
    HttpResponse<JsonNode> response = Unirest.get(BASE_URL + THREADS + threadId + "/runs")
        .header(Authorization_S, Authorization_S1).header(OPENAI_BETA, ASSISTANT_V1).asJson();

    if (response.getStatus() == 200) {
      String status =
          response.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("status");
      return "completed".equals(status);
    } else {
      throw new Exception(
          "Failed to check completion: " + response.getBody().getObject().toString());
    }
  }

  public String getMessages(String threadId) throws Exception {
    HttpResponse<JsonNode> response = Unirest.get(BASE_URL + THREADS + threadId + "/messages")
        .header(Authorization_S, Authorization_S1).header(OPENAI_BETA, ASSISTANT_V1).asJson();

    if (response.getStatus() == 200) {
      return response.getBody().getObject().getJSONArray("data").getJSONObject(0)
          .getJSONArray("content").getJSONObject(0).getJSONObject("text").getString("value");
    } else {
      throw new Exception(
          "Failed to retrieve messages: " + response.getBody().getObject().toString());
    }
  }
}
