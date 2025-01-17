# Timeless Odyssey

<p align="center">
<img src="./docs/resources/mockups/logo.png"/>
</p>

Timeless Odyssey is a 2D platformer game where the main character explores a futuristic world full of obstacles through the use of jumping and dashing mechanics, while also collecting stars to increase the score in search for a potential treasure. There are multiple consecutive "levels" with varying difficulty that blend through progressive scenery changes.

## IMPLEMENTED FEATURES

- **Main Menu Screen** - simple menu screen when launching the game, which allows the user to choose between starting the game, accessing the settings or exiting the application.
- **Screen Resizer** - at the start of the game, the code reads the user's screen size and extends the game to that resolution. The resolution can also be manually adjusted in the settings.
- **Level Loader** - class to load levels of written text files with specific characters, allowing to generate tiles and other elements for the different scenes/levels and allows for simple layout editions.
- **Sprite Image Loader** - a class that loads PNG images into the game and can then represent them pixel by pixel on the screen, using Lanterna. This is used for the player, tiles and spikes, stars and even logo images shown in the game.
- **Player Movement** - the player can move (using the arrow keys) at variable speeds, jump (Space bar) and even use a dash (X button) once to the side it's currently facing, gaining an impressive speed boost, but it can only successfully dash once before hitting the ground. Also, the player's movement animations change according to the player's current action and speed.
- **Collectible System** - throughout the levels, the player can find stars, which it should collect to get a better score at the end of the game.
- **Death Condition** - when the player touches one of the spikes, it dies and, after a short animation, returns to the beginning of the current level.
- **Particle System** - the particles give a nice aesthetic experience and flow to the gameplay. Currently, we have snow particles that appear in the foreground of all levels, as well as particles to animate the player's death.
- **Credits** - when finishing the game, the user is presented with an ending screen, where he can visualize the number of stars collected, the number of deaths and the total time of gameplay. Once done, the user can return to the main menu using the Escape button.

## GAME SCREENSHOTS AND MOCKUPS

[comment]: <> (Character Design)

<h4 align="center">
  Main Character Design
</h4>

<p align="center">
<img src="docs/resources/mockups/run.gif">
</p>

[comment]: <> (Menu Design)

<h4 align="center">
  Menu Design
</h4>

<p align="center">
  <img src="docs/resources/screenshots/timeless_odyssey_menu.png"/>
</p>

[comment]: <> (Level Showcase)

<h4 align="center">
  Level and Gameplay Showcase
</h4>

<p align="center">
  <img src="docs/resources/screenshots/level_showcase_1.gif">
  <img src="docs/resources/screenshots/level_showcase_2.gif">
  <img src="docs/resources/screenshots/level_showcase_3.gif">
</p>

[comment]: <> (Credits Screen)

<h4 align="center">
  Closing Credits
</h4>

<p align="center">
  <img src="docs/resources/screenshots/timeless_odyssey_credits.png"/>
</p>


[comment]: <> (General UML Structure)

## UML CLASS STRUCTURES AND OVERVIEW

<p align="center">
  <img src="docs/resources/uml/class/structure.png"/>
</p>