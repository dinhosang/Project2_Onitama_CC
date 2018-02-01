package com.codeclan.example.myapplication.constants;

/**
 * Created by user on 27/01/2018.
 */

public enum FactionColour {

    RED("Red"),
    BLUE("Blue");

    private String colourValue;

    FactionColour(String value){
        this.colourValue = value;
    }

    public String getColourValue(){
        return this.colourValue;
    }

}
