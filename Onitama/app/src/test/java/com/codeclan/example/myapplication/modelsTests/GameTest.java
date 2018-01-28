package com.codeclan.example.myapplication.modelsTests;

import com.codeclan.example.myapplication.R;
import com.codeclan.example.myapplication.constants.CardName;
import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.models.Coordinate;
import com.codeclan.example.myapplication.models.Game;
import com.codeclan.example.myapplication.models.cards.Card;
import com.codeclan.example.myapplication.models.moves.Down;
import com.codeclan.example.myapplication.models.moves.Left;
import com.codeclan.example.myapplication.models.moves.Right;
import com.codeclan.example.myapplication.models.moves.Up;
import com.codeclan.example.myapplication.models.squares.Board;
import com.codeclan.example.myapplication.models.squares.Square;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by user on 28/01/2018.
 */

public class GameTest {


    Game    game;
    Board   board;
    Card    frog;
    Card    tiger;
    Square  leftmostBlueStartingSquare;
    Square  centreBlueStartingSquare;
    Square  rightmostRedStartingSquare;
    Square  centreRedStartingSquare;
    Square  centreSquare;



    @Before
    public void before(){

        Right right = new Right();
        Down down = new Down();
        Left left = new Left();
        Up up = new Up();


        ArrayList<Coordinate> move1 = new ArrayList<>(Arrays.asList(right, down));
        ArrayList<Coordinate> move2 = new ArrayList<Coordinate>(Arrays.asList(left, left));
        ArrayList<Coordinate> move3 = new ArrayList<>(Arrays.asList(left, up));

        HashMap<Integer, ArrayList<Coordinate>> movesetFrog = new HashMap<>();
        movesetFrog.put(1, move1);
        movesetFrog.put(2, move2);
        movesetFrog.put(3, move3);


        ArrayList<Coordinate> move4 = new ArrayList<Coordinate>(Arrays.asList(up, up));
        ArrayList<Coordinate> move5 = new ArrayList<Coordinate>(Arrays.asList(down));

        HashMap<Integer, ArrayList<Coordinate>> movesetTiger = new HashMap<>();
        movesetTiger.put(1, move4);
        movesetTiger.put(2, move5);


        board   = new Board();
        game    = new Game();

        frog    = new Card(CardName.FROG, FactionColour.RED, R.drawable.eel, movesetFrog);
        tiger   = new Card(CardName.TIGER, FactionColour.BLUE, R.drawable.eel, movesetTiger);

        for (Square square: game.getBoard().getCompleteBoard()){
            if (square.getYCoord() == 0 && square.getXCoord() == 0){
                leftmostBlueStartingSquare = square;
            } else if (square.getYCoord() == 0 && square.getXCoord() == 2){
                centreBlueStartingSquare = square;
            } else if (square.getYCoord() == 4 && square.getXCoord() == 4) {
                rightmostRedStartingSquare = square;
            } else if (square.getYCoord() == 4 && square.getXCoord() == 2) {
                centreRedStartingSquare = square;
            } else if (square.getYCoord() == 2 && square.getXCoord() == 2) {
                centreSquare = square;
            }
        }

    }


    @Test
    public void canGetName(){
        assertEquals(null, game.getName());
        game.setName("Test Game");
        assertEquals("Test Game", game.getName());
    }

    @Test
    public void handsSetupCorrectly(){
        assertEquals(2, game.getRedHand().size());
        assertEquals(2, game.getBlueHand().size());
    }

    @Test
    public void startingPlayerDetermined(){
        if (game.getActiveFaction().equals(FactionColour.RED)){
            assertNotNull(game.getFloatingCardForRed());
            assertEquals(null, game.getFloatingCardForBlue());
        } else {
            assertNotNull(game.getFloatingCardForBlue());
            assertEquals(null, game.getFloatingCardForRed());
        }
    }

    @Test
    public void LegalMoveExistsWhichKeepPieceOnBoard(){
        if (game.getActiveFaction().equals(FactionColour.BLUE)) {
            assertEquals(true, game.checkMovesExistWhichKeepActiveFactionsPieceOnBoard(centreBlueStartingSquare, frog));
            assertEquals(false, game.checkMovesExistWhichKeepActiveFactionsPieceOnBoard(centreRedStartingSquare, frog));
        } else {
            assertEquals(false, game.checkMovesExistWhichKeepActiveFactionsPieceOnBoard(centreBlueStartingSquare, frog));
            assertEquals(true, game.checkMovesExistWhichKeepActiveFactionsPieceOnBoard(centreRedStartingSquare, frog));
        }
    }

    @Test
    public void LegalMoveDoesNotExistWhichWouldKeepPieceOnBoard(){
        assertEquals(false, game.checkMovesExistWhichKeepActiveFactionsPieceOnBoard(rightmostRedStartingSquare, frog));
        assertEquals(false, game.checkMovesExistWhichKeepActiveFactionsPieceOnBoard(centreSquare, frog));
        assertEquals(false, game.checkMovesExistWhichKeepActiveFactionsPieceOnBoard(leftmostBlueStartingSquare, frog));
    }

    @Test
    public void checkPieceCanMoveToSquare(){
        if (game.getActiveFaction().equals(FactionColour.BLUE)) {
            assertEquals(true, game.checkPieceMayMoveToSquare(centreBlueStartingSquare, tiger, centreSquare));
            assertEquals(false, game.checkPieceMayMoveToSquare(centreRedStartingSquare, tiger, centreSquare));
            assertEquals(false, game.checkPieceMayMoveToSquare(centreBlueStartingSquare, tiger, leftmostBlueStartingSquare));
            assertEquals(false, game.checkPieceMayMoveToSquare(centreBlueStartingSquare, frog, leftmostBlueStartingSquare));
        } else {
            assertEquals(true, game.checkPieceMayMoveToSquare(centreRedStartingSquare, tiger, centreSquare));
            assertEquals(false, game.checkPieceMayMoveToSquare(centreBlueStartingSquare, tiger, centreSquare));
            assertEquals(false, game.checkPieceMayMoveToSquare(centreRedStartingSquare, tiger, rightmostRedStartingSquare));
            assertEquals(false, game.checkPieceMayMoveToSquare(centreRedStartingSquare, frog, rightmostRedStartingSquare));
        }
    }

}
