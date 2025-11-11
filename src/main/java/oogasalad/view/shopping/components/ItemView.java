package oogasalad.view.shopping.components;

/**
 * This class is responsible for creating the item view that is used to display the items in the
 * shop.
 */
public class ItemView {

  private final String name;
  private final double worth;
  private final String url;
  private final int num;


  public ItemView(double worth, String url, String name, int num) {
    this.worth = worth;
    this.url = url;
    this.name = name;
    this.num = num;
  }

  public String getName() {
    return name;
  }

  public double getWorth() {
    return worth;
  }

  public String getUrl() {
    return url;
  }

  public int getNum() {
    return num;
  }
}
