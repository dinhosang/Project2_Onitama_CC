package com.codeclan.example.myapplication.models.pieces;

import com.codeclan.example.myapplication.R;
import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.constants.PieceType;

/**
 * Created by user on 27/01/2018.
 */

public class BlueStudent extends Piece {

    public BlueStudent() {

        super(FactionColour.BLUE, PieceType.STUDENT, R.drawable.blue_student_body_small_square, R.drawable.blue_student_body_small_active_square);
    }
}
