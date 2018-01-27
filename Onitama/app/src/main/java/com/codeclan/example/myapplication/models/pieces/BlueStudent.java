package com.codeclan.example.myapplication.models.pieces;

import com.codeclan.example.myapplication.R;
import com.codeclan.example.myapplication.constants.PieceColour;
import com.codeclan.example.myapplication.constants.PieceType;

/**
 * Created by user on 27/01/2018.
 */

public class BlueStudent extends Piece {

    public BlueStudent() {
        super(PieceColour.BLUE, PieceType.STUDENT, R.drawable.small_rock);
    }

}
