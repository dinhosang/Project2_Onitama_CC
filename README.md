# Onitama App

An application based on the Onitama board game (https://boardgamegeek.com/boardgame/160477/onitama).

A fuller description may be found through the above link, but to describe it quickly it is a chess-like game where each piece has access to the same moves, and the moves available to a player's pieces are randomly determined each game, and change with each turn.

We were given a week (Friday to following Friday) to plan and code the project. I chose to offer my own brief for consideration by the instructors. Due to my concerns that a mobile phone screen may not be large enough to display the game well and have it be easy to interact with from a user perspective, I spent a couple of hours on Thursday evening making a mock-up of the app in Android to be able to show to both myself and to the instructors that it would be viable. Location of mock-up: https://github.com/dinhosang/Project2_MockUp_Android_Onitama

## Reason for selecting this project

Android was something we only had four days of lessons on so I wished to practice it further to solidify my understanding.

The topic of the app itself was something that interested me, and something I had played very often in real life. This meant I would not have to spend time learning how the game works, and could instead concentrate trying to put it into practice in an Android app.

## Tools used

* Java for game logic
* Android for UI
* SharedPreferences for data storage
* JSON & GSON for interacting with SharedPreferences
* Camera for taking game images
* GIMP image editor for re-working game images

## Achievements

* MVP reached - fully working games of Onitama can be played.
* Data preservation - game state can be saved to, and loaded from, SharedPreferences
* Autosaving - game state saved each turn to allow review and rewinding actions, and reloading if game is left in error
* Z-levels in Android - use of bringToFront() and setAlpha to control what layouts are visible and interactive
* Use of gridView to display an interactive board, ensuring it is of the correct size and with the correct amount of space between columns.
* Use of menu and onClickListeners to give users access to features in-game, and prevent unwanted interactions.
* Use of alertDialogs to detail information and request user input
* Use of Toasts for confirmation and updating user

## Thoughts for the future

* Converting data storage from using SharedPreferences to using Room (an abstraction over SqLite) - see https://github.com/dinhosang/practice_room_database_Android for a practice example I wrote with a colleague.
* Looking into gridView animation while moving pieces - https://developer.android.com/reference/android/view/animation/GridLayoutAnimationController.html
* Reading up on Fragments to replace the use of layout hardcoding, allowing for more abstraction and flexibility, and hopefully better performance as not all layouts need to be present, just those who need to be viewed at that time.
* Particularly if moving to Room/SqLite, would require looking into multi-threading. Had issues due to amount of layouts and size of images, but reduced frame skips due to cleaning up the Activities and the logic contained within, reducing number of times a view is interacted with and changed, reducing number of layouts needing to be drawn and keep in memory at one time, and moving as much logic as possible into the Game class. Having calls to a database would likely cause too many issues without the use of the likes of AsyncTask or Threading.
* Think on how you would prevent previous save states breaking if changing either the models, or the way the information is saved. Often a problem that crops up in game development with new patches not always being able to be used with user information saved prior to that patch. Not just an issue in game development. Some kind of converter the first time the software is run after the upgrade to move the save data across to the new format?
* Adjusting layout and view to allow for different values to be presented when screen is rotated, or other devices are used. MainActivity looks correct on emulated Nexus 5/6, but current layout values for GridView do not show correctly on my Samsung S7. It did previously until I updated the values to show better on the emulated devices. Having logic to determine the screen size, position, type of device and then presenting correct images and modifying view values to fit that. Also see if it can be done without hard-coding using relative values or auto resizing of images.
* With the knowledge I have now, plan the Android view side more, and not just the Java logic.
