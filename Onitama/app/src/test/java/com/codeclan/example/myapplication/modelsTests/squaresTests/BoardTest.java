package com.codeclan.example.myapplication.modelsTests.squaresTests;

import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.constants.PieceType;
import com.codeclan.example.myapplication.models.pieces.Piece;
import com.codeclan.example.myapplication.models.squares.Board;
import com.codeclan.example.myapplication.models.squares.Square;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 28/01/2018.
 */

public class BoardTest {


    Board board;
    Piece piece1;
    Piece piece2;
    Boolean piece3Exists;


    @Before
    public void before(){

        board = new Board();

        for (Square square: board.getCompleteBoard()){
            if (square.getYCoord() == 4){
                if (square.getXCoord() == 1){
                    piece1 = square.removePiece();
                }
            } else if (square.getYCoord() == 0){
                if (square.getXCoord() == 2){
                    piece2 = square.removePiece();
                }
            } else if (square.getYCoord() == 2){
                if (square.getXCoord() == 4){
                    piece3Exists = square.containsPiece();
                }
            }
        }

    }


    @Test
    public void canGetSquaresOfBoard(){
        assertEquals(25, board.getCompleteBoard().size());
    }

    @Test
    public void boardIsSetUpCorrectly(){
        assertEquals(FactionColour.RED, piece1.getPieceColour());
        assertEquals(PieceType.STUDENT, piece1.getType());

        assertEquals(PieceType.SENSEI, piece2.getType());
        assertEquals(FactionColour.BLUE, piece2.getPieceColour());

        assertEquals(false, piece3Exists);
    }

}
