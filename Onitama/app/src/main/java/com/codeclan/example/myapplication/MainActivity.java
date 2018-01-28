package com.codeclan.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.codeclan.example.myapplication.models.squares.Board;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Board board = new Board();
        BoardGridAdapter boardGridAdapter = new BoardGridAdapter(this, board.getCompleteBoard());

        GridView gridView = findViewById(R.id.boardGridView);
        gridView.setAdapter(boardGridAdapter);
    }
}
