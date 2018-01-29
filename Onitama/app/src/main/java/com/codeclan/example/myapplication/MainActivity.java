package com.codeclan.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.codeclan.example.myapplication.models.Game;
import com.codeclan.example.myapplication.models.cards.Card;
import com.codeclan.example.myapplication.models.squares.Square;

public class MainActivity extends AppCompatActivity {

    ImageView           blueCardOne;
    ImageView           blueCardTwo;
    ImageView           redCardOne;
    ImageView           redCardTwo;
    Game                game;
    BoardGridAdapter    boardGridAdapter;
    GridView            gridView;
    Card                activeCard;
    Square              chosenStartSquareWithPiece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blueCardOne = findViewById(R.id.blueCardOne);
        blueCardTwo = findViewById(R.id.blueCardTwo);
        redCardOne  = findViewById(R.id.redCardOne);
        redCardTwo  = findViewById(R.id.redCardTwo);

        startGame();

    }

    private void startGame(){
        game        = new Game();

        activeCard  = null;

        chosenStartSquareWithPiece = null;

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

        boardGridAdapter = new BoardGridAdapter(this, game.getBoard().getCompleteBoard());
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
