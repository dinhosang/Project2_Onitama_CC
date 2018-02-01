package com.codeclan.example.myapplication.models;

import java.io.Serializable;

/**
 * Created by user on 27/01/2018.
 */

public class Coordinate implements Serializable {


    protected int xCoord;
    protected int yCoord;


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
