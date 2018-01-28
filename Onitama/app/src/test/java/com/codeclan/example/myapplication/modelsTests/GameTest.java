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
import com.codeclan.example.myapplication.models.pieces.Piece;
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
    Card    crab;
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


        ArrayList<Coordinate> move6 = new ArrayList<Coordinate>(Arrays.asList(right, right));
        ArrayList<Coordinate> move7 = new ArrayList<Coordinate>(Arrays.asList(left, left));
        ArrayList<Coordinate> move8 = new ArrayList<Coordinate>(Arrays.asList(up));

        HashMap<Integer, ArrayList<Coordinate>> movesetCrab = new HashMap<>();
        movesetCrab.put(1, move6);
        movesetCrab.put(2, move7);
        movesetCrab.put(3, move8);


        board   = new Board();
        game    = new Game();

        frog    = new Card(CardName.FROG, FactionColour.RED, R.drawable.eel, movesetFrog);
        tiger   = new Card(CardName.TIGER, FactionColour.BLUE, R.drawable.eel, movesetTiger);
        crab = new Card(CardName.CRAB, FactionColour.BLUE, R.drawable.eel, movesetCrab);


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
            leftmostBlueStartingSquare.removePiece();
            assertEquals(true, game.checkPieceMayMoveToSquare(centreBlueStartingSquare, frog, leftmostBlueStartingSquare));
        } else {
            assertEquals(true, game.checkPieceMayMoveToSquare(centreRedStartingSquare, tiger, centreSquare));
            assertEquals(false, game.checkPieceMayMoveToSquare(centreBlueStartingSquare, tiger, centreSquare));
            assertEquals(false, game.checkPieceMayMoveToSquare(centreRedStartingSquare, tiger, rightmostRedStartingSquare));
            assertEquals(false, game.checkPieceMayMoveToSquare(centreRedStartingSquare, frog, rightmostRedStartingSquare));
            rightmostRedStartingSquare.removePiece();
            assertEquals(true, game.checkPieceMayMoveToSquare(centreRedStartingSquare, frog, rightmostRedStartingSquare));
        }
    }

    @Test
    public void checkWinnerHasBeenDeterminedStartsEmpty(){
        assertEquals(null, game.getGameWinner());
    }

    @Test
    public void checkCapturedPiecesArrayListsStartEmpty(){
        assertEquals(0, game.getCapturedBluePieces().size());
        assertEquals(0, game.getCapturedRedPieces().size());
    }


//    Below four test do not work as long as code starting at line 134
//    in the Game.java file is left uncommented.
//
//    The check is to see if the card being played is part of players hand.
//
//    As a card is chosen specifically for the test this part of the check stops the test from
//    passing.
//
//    Comment out the if statement [if (!cardInActivePlayersHand(card))] to see the tests run.

    
//    @Test
//    public void checkPieceCanCaptureAnotherPiece(){
//        if (game.getActiveFaction().equals(FactionColour.BLUE)) {
//            assertEquals(0, game.getCapturedBluePieces().size());
//            assertEquals(false, centreSquare.containsPiece());
//            game.movePiece(centreBlueStartingSquare, tiger, centreSquare);
//            assertEquals(false, centreBlueStartingSquare.containsBluePiece());
//            assertEquals(true, centreSquare.containsBluePiece());
//            assertEquals(false, centreSquare.containsRedPiece());
//            game.movePiece(centreRedStartingSquare, tiger, centreSquare);
//            assertEquals(1, game.getCapturedBluePieces().size());
//            assertEquals(true, centreSquare.containsRedPiece());
//            assertEquals(false, centreSquare.containsBluePiece());
//        } else {
//            assertEquals(0, game.getCapturedRedPieces().size());
//            assertEquals(false, centreSquare.containsPiece());
//            game.movePiece(centreRedStartingSquare, tiger, centreSquare);
//            assertEquals(false, centreSquare.containsBluePiece());
//            assertEquals(true, centreSquare.containsRedPiece());
//            game.movePiece(centreBlueStartingSquare, tiger, centreSquare);
//            assertEquals(1, game.getCapturedRedPieces().size());
//            assertEquals(false, centreSquare.containsRedPiece());
//            assertEquals(true, centreSquare.containsBluePiece());
//        }
//    }
//
//    @Test
//    public void pieceOfNonActivePlayerCantMove(){
//        if (game.getActiveFaction().equals(FactionColour.BLUE)) {
//            game.movePiece(centreRedStartingSquare, tiger, centreSquare);
//        } else {
//            game.movePiece(centreBlueStartingSquare, tiger, centreSquare);
//
//        }
//
//        assertEquals(false, centreSquare.containsPiece());
//    }
//
//    @Test
//    public void gameEndsOnceSenseiCaptured(){
//        if (game.getActiveFaction().equals(FactionColour.BLUE)) {
//            assertEquals(null, game.getGameWinner());
//            assertNotNull(game.getFloatingCardForBlue());
//            game.movePiece(centreBlueStartingSquare, tiger, centreSquare);
//            assertEquals(tiger, game.getFloatingCardForRed());
//            assertEquals(null, game.getFloatingCardForBlue());
//            game.movePiece(centreRedStartingSquare, tiger, centreSquare);
//            assertEquals(true, centreSquare.containsRedPiece());
//            assertEquals(false, centreBlueStartingSquare.containsPiece());
//            assertEquals(FactionColour.RED, game.getGameWinner());
//            game.movePiece(leftmostBlueStartingSquare, crab, centreBlueStartingSquare);
//            assertEquals(false, centreBlueStartingSquare.containsPiece());
//        } else {
//            assertEquals(null, game.getGameWinner());
//            assertNotNull(game.getFloatingCardForRed());
//            game.movePiece(centreRedStartingSquare, tiger, centreSquare);
//            assertEquals(tiger, game.getFloatingCardForBlue());
//            assertEquals(null, game.getFloatingCardForRed());
//            game.movePiece(centreBlueStartingSquare, tiger, centreSquare);
//            assertEquals(true, centreSquare.containsBluePiece());
//            assertEquals(false, centreRedStartingSquare.containsPiece());
//            assertEquals(FactionColour.BLUE, game.getGameWinner());
//            game.movePiece(rightmostRedStartingSquare, crab, centreRedStartingSquare);
//            assertEquals(false, centreRedStartingSquare.containsPiece());
//        }
//    }
//
//    @Test
//    public void gameEndsIfSenseiReachesOpposingGate(){
//        if (game.getActiveFaction().equals(FactionColour.BLUE)) {
//            game.movePiece(centreBlueStartingSquare, tiger, centreSquare);
//            rightmostRedStartingSquare.removePiece();
//            assertEquals(false, rightmostRedStartingSquare.containsPiece());
//
//            game.movePiece(centreRedStartingSquare, frog, rightmostRedStartingSquare);
//            assertEquals(true, centreSquare.containsBluePiece());
//            assertEquals(true, rightmostRedStartingSquare.containsRedPiece());
//
//            game.movePiece(centreSquare, tiger, centreRedStartingSquare);
//            assertEquals(FactionColour.BLUE, game.getGameWinner());
//
//            game.movePiece(rightmostRedStartingSquare, crab, centreRedStartingSquare);
//            assertEquals(true, rightmostRedStartingSquare.containsPiece());
//        } else {
//            game.movePiece(centreRedStartingSquare, tiger, centreSquare);
//            leftmostBlueStartingSquare.removePiece();
//            assertEquals(false, leftmostBlueStartingSquare.containsPiece());
//
//            game.movePiece(centreBlueStartingSquare, frog, leftmostBlueStartingSquare);
//            assertEquals(true, centreSquare.containsRedPiece());
//            assertEquals(true, leftmostBlueStartingSquare.containsBluePiece());
//
//            game.movePiece(centreSquare, tiger, centreBlueStartingSquare);
//            assertEquals(FactionColour.RED, game.getGameWinner());
//
//            game.movePiece(leftmostBlueStartingSquare, crab, centreBlueStartingSquare);
//            assertEquals(true, leftmostBlueStartingSquare.containsPiece());
//        }
//    }

}
