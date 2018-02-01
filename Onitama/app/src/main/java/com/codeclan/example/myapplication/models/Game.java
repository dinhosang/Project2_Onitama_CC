package com.codeclan.example.myapplication.models;

import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.constants.PieceType;
import com.codeclan.example.myapplication.constants.VictoryType;
import com.codeclan.example.myapplication.models.cards.Card;
import com.codeclan.example.myapplication.models.cards.Deck;
import com.codeclan.example.myapplication.models.pieces.Piece;
import com.codeclan.example.myapplication.models.squares.Board;
import com.codeclan.example.myapplication.models.squares.Square;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 28/01/2018.
 */

public class Game implements Serializable {
    
    private String              name;
    private Date                dateSaved;
    private int                 turnCount;

    private Board               board;
    private Deck                deck;

    private ArrayList<Card>     blueHand;
    private ArrayList<Card>     redHand;
    private Card                floatingCardForRed;
    private Card                floatingCardForBlue;
    private Card                activeCard;
    
    private Square              activeSquare;

    private ArrayList<Piece>    capturedBluePieces;
    private ArrayList<Piece>    capturedRedPieces;

    private FactionColour       startingFaction;
    private FactionColour       activeFaction;

    private FactionColour       winningFaction;
    private VictoryType         victoryType;
    
    public Game(){

        this.name       = "recent game";
        this.dateSaved  = Calendar.getInstance().getTime();
        this.turnCount  = 1;

        this.board  = new Board();
        this.deck   = new Deck();


        this.blueHand   = new ArrayList<>();
        this.redHand    = new ArrayList<>();

        this.floatingCardForRed     = null;
        this.floatingCardForBlue    = null;
        this.activeCard             = null;
        this.winningFaction         = null;

        this.capturedBluePieces     = new ArrayList<>();
        this.capturedRedPieces      = new ArrayList<>();

        this.activeSquare = null;

        setupCards();
    }

    private void setupCards() {

        this.redHand.add(this.deck.removeTopCard());
        this.redHand.add(this.deck.removeTopCard());

        this.blueHand.add(this.deck.removeTopCard());
        this.blueHand.add(this.deck.removeTopCard());

        Card floatingCard = this.deck.removeTopCard();

        Card cardToDetermineStartPlayer = this.deck.removeTopCard();
        this.activeFaction = cardToDetermineStartPlayer.getCardColour();

        if (this.activeFaction.equals(FactionColour.RED)){

            this.floatingCardForRed = floatingCard;
        } else {

            this.floatingCardForBlue = floatingCard;
        }

        this.startingFaction = cardToDetermineStartPlayer.getCardColour();
    }


    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Date getDateSaved() {

        return this.dateSaved;
    }

    public void setDateSaved(Date newSaveDate) {

        this.dateSaved = newSaveDate;
    }

    public int getTurnCount(){

        return this.turnCount;
    }

    public String getVictoryType(){

        return this.victoryType.getVictoryValue();
    }

    public String getStartingFaction(){

        return this.startingFaction.getColourValue();
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

    public ArrayList<Piece> getCapturedBluePieces() {

        ArrayList<Piece> copyCapturedBluePieces = new ArrayList<>(this.capturedBluePieces);

        return copyCapturedBluePieces;
    }

    public ArrayList<Piece> getCapturedRedPieces() {

        ArrayList<Piece> copyCapturedRedPieces = new ArrayList<>(this.capturedRedPieces);

        return copyCapturedRedPieces;
    }

    public FactionColour getWinningFaction() {

        return winningFaction;
    }
    
    public void toggleActiveCardSelection(Card potentialActiveCard){

        if (this.activeFaction.equals(FactionColour.BLUE)){

            if (!this.blueHand.contains(potentialActiveCard)){

                return;
            }
        } else {

            if (!this.redHand.contains(potentialActiveCard)){

                return;
            }
        }

        if (this.activeCard == null) {

            this.activeCard = potentialActiveCard;

            return;
        }

        if (this.activeCard.equals(potentialActiveCard)){

            this.activeCard = null;
        } else {

            this.activeCard = potentialActiveCard;
        }

        this.activeSquare = null;
    }
    
    public Card getActiveCard(){

        return this.activeCard;
    }
    
    
    public void toggleSquareSelection(Square selectedSquare){

        if (this.activeCard == null) {

            return;
        }

        
        if (this.activeSquare != null) {
            
            if (this.activeSquare.equals(selectedSquare)){

                this.activeSquare = null;

                return;
            }
            
            if (selectedSquare.containsPiece()
                    && (this.activeFaction.equals(selectedSquare.getPiece().getPieceColour()))) {

                    this.activeSquare = selectedSquare;
            }

            movePiece(this.activeSquare, this.activeCard, selectedSquare);
        } else if (selectedSquare.containsPiece()
                        && (this.activeFaction.equals(selectedSquare.getPiece().getPieceColour()))){

                this.activeSquare = selectedSquare;
        }
    }
    
    public Square getActiveSquare(){

        return this.activeSquare;
    }
    
    // this was mainly for the highlighting of pieces once a card was selected
    // also has unnecessary weight of checking if there is a winning faction etc.
    private boolean checkMovesExistWhichKeepActiveFactionsPieceOnBoard(Square square, Card card) {

        if (this.winningFaction != null){

            return false;
        }

        if (!square.containsPiece()){

            return false;
        }

        if (!cardInActivePlayersHand(card)){

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

    private boolean checkPieceMayMoveToSquare(Square startSquare, Card card, Square endSquare) {

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

    private void movePiece(Square startSquare, Card card, Square endSquare) {

        if (checkPieceMayMoveToSquare(startSquare, card, endSquare)){

            Piece movingPiece = startSquare.removePiece();

            if (endSquare.containsPiece()){

                Piece removedPiece = endSquare.removePiece();
                capturePiece(removedPiece);
            }

            endSquare.addPiece(movingPiece);

            if(movingPiece.getType().equals(PieceType.SENSEI)) {

                setWinnerIfEndSquareIsOpposingGate(endSquare);
            }

            endOfTurnProcedure(card);
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

    private void setWinnerIfEndSquareIsOpposingGate(Square square) {

        int squareXCoord = square.getXCoord();
        int squareYCoord = square.getYCoord();

        if (this.activeFaction.equals(FactionColour.BLUE) && squareXCoord == 2 && squareYCoord == 4){

            this.winningFaction = this.activeFaction;
            this.victoryType    = VictoryType.GATE;
        } else if (this.activeFaction.equals(FactionColour.RED) && squareXCoord == 2 && squareYCoord == 0) {

            this.winningFaction = this.activeFaction;
            this.victoryType    = VictoryType.GATE;
        }
    }

    private void capturePiece(Piece piece) {

        if (piece.getPieceColour().equals(FactionColour.BLUE)){

            this.capturedBluePieces.add(piece);
        } else {

            this.capturedRedPieces.add(piece);
        }

        if (piece.getType().equals(PieceType.SENSEI)){

            this.winningFaction     = this.activeFaction;
            this.victoryType        = VictoryType.SENSEI;
        }
    }

    private boolean cardInActivePlayersHand(Card card) {

        if (this.activeFaction.equals(FactionColour.BLUE)){

            return this.blueHand.contains(card);
        } else {

            return this.redHand.contains(card);
        }
    }

    private void endOfTurnProcedure(Card card) {

        this.activeCard   = null;
        this.activeSquare = null;
        
        if (this.activeFaction.equals(FactionColour.BLUE)){

            this.blueHand.remove(card);
            this.floatingCardForRed = card;

            this.blueHand.add(this.floatingCardForBlue);
            this.floatingCardForBlue = null;
        } else {

            this.redHand.remove(card);
            this.floatingCardForBlue = card;

            this.redHand.add(this.floatingCardForRed);
            this.floatingCardForRed = null;
        }

        if(this.winningFaction == null){

            this.turnCount += 1;

            changeActiveFaction();
        }
    }

    private void changeActiveFaction() {

        if (this.activeFaction.equals(FactionColour.BLUE)){

            this.activeFaction = FactionColour.RED;
        } else {

            this.activeFaction = FactionColour.BLUE;
        }
    }
}
