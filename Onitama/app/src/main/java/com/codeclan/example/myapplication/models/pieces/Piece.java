package com.codeclan.example.myapplication.models.pieces;

import com.codeclan.example.myapplication.constants.PieceColour;
import com.codeclan.example.myapplication.constants.PieceType;

/**
 * Created by user on 27/01/2018.
 */

public class Piece {


    PieceColour     colour;


    public Piece(PieceColour colour, PieceType type, int imageNumber) {

        this.colour = colour;

    }


    public PieceColour getColour() {
        return colour;
    }
}
