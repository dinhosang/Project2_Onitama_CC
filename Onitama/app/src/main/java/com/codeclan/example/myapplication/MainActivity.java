package com.codeclan.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.codeclan.example.myapplication.models.Game;
import com.codeclan.example.myapplication.models.cards.Card;
import com.codeclan.example.myapplication.models.pieces.Piece;
import com.codeclan.example.myapplication.models.squares.Board;
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

        game        = new Game();

        activeCard  = null;

        chosenStartSquareWithPiece = null;

        blueCardOne = findViewById(R.id.blueCardOne);
        blueCardTwo = findViewById(R.id.blueCardTwo);
        redCardOne  = findViewById(R.id.redCardOne);
        redCardTwo  = findViewById(R.id.redCardTwo);

        showBoardState();

    }

    private void showBoardState() {

        blueCardOne.setImageResource(game.getBlueHand().get(0).getImageBlueViewInt());
        blueCardTwo.setImageResource(game.getBlueHand().get(1).getImageBlueViewInt());
        redCardOne.setImageResource(game.getRedHand().get(0).getImageRedViewInt());
        redCardTwo.setImageResource(game.getRedHand().get(1).getImageRedViewInt());

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
