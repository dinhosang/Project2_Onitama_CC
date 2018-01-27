package com.codeclan.example.myapplication.modelsTests.movesTest;

import com.codeclan.example.myapplication.models.moves.Down;
import com.codeclan.example.myapplication.models.moves.Left;
import com.codeclan.example.myapplication.models.moves.Right;
import com.codeclan.example.myapplication.models.moves.Up;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 27/01/2018.
 */

public class movesTest {


    Up      up;
    Left    left;
    Right   right;


    @Before
    public void before(){

        up      = new Up();
        left    = new Left();
        right   = new Right();

    }


    @Test
    public void upHasXOfZeroAndYOfOne(){
        assertEquals(1, up.getYCoord());
        assertEquals(0, up.getXCoord());
    }

    @Test
    public void leftHasXOfMinusOneAndYOfZero(){
        assertEquals(0, left.getYCoord());
        assertEquals(-1, left.getXCoord());
    }

    @Test
    public void rightHasXOfOneAndYOfZero(){
        assertEquals(0, right.getYCoord());
        assertEquals(1, right.getXCoord());
    }

}
