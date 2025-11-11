package oogasalad.model.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.Instant;
import oogasalad.model.api.ReadOnlyItem;

/**
 * Adapts ReadOnlyItem interface fields to concrete Item instances.
 *
 * @author Jason Qiu
 */
public class ReadOnlyItemAdapter extends TypeAdapter<ReadOnlyItem> {

  private final Gson gson =
      new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();

  private final InterfaceAdapter<ReadOnlyItem> interfaceAdapter = new InterfaceAdapter<>();

  @Override
  public void write(JsonWriter out, ReadOnlyItem value) throws IOException {
    if (value == null) {
      out.nullValue(); // Write JSON null if value is null
      return;
    }
    interfaceAdapter.write(out, value, gson);
  }

  @Override
  public ReadOnlyItem read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull(); // Consume the null token
      return null; // Return null to represent absence of an object
    }
    try {
      return interfaceAdapter.read(in, gson);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
