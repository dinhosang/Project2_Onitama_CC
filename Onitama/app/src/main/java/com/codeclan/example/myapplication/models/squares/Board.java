package com.codeclan.example.myapplication.models.squares;

import com.codeclan.example.myapplication.models.pieces.BlueSensei;
import com.codeclan.example.myapplication.models.pieces.BlueStudent;
import com.codeclan.example.myapplication.models.pieces.Piece;
import com.codeclan.example.myapplication.models.pieces.RedSensei;
import com.codeclan.example.myapplication.models.pieces.RedStudent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by user on 25/01/2018.
 */

public class Board {


    private ArrayList<Square> completeBoard;


    public Board(){
        completeBoard = new ArrayList<>();
        createBoard();
        prepareBoardWithUnits();
    }

    private void prepareBoardWithUnits() {
        Piece redStudent    = new RedStudent();
        Piece redSensei     = new RedSensei();
        Piece blueStudent   = new BlueStudent();
        Piece blueSensei    = new BlueSensei();

        for (Square square: this.completeBoard){
            if (square.getYCoord() == 4){
                if (square.getXCoord() == 2){
                    square.addPiece(redSensei);
                } else {
                    square.addPiece(redStudent);
                }
            } else if (square.getYCoord() == 0){
                if (square.getXCoord() == 2){
                    square.addPiece(blueSensei);
                } else {
                    square.addPiece(blueStudent);
                }
            }
        }

    }

    private void createBoard() {
        Square zerozero      = new Square(0, 0);
        Square onezero       = new Square(1, 0);
        Square twozero       = new Square(2, 0);
        Square threezero     = new Square(3, 0);
        Square fourzero      = new Square(4, 0);

        Square zeroone       = new Square(0, 1);
        Square oneone        = new Square(1, 1);
        Square twoone        = new Square(2, 1);
        Square threeone      = new Square(3, 1);
        Square fourone       = new Square(4, 1);

        Square zerotwo       = new Square(0, 2);
        Square onetwo        = new Square(1, 2);
        Square twotwo        = new Square(2, 2);
        Square threetwo      = new Square(3, 2);
        Square fourtwo       = new Square(4, 2);

        Square zerothree     = new Square(0, 3);
        Square onethree      = new Square(1, 3);
        Square twothree      = new Square(2, 3);
        Square threethree    = new Square(3, 3);
        Square fourthree     = new Square(4, 3);

        Square zerofour      = new Square(0, 4);
        Square onefour       = new Square(1, 4);
        Square twofour       = new Square(2, 4);
        Square threefour     = new Square(3, 4);
        Square fourfour      = new Square(4, 4);

        completeBoard.addAll(Arrays.asList(zerofour, onefour, twofour, threefour, fourfour,
                                            zerothree, onethree, twothree, threethree, fourthree,
                                            zerotwo, onetwo, twotwo, threetwo, fourtwo,
                                            zeroone, oneone, twoone, threeone, fourone,
                                            zerozero, onezero, twozero, threezero, fourzero));

    }

    public ArrayList<Square> getCompleteBoard(){
        ArrayList<Square> copyBoard = new ArrayList<>(this.completeBoard);
        return copyBoard;
    }
}
