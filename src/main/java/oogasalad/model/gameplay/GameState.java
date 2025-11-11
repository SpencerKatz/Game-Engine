package oogasalad.model.gameplay;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import oogasalad.model.api.ReadOnlyBag;
import oogasalad.model.api.ReadOnlyGameState;
import oogasalad.model.api.ReadOnlyGameTime;
import oogasalad.model.api.ReadOnlyGameWorld;
import oogasalad.model.api.ReadOnlyItem;
import oogasalad.model.api.ReadOnlyProperties;
import oogasalad.model.api.ReadOnlyShop;
import oogasalad.model.api.exception.BadGsonLoadException;
import oogasalad.model.api.exception.BadValueParseException;
import oogasalad.model.api.exception.KeyNotFoundException;
import oogasalad.model.data.DataFactory;
import oogasalad.model.gameobject.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents the state of a particular game save.
 * <p>
 * This includes the current game world (and any modifications made to it), the current time, stats,
 * etc.
 *
 * @author Jason Qiu
 */
public class GameState implements ReadOnlyGameState {

  // TODO: Externalize this to a configuration file.
  // The path to the gamesaves directory from the data directory.
  public static final String GAMESTATE_DIRECTORY_PATH = "gamesaves";

  /**
   * Initializes a GameState using a config and a world that it makes a copy of
   */
  public GameState(ReadOnlyProperties rules) {
    this(rules, new BuildableTileMap(10, 15, 1));
  }

  /**
   * Initializes a GameState using a config and a world that it makes a copy of
   */
  public GameState(ReadOnlyProperties rules, GameWorld world) {
    try {
      this.gameWorld = BuildableTileMap.copyOf(world);
    } catch (RuntimeException e) {
      LOG.error("Failed to copy initial state from the game configuration");
      throw new RuntimeException(e);
    }
    this.gameTime = new GameTime(1, 8, 0);
    this.maxEnergy = rules.getInteger("energyAmount");
    this.energy = this.maxEnergy;
    this.bag = new Bag();
    try {
      bag.addItems(rules.getStringIntegerMap("startingItems"));
    } catch (KeyNotFoundException | BadValueParseException e) {
      LOG.warn("Couldn't fill the bag with starting items!");
    }
    try {
      List<String> possibleItemStrings = rules.getStringList("shopPossibleItems");
      List<ReadOnlyItem> possibleItems = new ArrayList<>();
      for (String id : possibleItemStrings) {
        possibleItems.add(new Item(id));
      }
      this.shop = new Shop(gameTime, possibleItems, rules.getInteger("shopRotationSize"),
          rules.getInteger("shopRotationTime"));
    } catch (KeyNotFoundException | BadValueParseException e) {
      LOG.error("Couldn't load the shop!");
      throw new RuntimeException(e);
    }
  }

  /**
   * Creates and returns an instance of {@link GameState} from a JSON file.
   *
   * @param dataFilePath the path to the JSON file.
   * @return the created instance of {@link GameState}.
   * @throws BadGsonLoadException if the filePath is unable to be parsed into an instance of
   *                              {@link GameState}
   * @throws IOException          if the filePath could not be opened.
   */
  public static GameState of(String dataFilePath) throws BadGsonLoadException, IOException {
    return FACTORY.load(Paths.get(GAMESTATE_DIRECTORY_PATH, dataFilePath).toString());
  }

  /**
   * Serializes the instance to a JSON file.
   *
   * @param dataFilePath the path to the JSON file with the data directory as the root.
   * @throws IOException if there is an issue writing to the given dataFilePath.
   */
  public void save(String dataFilePath) throws IOException {
    FACTORY.save(Paths.get(GAMESTATE_DIRECTORY_PATH, dataFilePath).toString(), this);
  }

  @Override
  public ReadOnlyGameWorld getGameWorld() {
    return gameWorld;
  }

  @Override
  public ReadOnlyGameTime getGameTime() {
    return gameTime;
  }

  /**
   * Returns the current energy level.
   *
   * @return the current energy level.
   */
  @Override
  public double getEnergy() {
    return energy;
  }

  /**
   * Returns the amount of money currently possessed.
   *
   * @return the amount of money currently possessed.
   */
  @Override
  public int getMoney() {
    return money;
  }

  /**
   * Returns the current shop, which contains items currently being sold.
   *
   * @return the current shop.
   */
  @Override
  public ReadOnlyShop getShop() {
    return shop;
  }

  /**
   * Returns the current bag, which contains items currently held.
   *
   * @return the current bag.
   */
  @Override
  public ReadOnlyBag getBag() {
    return bag;
  }

  /**
   * Returns the selected item, if there is one selected.
   *
   * @return the optional describing the selected item.
   */
  @Override
  public Optional<ReadOnlyItem> getSelectedItem() {
    return Optional.ofNullable(selectedItem);
  }

  public GameWorld getEditableGameWorld() {
    return gameWorld;
  }

  public GameTime getEditableGameTime() {
    return gameTime;
  }

  public Bag getEditableBag() {
    return bag;
  }

  public Shop getEditableShop() {
    return shop;
  }

  public BuildableTileMap getEditableMap() {
    return gameWorld;
  }

  /**
   * Add items from GameWorld to the player's bag.
   */
  public void addItemsToBag() {
    bag.addItems(gameWorld.itemsToAddToInventory());
  }

  /**
   * Selects an item to be active if it is in the bag.
   *
   * @param id the id of the item to select.
   */
  public void selectItem(String id) throws KeyNotFoundException {
    Item item = new Item(id);
    if (!bag.getItems().containsKey(item)) {
      throw new KeyNotFoundException(id);
    }
    selectedItem = item;
  }

  /**
   * Add/subtract from the current amount of money.
   *
   * @param amount amount to add/subtract from the current amount of money.
   */
  public void addMoney(int amount) {
    money += amount;
  }

  /**
   * Restores energy by the given amount up to the max amount.
   *
   * @return the amount of energy restored.
   */
  public double restoreEnergy(double amount) {
    if (amount + energy > maxEnergy) {
      double amountRecovered = maxEnergy - energy;
      energy = maxEnergy;
      return amountRecovered;
    }
    energy += amount;
    return amount;
  }

  private static final DataFactory<GameState> FACTORY = new DataFactory<>(GameState.class);
  private static final Logger LOG = LogManager.getLogger(GameState.class);
  private final BuildableTileMap gameWorld;
  private final GameTime gameTime;
  private ReadOnlyItem selectedItem;
  private double energy;
  private int money;
  private final Shop shop;
  private final Bag bag;
  private final double maxEnergy;
}