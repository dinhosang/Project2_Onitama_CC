# Java/Android Project Definitions - Solo Project

## Onitama - Board Game

Goal: Practise Grid View with Android, and potentially Fragments

### MVP
Write an Android app that allows the user to play a solo game of Onitama. The user should be able to start a game and have the board set up correctly. The user should be able to select a piece
and move it while respecting the limits of the movesets available to them on their action cards. Any move which does not fit the moveset or would take the piece beyond the edge of the board should not be allowed.

The action card chosen should become the free card and move to the opponents side of the board for viewing, and the previous free card should enter the users hand of cards. User cards and opponent cards should be visible at all times. The free card should never be available for use, just for viewing until it is officially part of a player's hand.

The game should end when either sensei is captured, or a sensei moves into the opposing colour's gate. A results screen should display and the user allowed to return to the welcome screen.


### Project Extensions

* By selecting an action card and a piece, highlight the allowed movement spaces on the board for that combination.
* Display the users hand of cards, and the free card, in a fragment that can be click to pull up the hand display for a closer look and for selection.
* Have a sense of player turn, so that pieces may only be moved when it is their colour's turn. Turn should revolve after each action played.
* Have a simple AI that picks at random from the available legal moves to allow for player vs ai games
* Allow the current game state to be saved and reloaded at a future point. Data could be preserved in the shared preferences.
* Have the concept of a user account, which can log the games played, the result, and against whom the games were played. This could be displayed in a separate score activity or a highscore activity, with the data being preserved in the shared preferences. Could also store the details of active games for that account
* Allow for two accounts to be "logged in" at a time with the game result being recorded against both.
* Sound effects?


## Memory App

Goal: Create an app for studying and practising memory techniques.

### MVP
Write an android app that allows the user to practise various memory techniques. It should allow the user to choose from a selection of techniques to practise. This list should include:
* The Number Shape System
* The Number Rhyme System
* The Alphabet System

The user should be able to input particular words or phrases to correspond to the relevant entry in the chosen system, this should be saved to a database. They should be able to test their remembrance of those words and phrases, and once that has been set they should be given random words or images to remember using their chosen system. Use some metric to determine how confident a user appears to be with regards to a system (for instance perhaps a system starts as uncertain and upgrades to confident after three correct tests in a row). Perhaps also judge each individual element of a system, and offer a recommendation to change their choice of word for an element if they are having more difficulty correctly remembering using that element over another in the system.

A user should be able to see a history of their practice attempts, detailing their improvements or potential areas to work on.

### Project Extensions

* Elements of any system that appear to be clear in the users mind should be tested less often than elements they have difficulty with or new elements. Use this confidence value detailed earlier to help create the logic to put that into practice.
* Add the ability to save images which are linked to elements of a system to aid in the memory keeping, these should be displayed when reviewing the memory system.
* Add the Major system, allowing for the practise of elements an appropriate amount at a time - ten?
* Have memorisation tasks that persist beyond a session. For instance ask user to remember a list of random objects, show it on screen until they are ready, but then only ask for them to recall a set time later (a few minutes, an hour, a day, several days later). Will need to use LocalDates and the system clock, and more database links.
