package com.codeclan.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.codeclan.example.myapplication.models.Game;
import com.codeclan.example.myapplication.models.pieces.Piece;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 31/01/2018.
 */

public abstract class SaveDataHelper {


    private static String                   preferenceFileKey;
    private static SharedPreferences        sharedPref;
    private static SharedPreferences.Editor editor;

    private static Gson gson = new Gson();
    private static HashMap<Integer, Game> gameSaveMap;
    private static TypeToken<HashMap<Integer, Game>> gameSaveGsonToken = new TypeToken<HashMap<Integer, Game>>(){};


    private static void initSharedPref(Context context){

        preferenceFileKey   = context.getString(R.string.preference_file_key);
        sharedPref          = context.getSharedPreferences(preferenceFileKey, Context.MODE_PRIVATE);
    }


    public static ArrayList<Game> getAllSavedGamesExceptRecent(Context context){

        initSharedPref(context);

        Map<String, ?>  allEntries;
        allEntries = sharedPref.getAll();

        String gameName;
        String currentGameSavaDataString;

        Game currentSavedGameAtLatestTurn;
        HashMap<Integer, Game> currentSaveGameData;

        ArrayList<Game> savedGames = new ArrayList<>();

        if (!allEntries.isEmpty()) {

            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

                gameName = entry.getKey();
                if (!gameName.equals("recent game")) {

                    currentGameSavaDataString       = entry.getValue().toString();

                    currentSaveGameData             = gson.fromJson(currentGameSavaDataString,
                                                                    gameSaveGsonToken.getType());
                    currentSavedGameAtLatestTurn    = currentSaveGameData.get(0);

                    savedGames.add(currentSavedGameAtLatestTurn);
                }
            }
        }

        return savedGames;
    }


    public static boolean recentGameExists(Context context){

        initSharedPref(context);

        String emptyGameString      = new Game().toString();
        String mostRecentGame       = sharedPref.getString("recent game", emptyGameString);

        if (mostRecentGame.equals(emptyGameString)){
            return false;
        } else {
            return true;
        }
    }


    public static HashMap<Integer, Game> saveGame(Game gameToSave, String jsonStringSaveData){

        Date currentTime = Calendar.getInstance().getTime();
        gameToSave.setDateSaved(currentTime);

        Integer turnCount = gameToSave.getTurnCount();


        if (jsonStringSaveData.equals("no save")){

            gameSaveMap = new HashMap<>();

        } else {

            gameSaveMap = gson.fromJson(jsonStringSaveData, gameSaveGsonToken.getType());

        }

        gameSaveMap.put(0, gameToSave);
        gameSaveMap.put(turnCount, gameToSave);

        return gameSaveMap;
    }


    public static Game loadGame(String gameSaveDataString, int turnToLoadOn) {

        gameSaveMap = gson.fromJson(gameSaveDataString, gameSaveGsonToken.getType());

        return gameSaveMap.get(turnToLoadOn);
    }

}
