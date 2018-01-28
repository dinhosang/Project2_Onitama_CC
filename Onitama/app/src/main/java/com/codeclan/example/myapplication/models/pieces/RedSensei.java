package com.codeclan.example.myapplication.models.pieces;

import com.codeclan.example.myapplication.R;
import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.constants.PieceType;

/**
 * Created by user on 27/01/2018.
 */

public class RedSensei extends Piece {

    public RedSensei() {
        super(FactionColour.RED, PieceType.SENSEI, R.drawable.red_sensei_body_small_square);
    }

}
