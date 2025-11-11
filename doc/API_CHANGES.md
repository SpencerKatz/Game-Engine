Jason: Added a static factory method in `ReadOnlyGameTime` to create copies of `GameTime`:

```
/**
  * Read-only copy constructor.
  *
  * @param other the other GameTime to copy.
  */
  static ReadOnlyGameTime copyOf(ReadOnlyGameTime other);
```

Jason: Moved `getSelectedItem` method from `GameInterface` to `ReadOnlyGameState` since that it
where it should be stored.

Jason: Added `save` method to `GameInterface`, because we just forgot to give view a way to save:
```
  /**
   * Saves the current GameState to 'data/gamesaves' with the given filename.
   *
   * @param fileName the name of the file to save to.
   * @throws IOException if writing to the file fails.
  */
  void save(String fileName) throws IOException;
```

* Method changed:
  Several methods within GameWorld, Tile, and GameObject that previously took in Items now take in ReadOnlyItems.
    * Why was the change made?
  Taking in the interface rather than the Item increases flexiblity.
    * Major or Minor (how much they affected your team mate's code)
This had no affect on teammate's code as they can still send in Items to the methods
    * Better or Worse (and why)
Better as it increases flexibility.


* Method changed:
ItemsToAddToInventory
  * Why was the change made?
This method still exists within GameWorld, but it was deprecated in the external model API,
as it does not need to be accessed by the view.
  * Major or Minor (how much they affected your team mate's code)
This was a Minor change. This method was already only being used in the model, so this change had not affect.
  * Better or Worse (and why)
Better since it promotes further model view separation.

* Method changed:
  interact(ReadOnlyItem item, int width, int height, int depth) within GameWorld.
  * Why was this change made?
This was made from a void method to a boolean. The boolean represents whether a valid interaction
occurred. This is important for possibly decrementing the amount of the item that was used on it.
  * Major or Minor
This is a very minor change. No other code must be changed. This just provides more information
that can be used by other classes.
  * Better or Worse.
Better as this allows the classes that use it to know whether an interaction occurred or not.




 
