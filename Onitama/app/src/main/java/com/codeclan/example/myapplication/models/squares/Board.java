package com.codeclan.example.myapplication.models.squares;

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
    }

    private void createBoard() {
        Square zerozero      = new Square(0, 0);
        Square zeroone       = new Square(0, 1);
        Square zerotwo       = new Square(0, 2);
        Square zerothree     = new Square(0, 3);
        Square zerofour      = new Square(0, 4);
        Square onezero       = new Square(1, 0);
        Square oneone        = new Square(1, 1);
        Square onetwo        = new Square(1, 2);
        Square onethree      = new Square(1, 3);
        Square onefour       = new Square(1, 4);
        Square twozero       = new Square(2, 0);
        Square twoone        = new Square(2, 1);
        Square twotwo        = new Square(2, 2);
        Square twothree      = new Square(2, 3);
        Square twofour       = new Square(2, 4);
        Square threezero     = new Square(3, 0);
        Square threeone      = new Square(3, 1);
        Square threetwo      = new Square(3, 2);
        Square threethree    = new Square(3, 3);
        Square threefour     = new Square(3, 4);
        Square fourzero      = new Square(4, 0);
        Square fourone       = new Square(4, 1);
        Square fourtwo       = new Square(4, 2);
        Square fourthree     = new Square(4, 3);
        Square fourfour      = new Square(4, 4);

        completeBoard.addAll(Arrays.asList(zerozero, zeroone, zerotwo, zerothree, zerofour,
                                            onezero, oneone, onetwo, onethree, onefour,
                                            twozero, twoone, twotwo, twothree, twofour,
                                            threezero, threeone, threetwo, threethree, threefour,
                                            fourzero, fourone, fourtwo, fourthree, fourfour));
    }

    public ArrayList<Square> getCompleteBoard(){
        ArrayList<Square> copyBoard = new ArrayList<>(this.completeBoard);
        return copyBoard;
    }
}
