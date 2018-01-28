package com.codeclan.example.myapplication.models;

import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.models.cards.Card;
import com.codeclan.example.myapplication.models.cards.Deck;
import com.codeclan.example.myapplication.models.squares.Board;
import com.codeclan.example.myapplication.models.squares.Square;

import java.util.ArrayList;

/**
 * Created by user on 28/01/2018.
 */

public class Game {
    
    
    private String          name;
    private Board           board;
    private Deck            deck;
    private ArrayList<Card> blueHand;
    private ArrayList<Card> redHand;
    private Card            floatingCardForRed;
    private Card            floatingCardForBlue;
    private FactionColour   activeFaction;
    private FactionColour   gameWinner;
    
    public Game(){
        
        this.name   = null;
        this.board  = new Board();
        this.deck   = new Deck();

        this.blueHand   = new ArrayList<>();
        this.redHand    = new ArrayList<>();

        this.floatingCardForRed     = null;
        this.floatingCardForBlue    = null;
        this.gameWinner             = null;

        setupCards();
        
    }

    private void setupCards() {

        this.redHand.add(this.deck.removeTopCard());
        this.redHand.add(this.deck.removeTopCard());

        this.blueHand.add(this.deck.removeTopCard());
        this.blueHand.add(this.deck.removeTopCard());

        Card floatingCard = this.deck.removeTopCard();

        this.activeFaction = this.deck.removeTopCard().getCardColour();

        if (this.activeFaction.equals(FactionColour.RED)){
            this.floatingCardForRed = floatingCard;
        } else {
            this.floatingCardForBlue = floatingCard;
        }

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getRedHand() {
        ArrayList<Card> copyRedHand = new ArrayList<>(this.redHand);
        return copyRedHand;
    }

    public ArrayList<Card> getBlueHand() {
        ArrayList<Card> copyBlueHand = new ArrayList<>(this.blueHand);
        return copyBlueHand;
    }

    public FactionColour getActiveFaction() {
        return activeFaction;
    }

    public Card getFloatingCardForRed() {
        return floatingCardForRed;
    }

    public Card getFloatingCardForBlue() {
        return floatingCardForBlue;
    }

    public Board getBoard() {
        return board;
    }

    public boolean checkMovesExistWhichKeepActiveFactionsPieceOnBoard(Square square, Card card) {
        if (!square.containsPiece()){
            return false;
        }

        if (this.activeFaction.equals(FactionColour.RED)){
            if (square.containsRedPiece()){
               return checkMovesExistWhichKeepActiveFactionsPieceOnBoardRed(square, card);
            }
        } else {
            if (square.containsBluePiece()){
               return checkMovesExistWhichKeepActiveFactionsPieceOnBoardBlue(square, card);
            }
        }

        return false;
    }

    public boolean checkPieceMayMoveToSquare(Square startSquare, Card card, Square endSquare) {
        if (!checkMovesExistWhichKeepActiveFactionsPieceOnBoard(startSquare, card)){
            return false;
        }

        if (endSquare.containsPiece()) {
            if (startSquare.containsBluePiece() && endSquare.containsBluePiece()) {
                return false;
            } else if (startSquare.containsRedPiece() && endSquare.containsRedPiece()){
                return false;
            }
        }

        if (activeFaction.equals(FactionColour.BLUE)){
            return checkPieceCanReachSquareBlue(startSquare, card, endSquare);
        } else {
            return checkPieceCanReachSquareRed(startSquare, card, endSquare);
        }
    }


    private boolean checkPieceCanReachSquareBlue(Square startSquare, Card card, Square endSquare) {

        int startSquareXCoord;
        int startSquareYCoord;

        int endSquareXCoord = endSquare.getXCoord();
        int endSquareYCoord = endSquare.getYCoord();

        int numberOfMoves = card.getMoveset().keySet().size();

        for (int index = 1; index <= numberOfMoves; ++index){
            ArrayList<Coordinate> currentMovesFromSet = card.getMoveset().get(index);

            startSquareXCoord = startSquare.getXCoord();
            startSquareYCoord = startSquare.getYCoord();

            for (Coordinate move: currentMovesFromSet){

                startSquareXCoord += move.getXCoord();
                startSquareYCoord += move.getYCoord();

            }

            if (startSquareXCoord == endSquareXCoord && startSquareYCoord == endSquareYCoord){
                return true;
            }

        }

        return false;
    }

    private boolean checkPieceCanReachSquareRed(Square startSquare, Card card, Square endSquare) {

        int startSquareXCoord;
        int startSquareYCoord;

        int endSquareXCoord = endSquare.getXCoord();
        int endSquareYCoord = endSquare.getYCoord();

        int numberOfMoves = card.getMoveset().keySet().size();

        for (int index = 1; index <= numberOfMoves; ++index){
            ArrayList<Coordinate> currentMovesFromSet = card.getMoveset().get(index);

            startSquareXCoord = startSquare.getXCoord();
            startSquareYCoord = startSquare.getYCoord();

            for (Coordinate move: currentMovesFromSet){

                startSquareXCoord += (move.getXCoord() * -1);
                startSquareYCoord += (move.getYCoord() * -1);

            }

            if (startSquareXCoord == endSquareXCoord && startSquareYCoord == endSquareYCoord){
                return true;
            }

        }

        return false;

    }

    private boolean checkMovesExistWhichKeepActiveFactionsPieceOnBoardBlue(Square square, Card card) {

        int squareXCoord;
        int squareYCoord;

        int numberOfMoves = card.getMoveset().keySet().size();

        for (int index = 1; index <= numberOfMoves; ++index){
            ArrayList<Coordinate> currentMovesFromSet = card.getMoveset().get(index);

            squareXCoord = square.getXCoord();
            squareYCoord = square.getYCoord();

            for (Coordinate move: currentMovesFromSet){

                squareXCoord += move.getXCoord();
                squareYCoord += move.getYCoord();

            }

            if (squareXCoord <= 4 && squareXCoord >= 0 && squareYCoord <= 4 && squareYCoord >= 0){
                return true;
            }

        }

        return false;
    }

    private boolean checkMovesExistWhichKeepActiveFactionsPieceOnBoardRed(Square square, Card card) {

        int squareXCoord;
        int squareYCoord;

        int numberOfMoves = card.getMoveset().keySet().size();

        for (int index = 1; index <= numberOfMoves; ++index){
            ArrayList<Coordinate> currentMovesFromSet = card.getMoveset().get(index);

            squareXCoord = square.getXCoord();
            squareYCoord = square.getYCoord();

            for (Coordinate move: currentMovesFromSet){

                squareXCoord += (move.getXCoord() * -1);
                squareYCoord += (move.getYCoord() * -1);

            }

            if (squareXCoord <= 4 && squareXCoord >= 0 && squareYCoord <= 4 && squareYCoord >= 0){
                return true;
            }

        }

        return false;
    }

}
