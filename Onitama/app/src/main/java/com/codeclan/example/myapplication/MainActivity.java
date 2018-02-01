package com.codeclan.example.myapplication;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.helpers.BoardGridAdapter;
import com.codeclan.example.myapplication.helpers.SaveDataHelper;
import com.codeclan.example.myapplication.models.Game;
import com.codeclan.example.myapplication.models.cards.Card;
import com.codeclan.example.myapplication.models.squares.Square;

import org.w3c.dom.Text;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    ImageView           blueCardOne;
    ImageView           blueCardTwo;
    ImageView           redCardOne;
    ImageView           redCardTwo;
    ImageView           blueFloatingCard;
    ImageView           redFloatingCard;

    TextView            startingFaction;
    TextView            winningFaction;
    TextView            victoryType;
    TextView            turnCount;

    TextView            reviewRewindMessage;
    TextView            currentTurnDisplay;

    ConstraintLayout    activeGameLayout;
    ConstraintLayout    changeTurnLayout;

    BoardGridAdapter    boardGridAdapter;
    GridView            gridView;

    ConstraintLayout    resultView;
    Button              returnMainMenuButton;
    Button              startNewGameButton;
    Button              reviewGameButton;

    Game                game;

    boolean             gameWon;
    boolean             reviewingGame;

    int                 maxTurn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activeGameLayout        = findViewById(R.id.activeGameLayout);
        changeTurnLayout        = findViewById(R.id.changeTurnLayout);
        resultView              = findViewById(R.id.resultView);

        blueCardOne = findViewById(R.id.blueCardOne);
        blueCardTwo = findViewById(R.id.blueCardTwo);

        redCardOne  = findViewById(R.id.redCardOne);
        redCardTwo  = findViewById(R.id.redCardTwo);

        blueFloatingCard    = findViewById(R.id.blueFloatingCard);
        redFloatingCard     = findViewById(R.id.redFloatingCard);

        returnMainMenuButton    = findViewById(R.id.resultViewMainMenuButton);
        startNewGameButton      = findViewById(R.id.resultViewNewGameButton);
        reviewGameButton        = findViewById(R.id.resultViewReviewGameButton);

        gameWon = false;
        reviewingGame = false;

        prepareResultDisplayView();

        Intent intent = getIntent();
        if (intent.getStringExtra("load") != null){

            String gameName = intent.getStringExtra("load");
            loadGame(gameName, 0);

        } else {

            // onCreate runs again from after the if after reaching end of a method with no other path forward!
            // if below is not in else but just free standing code it then tries to run, and sets game to null
            // as nothing is in the intent under the chosen name if the game was loaded.
            this.game = (Game) intent.getSerializableExtra("game");

            saveGame();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main, menu);

        return true;
    }



    public void prepareResultDisplayView(){

        this.turnCount       = findViewById(R.id.resultViewTurnCountTextView);
        this.victoryType     = findViewById(R.id.resultViewVictoryConditionTextView);
        this.winningFaction  = findViewById(R.id.resultViewWinningFactionTextView);
        this.startingFaction = findViewById(R.id.resultViewStartingFactionTextView);
    }

    private void loadGame(String gameName, int turnToLoad) {

        this.game = SaveDataHelper.loadGame(gameName, this.getApplicationContext(),turnToLoad);

        if (!reviewingGame) {

            determineBoardState();
        } else {

            currentTurnDisplay.setText(String.format(Locale.UK, "%d/%d",
                    this.game.getTurnCount(), maxTurn));
            displayGame();
        }
    }

    private void startNewGame(){

        clearSaveOfGameNamed(this.game.getName());

        this.game   = new Game();
        gameWon     = false;

        changeGameViewBackToInteractiveMode();

        saveGame();
    }

    private void saveGame(){

        SaveDataHelper.saveGame(this.game, this.getApplicationContext());

        determineBoardState();
    }

    private void determineBoardState() {

        if (this.game.getWinningFaction() != null) {

            gameWon = true;

            changeGameViewToNonInteractiveMode();

            resultViewDisplay();
        }

        displayGame();
    }

    private void displayGame(){

        FactionColour activeFaction;
        activeFaction = this.game.getActiveFaction();

        Card firstBlueCard  = this.game.getBlueHand().get(0);
        Card secondBlueCard = this.game.getBlueHand().get(1);
        Card firstRedCard   = this.game.getRedHand().get(0);
        Card secondRedCard  = this.game.getRedHand().get(1);

        if ((activeFaction.equals(FactionColour.BLUE) && !gameWon)
                || (activeFaction.equals(FactionColour.RED) && gameWon)) {

            Card floatingCardForBlue = this.game.getFloatingCardForBlue();

            blueFloatingCard.setImageResource(floatingCardForBlue.getImageBlueViewInt());
            redFloatingCard.setImageResource(0);
        } else {

            Card floatingCardForRed = this.game.getFloatingCardForRed();

            redFloatingCard.setImageResource(floatingCardForRed.getImageRedViewInt());
            blueFloatingCard.setImageResource(0);
        }

        blueCardOne.setImageResource(firstBlueCard.getImageBlueViewInt());
        blueCardTwo.setImageResource(secondBlueCard.getImageBlueViewInt());

        redCardOne.setImageResource(firstRedCard.getImageRedViewInt());
        redCardTwo.setImageResource(secondRedCard.getImageRedViewInt());

        Card activeCard = this.game.getActiveCard();
        int activeCardBorder = R.drawable.active_card_player_hand_border;
        int nonActiveCardBorder = R.drawable.non_active_card_player_hand_border;

        blueCardOne.setBackgroundResource(nonActiveCardBorder);
        blueCardTwo.setBackgroundResource(nonActiveCardBorder);

        redCardOne.setBackgroundResource(nonActiveCardBorder);
        redCardTwo.setBackgroundResource(nonActiveCardBorder);

        if (activeCard != null) {
            if (activeCard.equals(firstBlueCard)) {
                blueCardOne.setBackgroundResource(activeCardBorder);
            } else if (activeCard.equals(secondBlueCard)) {
                blueCardTwo.setBackgroundResource(activeCardBorder);
            } else if (activeCard.equals(firstRedCard)) {
                redCardOne.setBackgroundResource(activeCardBorder);
            } else {
                redCardTwo.setBackgroundResource(activeCardBorder);
            }
        }

        boardGridAdapter = new BoardGridAdapter(this, this.game.getBoard().getCompleteBoard(), this.game.getActiveSquare());
        gridView = findViewById(R.id.boardGridView);
        gridView.setAdapter(boardGridAdapter);
    }

    private void clearSaveOfGameNamed(String gameNameToClearSaveOf){

        SaveDataHelper.clearSaveOfGameNamed(gameNameToClearSaveOf, this.getApplicationContext());
    }

    public void toggleCardSelectionOnClick(View view) {
        String buttonClicked = view.getTag().toString();

        Card card;

        if (buttonClicked.equals("blue one")) {
            card    = game.getBlueHand().get(0);
        } else if (buttonClicked.equals("blue two")) {
            card    = game.getBlueHand().get(1);
        } else if (buttonClicked.equals("red one")) {
            card    = game.getRedHand().get(0);
        } else {
            card    = game.getRedHand().get(1);
        }

        game.toggleActiveCardSelection(card);

        determineBoardState();
    }

    public void toggleUnitSquareSelection(View view) {

        Square clickedSquare = (Square) view.getTag();

        game.toggleSquareSelection(clickedSquare);

        saveGame();
    }

    public void onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_save_game) {

            final String        originalGameName;
            originalGameName    = MainActivity.this.game.getName();

            AlertDialog.Builder dialogBuilder;
            final EditText      saveGameNameField;

            TextView            saveGameExplanation;
            Button              cancelButton;
            Button              saveButton;

            View saveGameView   = getLayoutInflater().inflate(R.layout.save_game_view, null);
            dialogBuilder       = new AlertDialog.Builder(MainActivity.this);


            saveGameNameField   = saveGameView.findViewById(R.id.saveGameViewEnterSaveNameEditText);
            cancelButton        = saveGameView.findViewById(R.id.saveGameViewCancelButton);
            saveButton          = saveGameView.findViewById(R.id.saveGameViewSaveButton);
            saveGameExplanation = saveGameView.findViewById(R.id.saveGameViewExplanation);

            if (originalGameName.equals("recent game")){

                final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (190 * scale + 0.5f);

                saveGameExplanation.getLayoutParams().height = pixels;
                saveGameExplanation.setText(R.string.unsaved_autosave_explanation);
            } else {

                final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (120 * scale + 0.5f);

                saveGameExplanation.getLayoutParams().height = pixels;
                saveGameExplanation.setText(R.string.save_explanation);
            }

            dialogBuilder.setView(saveGameView);
            final AlertDialog dialog = dialogBuilder.create();
            dialog.show();


            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });


            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String nameChosen = saveGameNameField.getText().toString();

                    if (nameChosen.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter a name for the saved game", Toast.LENGTH_SHORT).show();
                    } else if (nameChosen.equals("recent game")) {
                        Toast.makeText(MainActivity.this, "Reserved name, please enter another", Toast.LENGTH_LONG).show();
                    } else {

                        MainActivity.this.game.setName(nameChosen);

                        saveGame();

                        if (originalGameName.equals("recent game")) {
                            clearSaveOfGameNamed(originalGameName);
                            Toast.makeText(MainActivity.this, String.format("Game saved as: %s", nameChosen), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, String.format("Game now saved as: %s", nameChosen), Toast.LENGTH_LONG).show();
                        }

                        dialog.cancel();
                    }
                }
            });
        } else if (item.getItemId() == R.id.action_rewind_game){

            maxTurn     = this.game.getTurnCount();

            changeRewindReviewFunctionalityToInteractiveMode();
            displayGame();
        }
    }

    private void resultViewDisplay(){

        displayGame();
        resultView.setAlpha(1);
        resultView.bringToFront();

        String startingFactionHeader    = getString(R.string.result_view_starting_faction_text);
        String startingFactionString    = String.format("%s %s", startingFactionHeader,
                this.game.getStartingFaction());

        String winningFactionHeader     = getString(R.string.result_view_winning_faction_colour);
        String winningFactionString     = String.format("%s %s", winningFactionHeader,
                this.game.getWinningFaction());

        String victoryTypeHeader        = getString(R.string.result_view_victory_condition_achieved);
        String victoryTypeString        = String.format("%s %s", victoryTypeHeader,
                this.game.getVictoryType());

        String turnCountHeader          = getString(R.string.result_view_turn_count);
        String turnCountString          = String.format(Locale.UK, "%s   %d", turnCountHeader,
                this.game.getTurnCount());

        startingFaction.setText(startingFactionString);
        winningFaction.setText(winningFactionString);
        victoryType.setText(victoryTypeString);
        turnCount.setText(turnCountString);
    }

    public void resultViewButtonOnClick(View view) {

        Button buttonChosen = (Button) view;

        if (buttonChosen.equals(this.returnMainMenuButton)) {

            returnToMainMenu();
        } else if (buttonChosen.equals(this.startNewGameButton)) {

            resultView.setAlpha(0);

            activeGameLayout.bringToFront();
            changeGameViewBackToInteractiveMode();

            startNewGame();
        } else if (buttonChosen.equals(this.reviewGameButton)){

            activeGameLayout.bringToFront();

            maxTurn     = this.game.getTurnCount();

            changeRewindReviewFunctionalityToInteractiveMode();
            displayGame();
        }
    }

    public void changeTurnOnClick(View view) {

        String      gameName;

        int         buttonId;
        int         currentTurn;

        Button      buttonChosen;

        gameName        = this.game.getName();
        currentTurn     = this.game.getTurnCount();

        buttonChosen    = (Button) view;
        buttonId        = buttonChosen.getId();

        if (buttonId == R.id.acceptTurnButton && !gameWon){

            clearLaterSavesOfGameNamed(gameName, currentTurn + 1, maxTurn);


            changeTurnLayout.setAlpha(0);
            changeGameViewBackToInteractiveMode();
            changeRewindReviewFunctionalityBackToNonInteractiveMode();
            displayGame();
//        } else if (buttonId == R.id.goToTurnButton && (turnChosen > 0) && (turnChosen < maxTurn)) {
//
//            loadGame(gameName, turnChosen);
//        } else if (buttonId == R.id.goToTurnButton && (turnChosen < 1)) {
//
//            Toast.makeText(this, "Please enter a number greater than 0", Toast.LENGTH_LONG).show();
//        } else if (buttonId == R.id.goToTurnButton && (turnChosen > maxTurn)) {
//
//            int maxTurnPlusOne = maxTurn + 1;
//            String messageToToast = "Please enter a number lower than " + maxTurnPlusOne;
//
//            Toast.makeText(this, messageToToast, Toast.LENGTH_LONG).show();
        } else if (buttonId == R.id.acceptTurnButton && gameWon) {

            returnToMainMenu();
        } else if (buttonId == R.id.startTurnButton && currentTurn != 1) {

            loadGame(gameName, 1);
        } else if (buttonId == R.id.backThreeButton && (currentTurn - 3) > 0) {

            loadGame(gameName, currentTurn - 3);
        } else if (buttonId == R.id.backOneButton && (currentTurn - 1) > 0) {

            loadGame(gameName, currentTurn - 1);
        } else if (buttonId == R.id.forwardOneButton && (maxTurn - currentTurn) > 0) {

            loadGame(gameName, currentTurn + 1);
        } else if (buttonId == R.id.forwardThreeButton  && (maxTurn - currentTurn) > 2) {

            loadGame(gameName, currentTurn + 3);
        } else if (buttonId == R.id.latestTurnButton && (currentTurn != maxTurn)) {

            loadGame(gameName, maxTurn);
        }
    }

    private void clearLaterSavesOfGameNamed(String gameNameToClearSaveOf, int turnToClearFrom, int  turnToClearTo){

        SaveDataHelper.clearLaterSavesOfGameNamed(gameNameToClearSaveOf, this.getApplicationContext(),
                                                    turnToClearFrom, turnToClearTo);

    }

    private void changeGameViewToNonInteractiveMode(){

        this.blueCardOne.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        blueCardTwo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        redCardOne.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        redCardTwo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

    }

    private void changeGameViewBackToInteractiveMode(){

        this.blueCardOne.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggleCardSelectionOnClick(v);
            }
        });

        blueCardTwo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggleCardSelectionOnClick(v);
            }
        });

        redCardOne.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggleCardSelectionOnClick(v);
            }
        });

        redCardTwo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggleCardSelectionOnClick(v);
            }
        });


    }

    private void changeRewindReviewFunctionalityToInteractiveMode(){

        Button finaliseRewindReview;

        finaliseRewindReview = findViewById(R.id.acceptTurnButton);

        reviewRewindMessage = findViewById(R.id.changeTurnWarningMessageTextView);
        currentTurnDisplay  = findViewById(R.id.reviewCurrentTurnDisplay);

        reviewingGame = true;

        if (gameWon) {
            reviewRewindMessage.setText(getString(R.string.review_game_message));
            finaliseRewindReview.setText(getString(R.string.finish_review));
        } else {
            reviewRewindMessage.setText(getString(R.string.change_turn_warning_message));
            finaliseRewindReview.setText(getString(R.string.accept_turn_change));
        }

        currentTurnDisplay.setText(String.format(Locale.UK, "%d/%d",
                                                this.game.getTurnCount(), maxTurn));

        changeTurnLayout.setAlpha(1);
        changeTurnLayout.bringToFront();
    }

    private void changeRewindReviewFunctionalityBackToNonInteractiveMode(){

        reviewingGame = false;

        changeTurnLayout.setAlpha(0);
        activeGameLayout.bringToFront();
    }

    private void returnToMainMenu(){

        clearSaveOfGameNamed(this.game.getName());

        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}
