package com.codeclan.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.models.Game;
import com.codeclan.example.myapplication.models.cards.Card;
import com.codeclan.example.myapplication.models.squares.Square;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    ImageView           blueCardOne;
    ImageView           blueCardTwo;
    ImageView           redCardOne;
    ImageView           redCardTwo;
    ImageView           blueFloatingCard;
    ImageView           redFloatingCard;
    Game                game;
    BoardGridAdapter    boardGridAdapter;
    GridView            gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blueCardOne = findViewById(R.id.blueCardOne);
        blueCardTwo = findViewById(R.id.blueCardTwo);

        redCardOne  = findViewById(R.id.redCardOne);
        redCardTwo  = findViewById(R.id.redCardTwo);

        blueFloatingCard    = findViewById(R.id.blueFloatingCard);
        redFloatingCard     = findViewById(R.id.redFloatingCard);

//        this.game = new Game();

        Intent intent = getIntent();
        if (intent.getStringExtra("load") != null){
            String gameName = intent.getStringExtra("load");
            loadGame(gameName);
        } else {
            // onCreate runs again after reaching end of a method with no other path forward!
            // if below is not in else it tries to run, and sets game to null as nothing is in
            // the intent under the chosen name if the game was loaded.
            this.game = (Game) intent.getSerializableExtra("game");

            saveGame(this.game);
        }

    }

    private void loadGame(String gameName) {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String mostRecentGame = sharedPref.getString(gameName, new Game().toString());

        Gson gson = new Gson();
        TypeToken<Game> gameGsonToken = new TypeToken<Game>(){};
        Game loadedGame = gson.fromJson(mostRecentGame, gameGsonToken.getType());
        this.game = loadedGame;

        saveGame(this.game);
    }

    private void startGame(){
        this.game        = new Game();

        showBoardState();
    }

    private void saveGame(Game gameToPotentiallySave){
        SharedPreferences sharedPref    = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();

        if (gameToPotentiallySave == null){
            editor.remove(this.game.getName());
            editor.apply();
            startGame();
        } else {
            editor.putString(this.game.getName(), gson.toJson(this.game));
            editor.apply();
        }

        showBoardState();
    }

    private void showBoardState() {

        if (this.game.getGameWinner() != null){
            Game blankGame = null;
            saveGame(blankGame);
        }

        Card firstBlueCard = this.game.getBlueHand().get(0);
        Card secondBlueCard = this.game.getBlueHand().get(1);
        Card firstRedCard = this.game.getRedHand().get(0);
        Card secondRedCard = this.game.getRedHand().get(1);

        if (this.game.getActiveFaction().equals(FactionColour.BLUE)){
            Card floatingCardForBlue = this.game.getFloatingCardForBlue();
            blueFloatingCard.setImageResource(floatingCardForBlue.getImageBlueViewInt());
            redFloatingCard.setImageResource(0);
        } else  {
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

        showBoardState();

    }

    public void toggleUnitSquareSelection(View view) {

        Square clickedSquare = (Square) view.getTag();

        game.toggleSquareSelection(clickedSquare);

        saveGame(this.game);
    }

}
