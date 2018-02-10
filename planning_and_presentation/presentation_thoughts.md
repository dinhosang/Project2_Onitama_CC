## Onitama - Board Game

* Time allotted:  10 minutes
* Song Chosen:    

## Categories for discussion

* Reason for choosing brief   - interest in topic -- very short
* What I imagined I could do  - planning & the extensions -- very short
* What I actually did         - app capabilities -- short
* Show app's functionalities  - -- medium
* Discuss interesting code    - perhaps images showing difference refactoring made -- short
* Thoughts for future         - plan Android, diagram updating for dependency mapping -- very short

3 x very short  (3)
2 x short       (4)
1 x medium      (3)

* Reason for choosing brief   - 1 minute
* What I imagined I could do  - 1 minutes
* What I actually did         - 2 minutes
* Show app's functionalities  - 3 minutes
* Discuss interesting code    - 2 minutes
* Thoughts for future         - 1 minute
* Total: 10 minutes!

#Reason:

* wished project interesting useful future

#Imagined:

* not just mvp - learn => fragments, shared preferences, sounds effects, animation
* many things - little time - each one new - time learn each add up -

#Achieved:

* mvp - yes
* ext - yes -- player turn constraints - saving
* other - yes -- rewind & review turn count - autosave recent unsaved - welcome screen display
      load menu display - working with images/gridView/SharedPreferences (resizing gridView)

* ext - no -- highlighting (method exists for foundation) - fragments (hack with bringToFront())
        random AI (methods exist to allow, need new class to hook in) - user login (not a big feature) - animation (did not have time, have seen documentation for gridView anim, will read up)

        * each something that could be done if dedicated time
        * but so many thing, have to choose what matters most to you/your users
        * fragments would have been nice and clean
        * but managed a similar effect with bringToFront() and layered layouts in xml
        * fragments will be learnt another day

#Show app

#Code:

* saving game by turns (including index 0)- auto save - rewind
* game play relies on active card - stop play if cannot toggle - easy to enable review
* board coordinates - moves - card move sets
* game logic for checking can move piece

#Future:
* make another version with animation in gridView
* learn multiThreading to stop the hitching on the emulator, and for good practice
* bring in fragments in place of layering
* bring in sound effects
* ability to design own cards with moves? - give user gridView where they would select the unit location, and then all possible movement squares, then turn that into a non-interactive image on a card with the types of moves worked out by simple addition/subtraction of starting coord and final coord.
* tourney system with user account logins to track victories
* think on how I would create updates without breaking previous users data, like how databases if moving to a new version need a way to travel from the old to the new to preserve user data, if I modified the Game class, then previous saves would break. Maybe not major for a small app I'll mostly use for myself, but if its something others plan to use, and plan to store anything of value on (with time spent on anything being of value to that person), then it's a consideration I'll need to keep mind in the future. Some kind of version number on a class that a helper class can see and then use the appropriate logic to convert and then save as the new type? The idea makes sense to me, but actually putting it into practice would be an interesting endeavour.
*
*
*
*
* online play? how? :o

# Lessons:

* have an updated class diagram as design grows, for viewing dependencies. Got tripped up many a time.
* learning a new thing on your own takes time, implementing it takes time, fixing issues takes time. Plan your goals appropriately with that in mind.

* those were lessons for future for all, for myself also just learnt more about Android Studio. Got a little more proficient at messing around with the xml text/design views.
