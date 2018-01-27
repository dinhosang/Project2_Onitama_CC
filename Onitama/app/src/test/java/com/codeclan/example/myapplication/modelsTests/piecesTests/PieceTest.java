package com.codeclan.example.myapplication.modelsTests.piecesTests;

import com.codeclan.example.myapplication.R;
import com.codeclan.example.myapplication.constants.PieceColour;
import com.codeclan.example.myapplication.constants.PieceType;
import com.codeclan.example.myapplication.models.pieces.BlueSensei;
import com.codeclan.example.myapplication.models.pieces.Piece;
import com.codeclan.example.myapplication.models.pieces.RedStudent;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 27/01/2018.
 */

public class PieceTest {


    Piece blueSensei;
    Piece redStudent;


    @Before
    public void before(){

        blueSensei = new BlueSensei();
        redStudent = new RedStudent();

    }

    @Test
    public void canGetColour(){
        assertEquals(PieceColour.RED, redStudent.getColour());
    }

    @Test
    public void canGetType(){
        assertEquals(PieceType.SENSEI, blueSensei.getType());
    }

    @Test
    public void canGetImageInt(){
        assertEquals(R.drawable.small_rock, redStudent.getImageInt());
    }

}
