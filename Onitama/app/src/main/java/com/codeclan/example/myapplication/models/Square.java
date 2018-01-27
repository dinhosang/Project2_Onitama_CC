package com.codeclan.example.myapplication.models;

/**
 * Created by user on 25/01/2018.
 */

public class Square {


    private int xCoord;
    private int yCoord;



    public Square(int x, int y){
        this.xCoord = x;
        this.yCoord = y;
    }


    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }
}
