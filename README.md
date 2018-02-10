# Onitama App

An application based on the Onitama board game (https://boardgamegeek.com/boardgame/160477/onitama).

A fuller description may be found through the above link, but to describe it quickly it is a chess-like game where each piece has access to the same moves, and the moves available to a player's pieces are randomly determined each game, and change with each turn.

## Reason for selection

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
