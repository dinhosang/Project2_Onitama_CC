package com.codeclan.example.myapplication;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
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


import java.util.ArrayList;


public class WelcomeActivity extends AppCompatActivity {

    Game newGame;
    Game gameToLoad;

    Card firstBlueCard;
    Card secondBlueCard;
    Card firstRedCard;
    Card secondRedCard;

    Card floatingCardForBlue;
    Card floatingCardForRed;

    ConstraintLayout welcomeMenu;
    ConstraintLayout loadMenu;

    GridView    gridView;
    ImageView   blueCardOne;
    ImageView   blueCardTwo;
    ImageView   redCardOne;
    ImageView   redCardTwo;
    ImageView   blueFloatingCard;
    ImageView   redFloatingCard;
    BoardGridAdapter    boardGridAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        this.newGame        = new Game();
        this.welcomeMenu    = findViewById(R.id.welcomeMenu);
        this.loadMenu       = findViewById(R.id.loadGameMenu);

        this.blueCardOne = findViewById(R.id.blueCardOneWelcome);
        this.blueCardTwo = findViewById(R.id.blueCardTwoWelcome);

        this.redCardOne  = findViewById(R.id.redCardOneWelcome);
        this.redCardTwo  = findViewById(R.id.redCardTwoWelcome);

        this.blueFloatingCard    = findViewById(R.id.blueFloatingCardWelcome);
        this.redFloatingCard     = findViewById(R.id.redFloatingCardWelcome);

        showWelcomeScreen(this.newGame);
    }

    private void showWelcomeScreen(Game game){

        this.firstBlueCard  = game.getBlueHand().get(0);
        this.secondBlueCard = game.getBlueHand().get(1);
        this.firstRedCard   = game.getRedHand().get(0);
        this.secondRedCard  = game.getRedHand().get(1);

        if ((game.getActiveFaction().equals(FactionColour.BLUE) && game.getWinningFaction() == null)
                || game.getActiveFaction().equals(FactionColour.RED) && game.getWinningFaction() != null) {
            
            this.floatingCardForBlue = game.getFloatingCardForBlue();
            
            this.blueFloatingCard.setImageResource(floatingCardForBlue.getImageBlueViewInt());
            
            this.redFloatingCard.setImageResource(0);
        } else  {
            
            this.floatingCardForRed = game.getFloatingCardForRed();
            
            this.redFloatingCard.setImageResource(floatingCardForRed.getImageRedViewInt());
            
            this.blueFloatingCard.setImageResource(0);
        }

        this.blueCardOne.setImageResource(this.firstBlueCard.getImageBlueViewInt());
        this.blueCardTwo.setImageResource(this.secondBlueCard.getImageBlueViewInt());
        this.redCardOne.setImageResource(this.firstRedCard.getImageRedViewInt());
        this.redCardTwo.setImageResource(this.secondRedCard.getImageRedViewInt());

        int nonActiveCardBorder = R.drawable.non_active_card_player_hand_border;

        this.blueCardOne.setBackgroundResource(nonActiveCardBorder);
        this.blueCardTwo.setBackgroundResource(nonActiveCardBorder);
        this.redCardOne.setBackgroundResource(nonActiveCardBorder);
        this.redCardTwo.setBackgroundResource(nonActiveCardBorder);

        this.boardGridAdapter    = new BoardGridAdapter(this, game.getBoard().getCompleteBoard(),
                                                    game.getActiveSquare());

        this.gridView = findViewById(R.id.welcomeBoardGridView);
        this.gridView.setAdapter(boardGridAdapter);
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

            this.gameToLoad = SaveDataHelper.loadGame("recent game",
                                                        this.getApplicationContext(),0);
            showWelcomeScreen(this.gameToLoad);
        }
    }

    private ArrayList<Game> getAllSavedGamesExceptRecent(){

        ArrayList<Game> savedGames;
        savedGames = SaveDataHelper.getAllSavedGamesExceptRecent(this.getApplicationContext());

        return savedGames;
    }

    public void loadMenuButtonOnClick(View view) {

        Button chosenButton = (Button) view;
        String textOnView   = chosenButton.getText().toString();

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

        this.gameToLoad = (Game) view.getTag();

        loadGame();
    }

    private void loadGame() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("game", this.gameToLoad);

        startActivity(intent);
    }

    public void toggleUnitSquareSelection(View view) {}
}
