package com.codeclan.example.myapplication;

import android.content.Context;
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

        startGame();

//        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        String mostRecentGame = sharedPref.getString("MyFavourites", new Game().toString()); // UPDATE
//
//        Gson gson = new Gson();
//        TypeToken<Game> gameGsonToken = new TypeToken<Game>(){};
//        Game recentGame = gson.fromJson(mostRecentGame, gameGsonToken.getType());
//
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString(this.game.getName(), gson.toJson(this.game));
//        editor.apply();
    }

    private void startGame(){
        game        = new Game();

        saveGame();
    }

    private void saveGame(){
        SharedPreferences sharedPref    = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();

        editor.putString(this.game.getName(), gson.toJson(this.game));
        editor.apply();

        showBoardState();
    }

    private void showBoardState() {

        if (game.getGameWinner() != null){
            startGame();
        }

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

        Card activeCard = game.getActiveCard();
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

        boardGridAdapter = new BoardGridAdapter(this, game.getBoard().getCompleteBoard(), game.getActiveSquare());
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

        showBoardState();
    }

}
