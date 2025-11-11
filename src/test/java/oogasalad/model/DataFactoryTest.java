package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.exception.BadGsonLoadException;
import oogasalad.model.api.exception.BadValueParseException;
import oogasalad.model.api.exception.KeyNotFoundException;
import oogasalad.model.data.DataFactory;
import oogasalad.model.data.Properties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BadProperties extends MockProperties {

  private int randomField = 15;
  private MockProperties properties;
}

class DataFactoryTest {

  private static final String TEST_DATA_DIRECTORY = "test";
  public DataFactory<Properties> propertiesDataFactory;
  public DataFactory<MockProperties> mockPropertiesDataFactory;
  public DataFactory<BadProperties> badPropertiesDataFactory;

  public MockProperties emptyProperties;
  public MockProperties filledProperties;
  public Map<String, String> mockValues =
      Map.of("key1", "val1", "key2", "123", "key3", "false", "key4", "True");
  public Map<String, List<String>> mockListValues =
      Map.of("key1", List.of("val1", "val2", "val3"), "key2", List.of("1", "2", "3"));
  public Map<String, Map<String, String>> mockMapValues =
      Map.of("key1", mockValues, "key2", Map.of("k1", "2"));


  @BeforeAll
  static void start() {
    // Make sure the test directory exists in the data directory
    File testDirectory =
        new File(Paths.get(DataFactory.DATA_DIRECTORY, TEST_DATA_DIRECTORY).toString());
    if (!testDirectory.exists()) {
      testDirectory.mkdirs();
    }
  }

  @BeforeEach
  void setUp() {

    propertiesDataFactory = new DataFactory<>(Properties.class);
    mockPropertiesDataFactory = new DataFactory<>(MockProperties.class);
    badPropertiesDataFactory = new DataFactory<>(BadProperties.class);

    emptyProperties = new MockProperties();

    filledProperties = new MockProperties(mockValues, mockListValues, mockMapValues);
  }

  @Test
  void saveEmpty() {
    String dataFilePath = "emptyPropertiesTest";
    deleteFile(dataFilePath);
    Assertions.assertDoesNotThrow(() -> save(dataFilePath, emptyProperties));
  }

  @Test
  void saveFilled() {
    String dataFilePath = "filledPropertiesTest";
    deleteFile(dataFilePath);
    Assertions.assertDoesNotThrow(() -> save(dataFilePath, filledProperties));
  }

  @Test
  void loadEmpty() throws IOException {
    // GIVEN
    String dataFilePath = "emptyPropertiesTest";
    deleteFile(dataFilePath);
    save(dataFilePath, emptyProperties);

    Properties loaded = load(dataFilePath);
    assertThrows(KeyNotFoundException.class, () -> loaded.getString("key1"));
    assertThrows(KeyNotFoundException.class, () -> loaded.getStringList("key1"));
    assertThrows(KeyNotFoundException.class, () -> loaded.getStringMap("key1"));
  }

  @Test
  void loadFilled() throws IOException {
    // GIVEN
    String dataFilePath = "filledPropertiesTest";
    deleteFile(dataFilePath);
    save(dataFilePath, filledProperties);

    Properties loaded = load(dataFilePath);
    assertEquals(filledProperties.properties, loaded.getCopyOfProperties());
    assertEquals(filledProperties.listProperties, loaded.getCopyOfListProperties());
    assertEquals(filledProperties.mapProperties, loaded.getCopyOfMapProperties());
  }

  @Test
  void badLoad() throws IOException {
    String dataFilePath = "filledPropertiesTest";
    deleteFile(dataFilePath);
    save(dataFilePath, filledProperties);

    BadGsonLoadException exception =
        assertThrows(BadGsonLoadException.class, () -> loadBad(dataFilePath));
    assertEquals(
        addDataFileExtension(Paths.get("data", TEST_DATA_DIRECTORY, dataFilePath).toString()),
        exception.getFilePath());
    assertEquals(BadProperties.class.toString(), exception.getClassName());
  }

  @Test
  void invalidParse() throws IOException {
    String dataFilePath = "filledPropertiesTest";
    deleteFile(dataFilePath);
    save(dataFilePath, filledProperties);
    Properties loaded = load(dataFilePath);

    assertThrows(BadValueParseException.class, () -> loaded.getStringIntegerMap("key1"));
    BadValueParseException exception =
        assertThrows(BadValueParseException.class, () -> loaded.getInteger("key1"));
    assertEquals("Integer", exception.getParseType());
    assertEquals("val1", exception.getBadValue());
    assertThrows(BadValueParseException.class, () -> loaded.getBoolean("key1"));
  }

  @Test
  void propertiesEqual() throws IOException {
    String dataFilePath = "filledPropertiesTest";
    deleteFile(dataFilePath);
    save(dataFilePath, filledProperties);
    Properties loaded = load(dataFilePath);
    assertEquals(mockMapValues, loaded.getMapProperties());
    assertEquals(mockListValues, loaded.getListProperties());
  }

  @Test
  void updateProperties() throws IOException {
    String dataFilePath = "filledPropertiesTest";
    deleteFile(dataFilePath);
    save(dataFilePath, filledProperties);
    Properties loaded = load(dataFilePath);

    assertThrows(BadValueParseException.class, () -> loaded.getBoolean("key1"));
    loaded.update("key1", "true");
    assertTrue(loaded.getBoolean("key1"));
    assertThrows(BadValueParseException.class, () -> loaded.getDouble("key1"));

    assertEquals(mockListValues.get("key1"), loaded.getStringList("key1"));
    loaded.update("key1", List.of());
    assertEquals(List.of(), loaded.getStringList("key1"));

    assertEquals(mockMapValues.get("key1"), loaded.getStringMap("key1"));
    loaded.update("key1", Map.of("k1", "1", "k2", "2", "k3", "3"));
    assertEquals(Map.of("k1", 1, "k2", 2, "k3", 3), loaded.getStringIntegerMap("key1"));
  }

  @Test
  void saveProperties() throws IOException {
    String dataFilePath = "filledPropertiesTest";
    deleteFile(dataFilePath);
    save(dataFilePath, filledProperties);
    Properties loaded = load(dataFilePath);

    loaded.save(dataFilePath);
    loaded = load(dataFilePath);
    assertEquals(mockMapValues, loaded.getMapProperties());
    assertEquals(mockListValues, loaded.getListProperties());

  }

  void save(String dataFilePath, MockProperties properties) throws IOException {
    mockPropertiesDataFactory.save(Paths.get(TEST_DATA_DIRECTORY, dataFilePath).toString(),
        properties);
  }

  void save(String dataFilePath, BadProperties properties) throws IOException {
    badPropertiesDataFactory.save(Paths.get(TEST_DATA_DIRECTORY, dataFilePath).toString(),
        properties);
  }

  Properties load(String dataFilePath) throws IOException {
    return Properties.of(Paths.get(TEST_DATA_DIRECTORY, dataFilePath).toString());
  }

  BadProperties loadBad(String dataFilePath) throws IOException {
    return badPropertiesDataFactory.load(Paths.get(TEST_DATA_DIRECTORY, dataFilePath).toString());
  }

  private void deleteFile(String dataFilePath) {
    File dataFile = new File(DataFactory.DATA_DIRECTORY + TEST_DATA_DIRECTORY,
        addDataFileExtension(dataFilePath));
    if (dataFile.exists()) {
      dataFile.delete();
    }
  }

  // makes sure the filePath ends with the given extension
  private String addDataFileExtension(String filePath) {
    if (filePath.endsWith("." + DataFactory.DATA_FILE_EXTENSION)) {
      return filePath;
    }
    return filePath + "." + DataFactory.DATA_FILE_EXTENSION;
  }
}