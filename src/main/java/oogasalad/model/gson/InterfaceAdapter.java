package oogasalad.model.gson;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/**
 * Adapter for interfaces
 *
 * @param <T>
 */
public class InterfaceAdapter<T> {

  public void write(JsonWriter out, T value, Gson gson) throws IOException {
    out.beginObject();
    out.name("type").value(value.getClass().getName());
    out.name("data").value(gson.toJson(value));
    out.endObject();
  }

  public T read(JsonReader in, Gson gson) throws IOException, ClassNotFoundException {
    in.beginObject();
    T value = null;
    while (in.hasNext()) {
      String name = in.nextName();
      if ("data".equals(name)) {
        String data = in.nextString();
        String type = in.nextName();
        String className = in.nextString();
        value = (T) gson.fromJson(data, Class.forName(className));
      } else if ("type".equals(name)) {
        String className = in.nextString();
        try {
          Class<?> clazz = Class.forName(className);
          String data = in.nextName();
          value = (T) gson.fromJson(in.nextString(), clazz);
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      } else {
        in.skipValue();
      }
    }
    in.endObject();
    return value;
  }


}
