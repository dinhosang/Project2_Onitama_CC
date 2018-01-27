package com.codeclan.example.myapplication.models;

/**
 * Created by user on 27/01/2018.
 */

public class Coordinate {


    int xCoord;
    int yCoord;


    public Coordinate(int xCoord, int yCoord) {

        this.xCoord = xCoord;
        this.yCoord = yCoord;

    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }
}
