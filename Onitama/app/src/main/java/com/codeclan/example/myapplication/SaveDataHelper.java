package com.codeclan.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.codeclan.example.myapplication.models.Game;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by user on 31/01/2018.
 */

public class SaveDataHelper {


    private static Gson gson = new Gson();


    public static HashMap<Integer, Game> saveGame(Game gameToSave, String jsonStringSaveData){

        Date currentTime = Calendar.getInstance().getTime();
        gameToSave.setDateSaved(currentTime);

        Integer turnCount = gameToSave.getTurnCount();

        HashMap<Integer, Game> gameSaveMap;


        if (jsonStringSaveData.equals("no save")){

            gameSaveMap = new HashMap<>();

        } else {

            TypeToken<HashMap<Integer, Game>> gameSaveGsonToken;
            gameSaveGsonToken   = new TypeToken<HashMap<Integer, Game>>(){};
            gameSaveMap         = gson.fromJson(jsonStringSaveData, gameSaveGsonToken.getType());

        }

        gameSaveMap.put(turnCount, gameToSave);

        return gameSaveMap;

    }

//    public static void clearGame(Game gameToClear){
//
//    }

}
