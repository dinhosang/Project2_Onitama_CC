package com.codeclan.example.myapplication.modelsTests.squaresTests;

import com.codeclan.example.myapplication.models.pieces.BlueSensei;
import com.codeclan.example.myapplication.models.pieces.Piece;
import com.codeclan.example.myapplication.models.pieces.RedStudent;
import com.codeclan.example.myapplication.models.squares.Square;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 27/01/2018.
 */

public class SquareTest {


    Square  square;
    Square  squareRed;
    Square  squareBlue;
    Piece   redStudent;
    Piece   blueSensei;


    @Before
    public void before(){

        square      = new Square(0,1);
        squareRed   = new Square(0,1);
        squareBlue  = new Square(0,1);

        redStudent  = new RedStudent();
        blueSensei  = new BlueSensei();

        squareRed.addPiece(redStudent);
        squareBlue.addPiece(blueSensei);

    }


    @Test
    public void canCheckSquareStartsEmpty(){
        assertEquals(false, square.containsPiece());
    }

    @Test
    public void canAddPieceToSquare(){
        assertEquals(true, squareRed.containsPiece());
    }

    @Test
    public void canCheckSquareContainsRedPiece(){
        assertEquals(true, squareRed.containsRedPiece());
        assertEquals(false, squareRed.containsBluePiece());
    }

    @Test
    public void canCheckSquareContainsBluePiece(){
        assertEquals(false, squareBlue.containsRedPiece());
        assertEquals(true, squareBlue.containsBluePiece());
    }

    @Test
    public void canGetPiece(){
        assertEquals(true, squareRed.containsPiece());
        Piece piece = squareRed.removePiece();
        assertEquals(false, squareRed.containsPiece());
    }

}
