package com.codeclan.example.myapplication.constants;

/**
 * Created by user on 31/01/2018.
 */

public enum VictoryType {

    SENSEI("Sensei"),
    GATE("Gate");

    private String victoryValue;

    VictoryType(String value){
        this.victoryValue = value;
    }

    public String getVictoryValue(){
        return this.victoryValue;
    }

}
