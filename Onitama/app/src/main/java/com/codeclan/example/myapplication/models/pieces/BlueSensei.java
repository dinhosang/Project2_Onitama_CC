package com.codeclan.example.myapplication.models.pieces;

import com.codeclan.example.myapplication.R;
import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.constants.PieceType;

/**
 * Created by user on 27/01/2018.
 */

public class BlueSensei extends Piece {

    public BlueSensei() {

        super(FactionColour.BLUE, PieceType.SENSEI, R.drawable.blue_sensei_body_small_square, R.drawable.blue_sensei_body_small_active_square);
    }
}
