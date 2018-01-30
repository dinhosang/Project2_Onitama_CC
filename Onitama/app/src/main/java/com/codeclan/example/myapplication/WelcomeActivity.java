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
import android.widget.TextView;

import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.models.Game;
import com.codeclan.example.myapplication.models.cards.Card;
import com.codeclan.example.myapplication.models.squares.Square;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WelcomeActivity extends AppCompatActivity {

    Game newGame;
    Game loadedGame;

    ConstraintLayout welcomeMenu;
    ConstraintLayout loadMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        this.newGame        =  new Game();
        this.welcomeMenu    = findViewById(R.id.welcomeMenu);
        this.loadMenu       = findViewById(R.id.loadGameMenu);

        showWelcomeScreen(this.newGame);

        this.welcomeMenu.setAlpha(1);
        this.loadMenu.setAlpha(0);

        this.welcomeMenu.bringToFront();

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

    private void startNewGame() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("game", this.newGame);
        startActivity(intent);
    }

    private void loadMenu() {

        Button      loadRecentUnsavedGameButton;
        TextView    noLastUnsavedGameTextView;

        loadRecentUnsavedGameButton = findViewById(R.id.loadLastUnsavedGameButton);
        loadRecentUnsavedGameButton.setText(R.string.last_unsaved_game);

        noLastUnsavedGameTextView = findViewById((R.id.noLastUnsavedGameTextView));

        loadRecentUnsavedGameButton.bringToFront();
        loadRecentUnsavedGameButton.setAlpha(1);
        noLastUnsavedGameTextView.setAlpha(0);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String emptyGame = new Game().toString();
        String mostRecentGame = sharedPref.getString("recent game", emptyGame);

        this.welcomeMenu.setAlpha(0);
        this.loadMenu.setAlpha(1);

        this.loadMenu.bringToFront();

        if (mostRecentGame.equals(emptyGame)){

            loadRecentUnsavedGameButton = findViewById(R.id.loadLastUnsavedGameButton);
            loadRecentUnsavedGameButton.setText(R.string.no_last_unsaved_game);

            loadRecentUnsavedGameButton.setAlpha(0);
            noLastUnsavedGameTextView.setAlpha(1);
            noLastUnsavedGameTextView.bringToFront();

            showWelcomeScreen(this.newGame);
        } else {

            Gson gson = new Gson();
            TypeToken<Game> gameGsonToken = new TypeToken<Game>() {};
            Game loadedGame = gson.fromJson(mostRecentGame, gameGsonToken.getType());
            this.loadedGame = loadedGame;

            showWelcomeScreen(this.loadedGame);
        }
    }

    public void toggleUnitSquareSelection(View view) {

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

    private void loadGame() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("game", this.loadedGame);
        startActivity(intent);
    }

    public void toggleCardSelectionOnClick(View view) {

    }
}
