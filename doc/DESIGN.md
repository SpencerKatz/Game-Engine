# DESIGN Document for PROJECT_NAME

### Team 3 Farm Game

### Names Jason Qiu, Spencer Katz, Ryan Teachman, Yinuo Guo, Beilong Tang, Saad Hakim

## Team Roles and Responsibilities

* Team Member Jason Qiu
    * Model Api
    * Data saving and loading
    * Debugging throughout entire project

* Team Member Spencer Katz
    * Model Api
    * Interaction and logic

* Team Member Ryan Teachman
    * Authoring environment view and model

* Team Member Yinuo Guo
    * Shop UI
    * Firebase

* Team Member Beilong Tang
    * Playing Page UI
    * Integration with model api

* Saad Hakim
    * Reflection and reformatting
    * Splash Screen UI

## Design Goals

* Able to create new types of properties and easily extend that behavior in the code.

* An extremely configurable game that allows for many different types of games (even totally
  non-farming related).

* The view only depends on the api subpackage inside the model package.

#### How were Specific Features Made Easy to Add

* Create new configurables.
    * All game objects have an id which refers to a particular set of properties.
    * Creating a new game object with a new image is as simple as creating a new set of properties
      and adding it along with an id as the key to the ConfigurablesStore.

* Add new properties for configurables.
    * Just add the new property to the rules or game object.
    * Get the value of that new key in the code with the appropriate `get` function in
      the `Properties` class and implement the functionality of the property (usually very minimal).

* Add cheat keys.
    * Because one structure `Game` holds all the information about a particular game, simply giving
      the cheat key controller/handler `GameKeyHandler` a reference to the Game to hold on to allows
      it to create cheat keys for virtually all functionality that exists in the model.

## High-level Design

#### Core Classes and Abstractions, their Responsibilities and Collaborators

* `Properties`
    * This is the core class for all game-related configuration in the entire project. All
      configurables (game rules, game objects, shop, etc.) are defined using `Properties`.
    * It provides helper functions to parse the String values of each property into different types.

* `DataFactory`
    * This class is the only class that depends on Gson to limit the scope of its influence and make
      debugging much easier. It is used to save and load instances
      of `GameConfiguration`, `GameConfigurablesStore`,
      and `GameState` and other related classes.

* `PlayingPageView`
    * This class is the core class for the playing part of the game. It is responsible for
      displaying
      the data from the model and creating the game loop that keeps updating the game. Its
      collaborators is
      `GameInterface.java`, which is the model api interface and the `ShoppingPageView.java` for
      opening
      the shopping page, and `BagView.java` for displaying items in the bag.
* AbstractSplashScreen.java
  * This abstract is the class that is extended by `StartScreen.java` and 
  `PlayModeSplashScreen.java`. This class abstracts the common functionality of these two
  concrete classes, which in turn handle the user's navigation to `PlayingPageView.java`
  and `EditorScene.java`.
* ButtonActionHandler
  * Collaborating with `SplashUtils.java`, this class allows the implementation of reflection in 
  the action of the buttons allowing the creation of different buttons from csv data files
* `Game`
    * This class contains all the information for a given game config and state and provides all the
      methods that the view (mainly `PlayingPageView`) requires to interact with the game. It also
      provides getters for all ReadOnly interfaces so that the view can update the frontend with
      updated information.

## Assumptions or Simplifications

* No multiple simultaneous games.
    * We ran into a really big blocker with designing where to store
      configurables so that all parts of the program can access them without leaving a backlink (
      limitation due to Gson) and decided that we needed to get this functionality implemented in
      order to progress. The easiest and most direct solution we could think of was to make the
      ConfigurablesStore static and accessible to the entire project, but that came at the cost of
      no multiple simultaneous games.

* Energy consumption is tied to the item being used and does not consider the structure it is being
  used on. For example, using a hoe on diamond and on dirt would use the same energy even though it
  would be nice if it could deduct different amounts.

* No Game Character and No Map Adjustment
    * In our game, there is no game character that moves around. All actions are done by clicking
      with
      the objects. This assumption lowers the burden from the view team. Also, the map has a fixed
      size and not scrollable once specified in the authoring environment. This also lowers the
      burden
      for view team.

* The map cell shape (e.g. square, hexagon, triangle) is fixed to be a square for simplification in
  the entire development process.

## Changes from the Original Plan

* No weather system, which would have been really incredible but also really complicated.

* No bed or interactable game objects in the map to sleep or trade or other.

* Plant Model Changed
    * Initially, we have a plant model that can get the progress of plants. It is deprecated
      now, called `PlantModelInterface.java`. This will help the view to show the progress of the
      plants.
      This is changed base on the difficulty of generalizing it with other game objects in the
      model. Instead, we have each
      plant
      as a unique object and the growing phase of the plants are also game object. In other words,
      we can have
      immature wheat, and wheat as two separate game objects instead of single one.

* No enemies that you have to defend against.

## How to Add New Features

#### Features Designed to be Easy to Add

* Real-time game editing.
    1. Add the Game view to an EditorPanel in the editor view
    2. When the editor saves any configuration, save the game configuration and gamestate.
    3. Immediately load the updated configuration and previous gamestate.

* Load existing game configuration into editor.
    1. Add a file selector when clicking "Create" on the splash screen, just like when clicking "
       New" in the play screen.'
    2. Give the filename to the controller
    3. The controller loads the GameConfiguration and passes it to the editor view to load.

* Resource bundling in the editor and the playing view.
    1. Pass the resource bundle to the constructor of the editor and playing view.
    2. Change all displayed property names and labels to read from the given resource bundle.

* Game statistics tracking.
    1. Create a statistics class which provides methods to increment certain statistics like times
       interacted, items sold, etc..
    2. Add the statistics class to `Game`.
    3. Increment the appropriate statistics when certain methods are called in `Game`.
    4. Provide a getter for a ReadOnly version of the statistics class in `Game` and add it to the
       model API for the view to access.

#### Features Not Yet Done

* Explorable map.

* Loading A Configuration File into the Authoring Environment
    * In our current implementation, the user cannot load a configuration to the Authoring
    Environment. Therefore, every time a user enters the Authoring Environment, they have to 
    start with the default configuration.

* Moving game character in map

* Random terrain generation

* Climate and weather system.