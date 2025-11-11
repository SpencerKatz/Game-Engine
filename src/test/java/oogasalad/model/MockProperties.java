package oogasalad.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.data.DataFactory;
import oogasalad.model.data.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// MAKE SURE THIS HAS THE SAME FIELDS AS PROPERTIES
class MockProperties {

  private static final DataFactory<MockProperties> FACTORY =
      new DataFactory<>(MockProperties.class);
  private static final Logger LOG = LogManager.getLogger(Properties.class);
  public final Map<String, String> properties;
  public final Map<String, List<String>> listProperties;
  public final Map<String, Map<String, String>> mapProperties;

  public MockProperties() {
    this.properties = new HashMap<>();
    this.listProperties = new HashMap<>();
    this.mapProperties = new HashMap<>();
  }

  public MockProperties(Map<String, String> properties, Map<String, List<String>> listProperties,
      Map<String, Map<String, String>> mapProperties) {
    this.properties = properties;
    this.listProperties = listProperties;
    this.mapProperties = mapProperties;
  }
}


