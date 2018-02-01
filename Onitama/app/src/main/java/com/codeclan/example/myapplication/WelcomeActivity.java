package com.codeclan.example.myapplication;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.helpers.BoardGridAdapter;
import com.codeclan.example.myapplication.helpers.SaveDataHelper;
import com.codeclan.example.myapplication.helpers.SaveGamesListViewAdapter;
import com.codeclan.example.myapplication.models.Game;
import com.codeclan.example.myapplication.models.cards.Card;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class WelcomeActivity extends AppCompatActivity {

    Game newGame;
    Game loadedGame;

    ConstraintLayout welcomeMenu;
    ConstraintLayout loadMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        this.newGame        = new Game();
        this.welcomeMenu    = findViewById(R.id.welcomeMenu);
        this.loadMenu       = findViewById(R.id.loadGameMenu);

        this.welcomeMenu.setAlpha(1);
        this.loadMenu.setAlpha(0);

        this.welcomeMenu.bringToFront();

        showWelcomeScreen(this.newGame);
    }

    private void showWelcomeScreen(Game game){
        BoardGridAdapter    boardGridAdapter;
        GridView            gridView;
        ImageView           blueCardOne;
        ImageView           blueCardTwo;
        ImageView           redCardOne;
        ImageView           redCardTwo;
        ImageView           blueFloatingCard;
        ImageView           redFloatingCard;

        blueCardOne = findViewById(R.id.blueCardOneWelcome);
        blueCardTwo = findViewById(R.id.blueCardTwoWelcome);

        redCardOne  = findViewById(R.id.redCardOneWelcome);
        redCardTwo  = findViewById(R.id.redCardTwoWelcome);

        blueFloatingCard    = findViewById(R.id.blueFloatingCardWelcome);
        redFloatingCard     = findViewById(R.id.redFloatingCardWelcome);

        Card firstBlueCard = game.getBlueHand().get(0);
        Card secondBlueCard = game.getBlueHand().get(1);
        Card firstRedCard = game.getRedHand().get(0);
        Card secondRedCard = game.getRedHand().get(1);

        if ((game.getActiveFaction().equals(FactionColour.BLUE) && game.getWinningFaction() == null)
                || game.getActiveFaction().equals(FactionColour.RED) && game.getWinningFaction() != null) {
            Card floatingCardForBlue = game.getFloatingCardForBlue();
            blueFloatingCard.setImageResource(floatingCardForBlue.getImageBlueViewInt());
            redFloatingCard.setImageResource(0);
        } else  {
            Card floatingCardForRed = game.getFloatingCardForRed();
            redFloatingCard.setImageResource(floatingCardForRed.getImageRedViewInt());
            blueFloatingCard.setImageResource(0);
        }

        blueCardOne.setImageResource(firstBlueCard.getImageBlueViewInt());
        blueCardTwo.setImageResource(secondBlueCard.getImageBlueViewInt());
        redCardOne.setImageResource(firstRedCard.getImageRedViewInt());
        redCardTwo.setImageResource(secondRedCard.getImageRedViewInt());

        int nonActiveCardBorder = R.drawable.non_active_card_player_hand_border;

        blueCardOne.setBackgroundResource(nonActiveCardBorder);
        blueCardTwo.setBackgroundResource(nonActiveCardBorder);
        redCardOne.setBackgroundResource(nonActiveCardBorder);
        redCardTwo.setBackgroundResource(nonActiveCardBorder);

        boardGridAdapter    = new BoardGridAdapter(this, game.getBoard().getCompleteBoard(),
                                                    game.getActiveSquare());

        gridView            = findViewById(R.id.welcomeBoardGridView);
        gridView.setAdapter(boardGridAdapter);
    }

    public void welcomeScreenButtonOnClick(View view) {

        Button chosenButton = (Button) view;
        String textOnView   = chosenButton.getText().toString();

        if (textOnView.equals(getString(R.string.new_game))){

            startNewGame();
        } else if (textOnView.equals(getString(R.string.load_game))){

            loadMenu();
        } else {

            Toast.makeText(this, "Not yet accessible", Toast.LENGTH_SHORT).show();
        }

    }


    private void startNewGame() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("game", this.newGame);

        startActivity(intent);
    }

    private void loadMenu() {

        Button      loadRecentUnsavedGameButton;
        TextView    noLastUnsavedGameTextView;
        ListView    saveGamesListView;

        loadRecentUnsavedGameButton = findViewById(R.id.loadLastUnsavedGameButton);
        loadRecentUnsavedGameButton.setText(R.string.last_unsaved_game);
        saveGamesListView = findViewById(R.id.saveGamesListView);
        saveGamesListView.setBackgroundResource(R.drawable.onitama_square);

        noLastUnsavedGameTextView = findViewById((R.id.noLastUnsavedGameTextView));

        ArrayList<Game> savedGames = getAllSavedGamesExceptRecent();

        if (!savedGames.isEmpty()){

            SaveGamesListViewAdapter savedGameAdapter = new SaveGamesListViewAdapter(this, savedGames);
            saveGamesListView.setAdapter(savedGameAdapter);
        }

        loadRecentUnsavedGameButton.bringToFront();
        loadRecentUnsavedGameButton.setAlpha(1);
        noLastUnsavedGameTextView.setAlpha(0);


        this.welcomeMenu.setAlpha(0);
        this.loadMenu.setAlpha(1);
        this.loadMenu.bringToFront();

        if (!SaveDataHelper.recentGameExists(this.getApplicationContext())){

            loadRecentUnsavedGameButton = findViewById(R.id.loadLastUnsavedGameButton);
            loadRecentUnsavedGameButton.setText(R.string.no_last_unsaved_game);

            loadRecentUnsavedGameButton.setAlpha(0);
            noLastUnsavedGameTextView.setAlpha(1);
            noLastUnsavedGameTextView.bringToFront();

            showWelcomeScreen(this.newGame);
        } else {

            this.loadedGame = SaveDataHelper.loadGame("recent game",
                                                        this.getApplicationContext(),0);
            showWelcomeScreen(this.loadedGame);
        }
    }

    private ArrayList<Game> getAllSavedGamesExceptRecent(){

        ArrayList<Game> savedGames;
        savedGames = SaveDataHelper.getAllSavedGamesExceptRecent(this.getApplicationContext());

        return savedGames;
    }

    public void loadMenuButtonOnClick(View view) {

        Button chosenButton = (Button) view;
        String textOnView = chosenButton.getText().toString();

        if (textOnView.equals(getString(R.string.return_to_welcome))){

            this.welcomeMenu.setAlpha(1);
            this.loadMenu.setAlpha(0);
            this.welcomeMenu.bringToFront();

            showWelcomeScreen(this.newGame);
        } else if (textOnView.equals(getString(R.string.last_unsaved_game))){

            loadGame();
        }

    }

    public void savedGameItemOnClick(View view){

        Game gameToLoad = (Game) view.getTag();

        this.loadedGame = gameToLoad;

        loadGame();
    }

//    public void queryDeleteSaveOnClick(View view) {
//
//        Game gameSaveToDelete = (Game) view.getTag();
//
//        Button      cancelButton;
//        Button      confirmDeleteButton;
//        TextView    gameToDeleteNameDisplay;
//        TextView    gameToDeleteTurnCountDisplay;
//        TextView    gameToDeleteTimeSavedDisplay;
//
//        final String nameOfGameToDelete;
//        String turnCountOfGameToDelete;
//        String timeSavedOfGameToDelete;
//
//        View queryDeleteGameView;
//
//        AlertDialog.Builder dialogBuilder;
//
//
//        dialogBuilder       = new AlertDialog.Builder(this);
//        nameOfGameToDelete  = gameSaveToDelete.getName();
//        turnCountOfGameToDelete = Integer.toString(gameSaveToDelete.getTurnCount());
//        timeSavedOfGameToDelete = gameSaveToDelete.getDateSaved().toString();
//
//        cancelButton        = findViewById(R.id.deleteGameCancelDeletionButton);
//        confirmDeleteButton = findViewById(R.id.deleteGameConfirmDeletionButton);
//
//        gameToDeleteNameDisplay         = findViewById(R.id.deleteGameNameDisplay);
//        gameToDeleteTurnCountDisplay    = findViewById(R.id.deleteGameTurnCountDisplay);
//        gameToDeleteTimeSavedDisplay    = findViewById(R.id.deleteGameTimeSavedDisplay);
//
//
//        gameToDeleteNameDisplay.setText(nameOfGameToDelete);
//        gameToDeleteTurnCountDisplay.setText(turnCountOfGameToDelete);
//        gameToDeleteTimeSavedDisplay.setText(timeSavedOfGameToDelete);
//
//
//        queryDeleteGameView = getLayoutInflater().inflate(R.layout.query_delete_game_view, null);
//
//
//        dialogBuilder.setView(queryDeleteGameView);
//        final AlertDialog dialog = dialogBuilder.create();
//
//        dialog.show();
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(WelcomeActivity.this, "Deletion Cancelled", Toast.LENGTH_LONG).show();
//                dialog.cancel();
//            }
//        });
//
//        confirmDeleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                SaveDataHelper.clearSaveOfGameNamed(nameOfGameToDelete, WelcomeActivity.this);
//                Toast.makeText(WelcomeActivity.this, "Game successfully deleted", Toast.LENGTH_LONG).show();
//                dialog.cancel();
//            }
//        });
//    }

    private void loadGame() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("game", this.loadedGame);

        startActivity(intent);
    }

    public void toggleUnitSquareSelection(View view) {}
}
