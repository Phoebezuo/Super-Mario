#StickMan

Note: This document has been formatted with Markdown tags. Change the file type to .md and open with
a markdown reader (e.g. IntelliJ) to see a clean output.

##Sprites
Mushroom: https://www.deviantart.com/adventuresmg64gaming/art/Super-Mario-World-Modern-1-UP-Mushroom-Sprite-666837189

##Style
All Java code has been written following the Google Java Style Guide.

##Run
Run the code with `gradle run`

##JSON Format
* "stickmanSize": The size of the StickMan, either "normal" or "large"
* "stickmanPos": A JSON object storing the starting x-coordinate of the StickMan (he starts on the floor)
* "cloudVelocity": The horizontal velocity of clouds
* "levelDimensions": A JSON object storing width, height and floorHeight of the level
* "platforms": A JSON array of x,y coordinates representing locations of platforms
* "mushrooms": A JSON array of x,y coordinates representing locations of mushrooms
* "enemies": A JSON array of enemy objects
    * Each enemy is represented with a JSON object storing x, y coordinates, the sprite path, whether the enemy starts by
      moving left and the strategy used by the enemy (either "dumb", which just goes backwards and forwards, or "follow",
      which moves towards the player's location).
    * In the current set of levels, yellow slimes are set to "follow", while blue and green are set to "dumb"
* "flag": A JSON object storing the x,y coordinates of the final flag

##Different Levels
Level files are stored in levels/. GameManager reads in the list of levels
from levels.json, and uses the first String in the "levelFiles" array as the
first level for the game. To demo loading other levels, change the order of
the array so that other levels can be loaded in first.

##Controls
* Move left: Left Arrow Key
* Move right: Right Arrow Key
* Jump: Up Arrow Key
* Shoot: Space Key

##Collisions
Movement is configured to use a raycasting algorithm. Raycasting is where a line (ray) is projected
from one object in a direction, and determines the distance to the nearest object in its path. This
ensures that regardless of speed, objects will not pass through each other instead of colliding.

##Documentation
All classes have been documented with Javadocs. This can be generated with `gradle javadoc`.