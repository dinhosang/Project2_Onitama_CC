package com.codeclan.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.models.Game;
import com.codeclan.example.myapplication.models.cards.Card;
import com.codeclan.example.myapplication.models.squares.Square;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity {

    Game newGame;
    Game loadedGame;

    ConstraintLayout welcomeMenu;
    ConstraintLayout loadMenu;

    Gson gson;

    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        this.newGame        = new Game();
        this.welcomeMenu    = findViewById(R.id.welcomeMenu);
        this.loadMenu       = findViewById(R.id.loadGameMenu);

        this.gson           = new Gson();
        this.sharedPref     = getSharedPreferences(getString(R.string.preference_file_key),
                                            Context.MODE_PRIVATE);

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

        if (game.getActiveFaction().equals(FactionColour.BLUE)){
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

        String emptyGame        = new Game().toString();
        String mostRecentGame   = this.sharedPref.getString("recent game", emptyGame);


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

            this.loadedGame = SaveDataHelper.loadGame(mostRecentGame, 0);

            showWelcomeScreen(this.loadedGame);
        }
    }

    private ArrayList<Game> getAllSavedGamesExceptRecent(){

        ArrayList<Game> savedGames;
        savedGames = SaveDataHelper.getAllSavedGamesExceptRecent(this.getApplicationContext());

        return savedGames;
    }

    public void welcomeScreenButtonOnClick(View view) {
        Button chosenButton = (Button) view;
        String textOnView = chosenButton.getText().toString();

        if (textOnView.equals(getString(R.string.new_game))){
            startNewGame();
        } else if (textOnView.equals(getString(R.string.load_game))){
            loadMenu();
        }

//        else if (textOnView.equals(getString(R.string.quit_game))){
//            finish();
//            System.exit(0);
//        }
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

    private void loadGame() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("game", this.loadedGame);
        startActivity(intent);
    }

    public void toggleUnitSquareSelection(View view) {}
}
