### API for Inventory class

```java
public class InventoryAPI{
//  API for class Data

  /**
   * Removes one instance of parameter from the inventory
   * @param thing
   * @return
   * true if bagItemModel is removed
   * false if no sellItemViews are removed
   */
  public boolean consume (Item thing);
  

/**
 * Decrements durability of object
 * @param thing
 * @return
 * true if bagItemModel durability is decremented
 * false if durability is not decremented
 */
public boolean weaken (Item thing);

}

``` 