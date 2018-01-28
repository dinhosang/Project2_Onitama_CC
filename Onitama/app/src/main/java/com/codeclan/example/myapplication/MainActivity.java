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

public class MainActivity extends AppCompatActivity {

    ImageView           blueCardOne;
    ImageView           blueCardTwo;
    ImageView           redCardOne;
    ImageView           redCardTwo;
    Game                game;
    BoardGridAdapter    boardGridAdapter;
    GridView            gridView;
    Card                activeCard;
    Piece               chosenPiece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game        = new Game();

        activeCard  = null;
        chosenPiece = null;

        blueCardOne = findViewById(R.id.blueCardOne);
        blueCardTwo = findViewById(R.id.blueCardTwo);
        redCardOne  = findViewById(R.id.redCardOne);
        redCardTwo  = findViewById(R.id.redCardTwo);

        blueCardOne.setImageResource(R.drawable.elephant_blue_view);
        blueCardTwo.setImageResource(R.drawable.elephant_blue_view);
        redCardOne.setImageResource(R.drawable.elephant_red_view);
        redCardTwo.setImageResource(R.drawable.elephant_red_view);

        showBoardState();

    }

    private void showBoardState() {
        boardGridAdapter = new BoardGridAdapter(this, game.getBoard().getCompleteBoard());
        gridView = findViewById(R.id.boardGridView);
        gridView.setAdapter(boardGridAdapter);
    }


    public void toggleCardSelectionOnClick(View view) {

    }
}
