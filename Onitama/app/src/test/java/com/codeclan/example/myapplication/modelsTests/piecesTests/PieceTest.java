package com.codeclan.example.myapplication.modelsTests.piecesTests;

import com.codeclan.example.myapplication.R;
import com.codeclan.example.myapplication.constants.PieceColour;
import com.codeclan.example.myapplication.constants.PieceType;
import com.codeclan.example.myapplication.models.pieces.Piece;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 27/01/2018.
 */

public class PieceTest {


    Piece piece;


    @Before
    public void before(){

        piece = new Piece(PieceColour.RED, PieceType.STUDENT, R.drawable.small_rock);

    }

    @Test
    public void canGetColour(){
        assertEquals(PieceColour.RED, piece.getColour());
    }


}
