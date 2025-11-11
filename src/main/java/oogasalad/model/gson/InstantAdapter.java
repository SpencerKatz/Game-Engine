package oogasalad.model.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.Instant;

/**
 * [IGNORE THIS FILE]. Adapter class for gson to save files.
 */
public class InstantAdapter extends TypeAdapter<Instant> {

  @Override
  public void write(JsonWriter jsonWriter, Instant instant) throws IOException {
    jsonWriter.beginObject();
    jsonWriter.name("data").value(instant.toString());
    jsonWriter.endObject();
  }

  @Override
  public Instant read(JsonReader jsonReader) throws IOException {
    jsonReader.beginObject();
    Instant instant = null;
    while (jsonReader.hasNext()) {
      String name = jsonReader.nextName();
      if (name.equals("data")) {
        instant = Instant.parse(jsonReader.nextString());
      } else {
        jsonReader.skipValue();
      }
    }
    jsonReader.endObject();
    return instant;
  }
}
