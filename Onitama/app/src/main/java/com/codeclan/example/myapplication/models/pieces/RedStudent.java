package com.codeclan.example.myapplication.models.pieces;

import com.codeclan.example.myapplication.R;
import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.constants.PieceType;

/**
 * Created by user on 27/01/2018.
 */

public class RedStudent extends Piece {

    public RedStudent(){
        super(FactionColour.RED, PieceType.STUDENT, R.drawable.small_rock);
    }

}
