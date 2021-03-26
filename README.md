# StickMan

An application model for a video game **Super Mario**, which consists of a character on the screen, can move left, right, has the ability to jump and shoot after eating mushrooms. The character will interact with the world, have a floor to stand on, obstacles to interact with (bounce, hit, etc.) and maintain a score.

![iShot2021-03-25 10.50.36](https://i.loli.net/2021/03/25/cjfF8JuXly1nztv.png)

![iShot2021-03-25 10.50.53](https://i.loli.net/2021/03/25/rLBP7kAahGd5pxv.png)

## Usage

Run the code: `gradle run`

Move Left: `Left arrow`

Move Right: `Right arrow`

Jump: `Up arrow`

Shot: `Space`

Save: `S`

Load: `L`

## JSON Format

* `stickmanSize`: The size of the StickMan, either `normal` or `large`
* `stickmanPos`: A JSON object storing the starting x-coordinate of the StickMan (he starts on the floor)
* `cloudVelocity`: The horizontal velocity of clouds
* `levelDimensions`: A JSON object storing width, height and floorHeight of the level
* `platforms`: A JSON array of x, y coordinates representing locations of platforms
* `mushrooms`: A JSON array of x, y coordinates representing locations of mushrooms
* `enemies`: A JSON array of enemy objects, each enemy is represented with a JSON object storing x, y coordinates, the sprite path, whether the enemy starts by moving left and the strategy used by the enemy (either "dumb", which just goes backwards and forwards, or "follow", which moves towards the player's location).
* `flag`: A JSON object storing the x,y coordinates of the final flag

## Features

-   The stickman can be created in two sizes which are defined within JSON configuration file. The size of the stickman is as a string whose options are normal and large. Stickman's starting position is as an x, y coordinate. The stickman must be able to move around the stage (move left and right continuously) and jump. Camera follows Stickman's movement (both horizontal and vertical)
-   Loading different levels
    -   Levels must be loaded from your JSON configuration file which gives instructions for a different assets to be loaded and positioned
    -   Level information must include platforms (that do not move), enemies, mushrooms, a finish flag position and stickman. The stickman must be able to stand on platform objects and must not be able to intersect with any object. The stickman is able to touch the finish flag, at which time the level will finish. 
    -   Moving from level 1 to n (n>=3), then showing 'Winner' when level n is completed as the stickman achieves the goal (finish flag touched).
-   Levels will contain mushrooms which will strengthen the stickman
    -   If the stickman touches the mushroom, the mushroom will disappear and the stickman will now gain the ability to shoot bullets.
    -   Touching multiple mushrooms (beyond the first) will have no effect on the stickman.

-   Levels will contain enemy agents which will harm the stickman 
    -   Enemies have different movement personalities (e.g. some enemies move from left to right, some in the opposite direction, etc)
    -   If the stickman shoots enemies, the enemy will disappear
    -   If the stickman touches enemies in any other way, the stickman will lost a life and start back at the originally configured location

-   Score and Time
    -   The game must record and display the time elapsed for the current level since the start of the level until the stickman has touched the finish flag of this level.
    -   Each level has a target time, which needs to be set in the configuration file. For every 1 second below this time, there is 1 point added into the score; whereas for every 1 second over this time, there is 1 point deducted from the score. There are 100 points added into the score per enemy shot. There are 50 points added into the score per mushroom touched by stickman. 
    -   Stickman now has more than one life displayed, which needs to be set in the configuration file. Losing a life does not impact points. The minimum score is 0. If all lives lost, the game is over with showing 'Game Over'.
    -   The game must record and display on the screen an updating score. You must display the current level's score (initial is 0) on screen during the level (which will visibly be counting down to 0), as well as the total score for all previous levels.

-   Save and Load
    -   The player must be able to save the state of the game (quicksave), then reload that saved game at any point in time (quickload).
    -   The full state of the game must be reverted to the saved state at that time (including Level, Score, and all Entities).
    -   This must be a single saved state that is not written to disk, and each subsequent quicksave overwrites the existing saved state.
    -   Quickload reverts the game state to the single saved version. 

## Design Patterns

### Strategy Design Pattern

<img src='https://i.loli.net/2020/11/08/jaTZgwQNIeqbsO1.png' alt='jaTZgwQNIeqbsO1'/>

The *Strategy Pattern* is implemented in `Slime.java`, `EntityStrategy.java`, `DumbStrategy.java` and `FollowerStrategy.java`.

- `Slime.java` is *Context* of this strategy pattern, which call `think()` in `tick()` to get the `xVelocity` of current slime without knowing the algorithm in `think()`.
- `EntityStrategy.java` is *Abstract Strategy* of this strategy pattern, it declares an interface common to all strategies.
- `DumbStrategy.java` and `FollowerStrategy.java` are *Concrete Strategies* of this strategy pattern to implement different `think()` algorithm.

The *Strategy Pattern* demonstrates *Open-Closed Principle*, as a new strategy can added easily by implements `EnempyStrategy.java` without modified other *Concrete Strategies*. So that the developer do not have to reinvent the wheel and helps to minimize the error of extension after adding new strategy which has different `xVelocity`.

However, the drawback is that the `LevelBuilderImpl.java`, which is the client, needs to be aware of different strategies, so that it is able to initialize in `generateFromFile()`.

### Builder Design Pattern

<img src='https://i.loli.net/2020/11/08/ZeNrTWqMx1H9i8O.png' alt='ZeNrTWqMx1H9i8O'/>

The *Builder Pattern* is implemented in `LevelBuilder.java`, `LevelBuilderImpl.java` and `LevelManager.java`.

- `generateFromFile()` in `LevelBuilderImpl.java` is *Director* of this builder pattern to construct the object in specific order.
- `LevelBuilder.java` is *Abstract Builder* of this builder pattern, it specifics an abstract interface for creating parts of `LevelManager` object.
- `LevelBuilderImpl.java` is *Concrete Builder* of this builder pattern. It constructs and assembles parts of a `LevelManager` object, so that product can be retrived from `build()`.
- `LevelManager.java` is *Product* of this builder pattern, where `LevelBuilderImpl.java`  builds its internal representation.

The *Builder Pattern* gives flexibility to extend by implementing new *Concrete Builder* class, which also demonstrates *Open-Closed Principle*. Since this pattern separate the construction of `LevelManager` object from its representation, so that same construction can create different representations, which enhance code reusability and readability.

However, putting the job of *Director* into `LevelBuilderImpl.java` is a violation of *Single Responsibility Principle*, as current `LevelBuilderImpl.java` has two responsibilities, that is constructing parts of object and knowing specific parts have different order. In addition, `generateFromFile()` is too long, which is over 100 lines, would be better to divided into appropriate methods.

### Prototype Design Pattern

<img src='https://i.loli.net/2020/11/16/zNVIwsi1fFAhurj.png' alt='zNVIwsi1fFAhurj'/>

The *Prototype Design Pattern* is implemented in `Entity` Package.

- `Entity.java` is the *Prototype* of this pattern.
- `Flag.java`, `Mushroom.java`, `Platform.java`, `StickMan.java`, `Bullet.java` and `Slime.java` are the *Concrete Prototype* of this pattern.
- `LevelBuilderImpl.java` is the *Client* of this pattern, which copying the entity whenever there is a need to save all the information in current level.

This design pattern helps to clone all the entities in the current level at run-time, so that they are able to save in `Caretaker` object. Since it hides the concrete prototype classes from the client, it reduces the number of names clients know about. Besides, it also hides the complexity to create objects, but simply uses `deepCopy()` instead.

However, each concrete prototype classes need to implement `deepCopy()`, which some of them need to overload its constructor in order to have more information in cloned objects. This may adds complexity to implement this design pattern.

### Singleton Design Pattern

<img src='https://i.loli.net/2020/11/14/fUFv9mq8TJ2bH15.png' alt='fUFv9mq8TJ2bH15'/>

The *Singleton Design Pattern* is implemented in `ConfigFile.java`.

This provides a global point of access to it, so that between level transition, there is no need to reload the configuration file through out the lifetime of application, this saves running time as well as usage. Besides, it ensure only one and same instance of `ConfigFile` object throughout the application, as the information in `ConfigFile` will be carried over different levels.

However, this pattern may cause code to be *Hight Couping*, which may influence other part of application when there is modification in `ConfigFile.java`. This also cause the code hard to test.

### Memento Design Pattern

<a href="https://sm.ms/image/Jf5WzlvhTPrpFVK" target="_blank"><img src="https://i.loli.net/2020/11/16/Jf5WzlvhTPrpFVK.png" ></a>

The *Memento Design Pattern* is implemented in `Caretaker.java`, `Memento.java` and `Originator.java`.

- `Originator.java` is the *Originator* in this pattern, manipulating `Memento` object by creating or retrieving.
- `Memento.java` is the *Memento* in this pattern, which stores the internal state of `Originator` object.
- `Caretaker.java` is the *Caretaker* in this pattern, which safekeeping the last version of `Memento` objects

This design pattern will capture all the information of current state (including slimes position, floor height, ability to shoot, etc), which is stored in `LevelManager` object. Whenever player calls `save()`, it uses `Originator` object to create a `Memento` object that stored in `Caretaker` object. `Caretaker` object contains the last saved version, so that whenever player calls `load()`, the last saved version will be retrieved by `Originator` and set the current level to be last saved version to satisfied player's required.

However, since the whole level's information is stored in `Memento` object, this design patterns might be expensive, as the information every time to save is quite big.

## Collisions
Movement is configured to use a raycasting algorithm. Raycasting is where a line (ray) is projected from one object in a direction, and determines the distance to the nearest object in its path. This ensures that regardless of speed, objects will not pass through each other instead of colliding.
