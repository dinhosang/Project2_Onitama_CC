package com.codeclan.example.myapplication.modelsTests.cardsTests;

import com.codeclan.example.myapplication.R;
import com.codeclan.example.myapplication.constants.CardName;
import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.models.Coordinate;
import com.codeclan.example.myapplication.models.cards.Card;
import com.codeclan.example.myapplication.models.moves.Down;
import com.codeclan.example.myapplication.models.moves.Left;
import com.codeclan.example.myapplication.models.moves.Right;
import com.codeclan.example.myapplication.models.moves.Up;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 23/01/2018.
 */

public class CardTest {


    Card card;


    @Before
    public void before(){

        Down down   = new Down();
        Up up       = new Up();
        Left left   = new Left();
        Right right = new Right();

        ArrayList<Coordinate> move1 = new ArrayList<>(Arrays.asList(right, down));
        // Android Studio does not like the below unless type is re-declared
        ArrayList<Coordinate> move2 = new ArrayList<Coordinate>(Arrays.asList(left, left));
        ArrayList<Coordinate> move3 = new ArrayList<>(Arrays.asList(left, up));

        HashMap<Integer, ArrayList<Coordinate>> movesetFrog = new HashMap<>();
        movesetFrog.put(1, move1);
        movesetFrog.put(2, move2);
        movesetFrog.put(3, move3);

        card = new Card(CardName.FROG, FactionColour.RED, R.drawable.eel, movesetFrog);
    }


    @Test
    public void canGetName(){
        assertEquals(CardName.FROG, card.getName());
    }

    @Test
    public void canGetImageColour(){
        assertEquals(FactionColour.RED, card.getCardColour());
    }

    @Test
    public void canGetImageInt(){
        assertEquals(R.drawable.eel, card.getImageInt());
    }

    @Test
    public void canGetMoveset(){
        assertEquals(3, card.getMoveset().keySet().size());
    }
}
