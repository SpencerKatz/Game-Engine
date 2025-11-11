package oogasalad.database;

public class GameSaveData {

  private final String gameSave;
  private final String gameConfiguration;
  private final String configurableStores;
  private final int id;

  public GameSaveData(int id, String gameSave, String gameConfiguration,
      String configurableStores) {
    this.gameSave = gameSave;
    this.gameConfiguration = gameConfiguration;
    this.configurableStores = configurableStores;
    this.id = id;
  }

  public String getGameSave() {
    return gameSave;
  }

  public String getGameConfiguration() {
    return gameConfiguration;
  }

  public String getConfigurableStores() {
    return configurableStores;
  }

  public int getId() {
    return id;
  }
}
