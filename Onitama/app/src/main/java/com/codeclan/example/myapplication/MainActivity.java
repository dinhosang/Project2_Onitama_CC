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

import java.util.ArrayList;
import java.util.Arrays;
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

    ArrayList<ImageView> hands;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.activeGameLayout        = findViewById(R.id.activeGameLayout);
        this.changeTurnLayout        = findViewById(R.id.changeTurnLayout);
        this.resultView              = findViewById(R.id.resultView);

        this.blueCardOne = findViewById(R.id.blueCardOne);
        this.blueCardTwo = findViewById(R.id.blueCardTwo);

        this.redCardOne  = findViewById(R.id.redCardOne);
        this.redCardTwo  = findViewById(R.id.redCardTwo);

        this.hands       = new ArrayList<>(Arrays.asList(this.blueCardOne, this.blueCardTwo,
                                                    this.redCardOne, this.redCardTwo));

        this.blueFloatingCard    = findViewById(R.id.blueFloatingCard);
        this.redFloatingCard     = findViewById(R.id.redFloatingCard);

        this.returnMainMenuButton    = findViewById(R.id.resultViewMainMenuButton);
        this.startNewGameButton      = findViewById(R.id.resultViewNewGameButton);
        this.reviewGameButton        = findViewById(R.id.resultViewReviewGameButton);



        this.gameWon         = false;
        this.reviewingGame   = false;

        prepareResultDisplayView();

        Intent intent = getIntent();
        if (intent.getStringExtra("load") != null){

            String gameName = intent.getStringExtra("load");
            loadGame(gameName, 0);
        } else {

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

        this.game = SaveDataHelper.loadGame(gameName, this,turnToLoad);

        if (!this.reviewingGame) {

            determineBoardState();
        } else {

            this.currentTurnDisplay.setText(String.format(Locale.UK, "%d/%d",
                                                            this.game.getTurnCount(), maxTurn));
            displayGame();
        }
    }

    private void startNewGame(){

        clearSaveOfGameNamed(this.game.getName());

        this.game   = new Game();
        this.gameWon     = false;

        changeGameViewBackToInteractiveMode();

        saveGame();
    }

    private void saveGame(){

        SaveDataHelper.saveGame(this.game, this.getApplicationContext());

        determineBoardState();
    }

    private void determineBoardState() {

        if (this.game.getWinningFaction() != null) {

            this.gameWon = true;

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

            this.blueFloatingCard.setImageResource(floatingCardForBlue.getImageBlueViewInt());
            this.redFloatingCard.setImageResource(0);
        } else {

            Card floatingCardForRed = this.game.getFloatingCardForRed();

            this.redFloatingCard.setImageResource(floatingCardForRed.getImageRedViewInt());
            this.blueFloatingCard.setImageResource(0);
        }

        this.blueCardOne.setImageResource(firstBlueCard.getImageBlueViewInt());
        this.blueCardTwo.setImageResource(secondBlueCard.getImageBlueViewInt());

        this.redCardOne.setImageResource(firstRedCard.getImageRedViewInt());
        this.redCardTwo.setImageResource(secondRedCard.getImageRedViewInt());

        Card activeCard = this.game.getActiveCard();
        int activeCardBorder = R.drawable.active_card_player_hand_border;
        int nonActiveCardBorder = R.drawable.non_active_card_player_hand_border;

        this.blueCardOne.setBackgroundResource(nonActiveCardBorder);
        this.blueCardTwo.setBackgroundResource(nonActiveCardBorder);

        this.redCardOne.setBackgroundResource(nonActiveCardBorder);
        this.redCardTwo.setBackgroundResource(nonActiveCardBorder);

        if (activeCard != null) {

            if (activeCard.equals(firstBlueCard)) {

                this.blueCardOne.setBackgroundResource(activeCardBorder);
            } else if (activeCard.equals(secondBlueCard)) {

                this.blueCardTwo.setBackgroundResource(activeCardBorder);
            } else if (activeCard.equals(firstRedCard)) {

                this.redCardOne.setBackgroundResource(activeCardBorder);
            } else {

                this.redCardTwo.setBackgroundResource(activeCardBorder);
            }
        }

        this.boardGridAdapter = new BoardGridAdapter(this, this.game.getBoard().getCompleteBoard(), this.game.getActiveSquare());

        this.gridView = findViewById(R.id.boardGridView);

        this.gridView.setAdapter(boardGridAdapter);
    }

    private void clearSaveOfGameNamed(String gameNameToClearSaveOf){

        SaveDataHelper.clearSaveOfGameNamed(gameNameToClearSaveOf, this.getApplicationContext());
    }

    public void toggleCardSelectionOnClick(View view) {

        String buttonClicked = view.getTag().toString();

        Card card;

        if (buttonClicked.equals("blue one")) {

            card    = this.game.getBlueHand().get(0);
        } else if (buttonClicked.equals("blue two")) {

            card    = this.game.getBlueHand().get(1);
        } else if (buttonClicked.equals("red one")) {

            card    = this.game.getRedHand().get(0);
        } else {

            card    = this.game.getRedHand().get(1);
        }

        this.game.toggleActiveCardSelection(card);

        determineBoardState();
    }

    public void toggleUnitSquareSelection(View view) {

        Square clickedSquare = (Square) view.getTag();

        this.game.toggleSquareSelection(clickedSquare);

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
            dialogBuilder       = new AlertDialog.Builder(this);


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

                        Game gameToSave = MainActivity.this.game;

                        SaveDataHelper.convertOriginalSaveToNewSave(originalGameName, nameChosen, MainActivity.this);

                        gameToSave.setName(nameChosen);

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

            this.maxTurn = this.game.getTurnCount();

            changeRewindReviewFunctionalityToInteractiveMode();
            displayGame();
        }
    }

    private void resultViewDisplay(){

        displayGame();

        this.resultView.setAlpha(1);
        this.resultView.bringToFront();

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

        this.startingFaction.setText(startingFactionString);
        this.winningFaction.setText(winningFactionString);

        this.victoryType.setText(victoryTypeString);
        this.turnCount.setText(turnCountString);
    }

    public void resultViewButtonOnClick(View view) {

        Button buttonChosen = (Button) view;

        if (buttonChosen.equals(this.returnMainMenuButton)) {

            returnToMainMenu();
        } else if (buttonChosen.equals(this.startNewGameButton)) {

            this.resultView.setAlpha(0);

            this.activeGameLayout.bringToFront();
            changeGameViewBackToInteractiveMode();

            startNewGame();
        } else if (buttonChosen.equals(this.reviewGameButton)){

            this.activeGameLayout.bringToFront();

            this.maxTurn = this.game.getTurnCount();

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
            saveGame();

            this.changeTurnLayout.setAlpha(0);

            changeRewindReviewFunctionalityBackToNonInteractiveMode();
            displayGame();
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

        for (ImageView card: this.hands){

            card.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    private void changeGameViewBackToInteractiveMode(){

        for (ImageView card: this.hands){

            card.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    toggleCardSelectionOnClick(view);
                }
            });
        }
    }

    private void changeRewindReviewFunctionalityToInteractiveMode(){

        Button finaliseRewindReview;

        finaliseRewindReview = findViewById(R.id.acceptTurnButton);

        this.reviewRewindMessage = findViewById(R.id.changeTurnWarningMessageTextView);
        this.currentTurnDisplay  = findViewById(R.id.reviewCurrentTurnDisplay);

        this.reviewingGame = true;

        if (gameWon) {

            this.reviewRewindMessage.setText(getString(R.string.review_game_message));

            finaliseRewindReview.setText(getString(R.string.finish_review));
        } else {

            this.reviewRewindMessage.setText(getString(R.string.change_turn_warning_message));

            finaliseRewindReview.setText(getString(R.string.accept_turn_change));
        }

        this.currentTurnDisplay.setText(String.format(Locale.UK, "%d/%d",
                                                this.game.getTurnCount(), maxTurn));

        changeGameViewToNonInteractiveMode();

        this.changeTurnLayout.setAlpha(1);
        this.changeTurnLayout.bringToFront();
    }

    private void changeRewindReviewFunctionalityBackToNonInteractiveMode(){

        this.reviewingGame = false;

        changeGameViewBackToInteractiveMode();

        this.changeTurnLayout.setAlpha(0);
        this.activeGameLayout.bringToFront();
    }

    private void returnToMainMenu(){

        clearSaveOfGameNamed(this.game.getName());

        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}
