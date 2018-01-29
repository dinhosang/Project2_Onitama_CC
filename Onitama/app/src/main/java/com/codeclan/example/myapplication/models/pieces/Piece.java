package com.codeclan.example.myapplication.models.pieces;

import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.constants.PieceType;

/**
 * Created by user on 27/01/2018.
 */

public abstract class Piece {


    protected int           imageInt;
    protected int           activeImageInt;
    protected PieceType     type;
    protected FactionColour colour;


    public Piece(FactionColour colour, PieceType type, int imageInt, int activeImageInt) {

        this.colour         = colour;
        this.type           = type;
        this.imageInt       = imageInt;
        this.activeImageInt = activeImageInt;

    }


    public FactionColour getPieceColour() {
        return colour;
    }

    public PieceType getType() {
        return type;
    }

    public int getImageInt() {
        return imageInt;
    }

    public int getActiveImageInt(){
        return activeImageInt;
    }
}
