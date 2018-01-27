package com.codeclan.example.myapplication.models.pieces;

import com.codeclan.example.myapplication.constants.PieceColour;
import com.codeclan.example.myapplication.constants.PieceType;

/**
 * Created by user on 27/01/2018.
 */

public abstract class Piece {


    int                 imageInt;
    PieceType           type;
    PieceColour         colour;


    public Piece(PieceColour colour, PieceType type, int imageInt) {

        this.colour     = colour;
        this.type       = type;
        this.imageInt   = imageInt;

    }


    public PieceColour getColour() {
        return colour;
    }

    public PieceType getType() {
        return type;
    }

    public int getImageInt() {
        return imageInt;
    }
}
