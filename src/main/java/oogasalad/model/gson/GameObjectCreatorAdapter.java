package oogasalad.model.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import oogasalad.model.gameobject.gameobjectfactory.GameObjectCreator;

/**
 * [IGNORE THIS FILE]. Adapter class for gson to save files.
 */
public class GameObjectCreatorAdapter extends TypeAdapter<GameObjectCreator> {

  private final Gson gson = new Gson();

  private final InterfaceAdapter<GameObjectCreator> interfaceAdapter = new InterfaceAdapter<>();


  @Override
  public void write(JsonWriter out, GameObjectCreator value) throws IOException {
    interfaceAdapter.write(out, value, gson);
  }

  @Override
  public GameObjectCreator read(JsonReader in) throws IOException {
    try {
      return interfaceAdapter.read(in, gson);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
