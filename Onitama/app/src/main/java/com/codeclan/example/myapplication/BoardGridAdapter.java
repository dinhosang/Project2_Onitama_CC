package com.codeclan.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.constants.PieceType;
import com.codeclan.example.myapplication.models.Game;
import com.codeclan.example.myapplication.models.pieces.Piece;
import com.codeclan.example.myapplication.models.squares.Square;

import java.util.ArrayList;

/**
 * Created by user on 25/01/2018.
 */

public class BoardGridAdapter extends ArrayAdapter<Square> {

    private Square activeSquare;


    public BoardGridAdapter(Context context, ArrayList<Square> squares, Square activeSquare) {
        super(context, 0, squares);

        this.activeSquare = activeSquare;
    }


    @Override
    public View getView(int position, View squareView, ViewGroup parent){
        if (squareView == null){
            squareView = LayoutInflater.from(getContext()).inflate(R.layout.square_item_view, parent, false);
        }

        ImageView squareImage = squareView.findViewById(R.id.squareItemView);
        squareImage.setBackgroundResource(R.drawable.non_active_square_border);

        Square currentSquare = getItem(position);


        if (currentSquare.containsPiece()){
            squareImage.setImageResource(currentSquare.getPieceImage());
        }

        if (this.activeSquare != null){
            if (currentSquare.equals(activeSquare)){
                squareImage.setImageResource(currentSquare.getPieceActiveImage());
            }
        }

        squareView.setTag(currentSquare);

        return squareView;

    }
}
