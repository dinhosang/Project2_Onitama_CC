package com.codeclan.example.myapplication.modelsTests.squaresTests;

import com.codeclan.example.myapplication.models.squares.Board;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 28/01/2018.
 */

public class BoardTest {


    Board board;


    @Before
    public void before(){

        board = new Board();

    }


    @Test
    public void canGetSquaresOfBoard(){
        assertEquals(25, board.getCompleteBoard().size());
    }

}
