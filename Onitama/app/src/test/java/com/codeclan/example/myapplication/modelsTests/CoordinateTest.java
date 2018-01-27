package com.codeclan.example.myapplication.modelsTests;

import com.codeclan.example.myapplication.models.Coordinate;
import com.codeclan.example.myapplication.models.moves.Down;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 27/01/2018.
 */

public class CoordinateTest {


    Coordinate down;


    @Before
    public void before(){

        down = new Down();

    }


    @Test
    public void canGetXCoord(){
        assertEquals(0, down.getXCoord());
    }

    @Test
    public void canGetYCoord(){
        assertEquals(-1, down.getYCoord());
    }
}
