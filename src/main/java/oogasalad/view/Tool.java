package oogasalad.view;

import java.io.File;
import java.net.MalformedURLException;

public class Tool {

  /**
   * Get the url from the fileName, it adds "data/images"
   *
   * @param url
   * @return
   */
  public static String getImagePath(String url) {
    try {
      return (String.valueOf(new File("data/images/" + url).toURI().toURL()));
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

}
