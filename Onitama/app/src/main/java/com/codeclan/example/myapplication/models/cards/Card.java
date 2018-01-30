package com.codeclan.example.myapplication.models.cards;

import com.codeclan.example.myapplication.constants.CardName;
import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.models.Coordinate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 23/01/2018.
 */

public class Card implements Serializable {


    private CardName        name;
    private FactionColour   colour;
    private int             imageBlueViewInt;
    private int             imageRedViewInt;
    private HashMap<Integer, ArrayList<Coordinate>> moveset;


    public Card(CardName name, FactionColour colour, int imageBlueViewInt, int imageRedViewInt, HashMap<Integer, ArrayList<Coordinate>> moveset) {

        this.name               = name;
        this.colour             = colour;
        this.imageBlueViewInt   = imageBlueViewInt;
        this.imageRedViewInt    = imageRedViewInt;
        this.moveset            = moveset;

    }

    public CardName getName() {
        return name;
    }

    public FactionColour getCardColour() {
        return this.colour;
    }

    public int getImageBlueViewInt(){
        return this.imageBlueViewInt;
    }

    public int getImageRedViewInt(){
        return this.imageRedViewInt;
    }

    public HashMap<Integer, ArrayList<Coordinate>> getMoveset() {
        HashMap<Integer, ArrayList<Coordinate>> copyMoveset = new HashMap<>();
        copyMoveset.putAll(this.moveset);

        return copyMoveset;
    }
}
