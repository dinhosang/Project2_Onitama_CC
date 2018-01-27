package com.codeclan.example.myapplication.modelsTests;

import com.codeclan.example.myapplication.models.Coordinate;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 27/01/2018.
 */

public class CoordinateTest {


    Coordinate coord;


    @Before
    public void before(){

        coord = new Coordinate(2,3);

    }


    @Test
    public void canGetXCoord(){
        assertEquals(2, coord.getXCoord());
    }

    @Test
    public void canGetYCoord(){
        assertEquals(3, coord.getYCoord());
    }
}
