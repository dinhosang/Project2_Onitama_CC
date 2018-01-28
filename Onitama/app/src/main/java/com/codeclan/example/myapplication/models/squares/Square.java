package com.codeclan.example.myapplication.models.squares;

import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.models.Coordinate;
import com.codeclan.example.myapplication.models.pieces.Piece;

/**
 * Created by user on 25/01/2018.
 */

public class Square extends Coordinate {


    private Piece piece;


    public Square(int x, int y){
        super(x, y);
        this.piece = null;
    }

    public boolean containsPiece() {
        if (this.piece == null){
            return false;
        }

        return true;
    }

    public void addPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean containsRedPiece() {
        if (!containsPiece()){
            return false;
        }

        if (this.piece.getPieceColour().equals(FactionColour.RED)){
            return true;
        }

        return false;
    }

    public boolean containsBluePiece() {
        if (!containsPiece()){
            return false;
        }

        if (this.piece.getPieceColour().equals(FactionColour.BLUE)){
            return true;
        }

        return false;
    }

    public Piece removePiece() {
        Piece piece = this.piece;
        this.piece = null;
        return piece;
    }
}
