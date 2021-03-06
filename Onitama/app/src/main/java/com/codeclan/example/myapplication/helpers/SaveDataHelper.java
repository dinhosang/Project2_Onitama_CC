package com.codeclan.example.myapplication.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.codeclan.example.myapplication.R;
import com.codeclan.example.myapplication.models.Game;
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


    private static Gson gson;

    private static SharedPreferences                    sharedPref;
    private static SharedPreferences.Editor             editor;

    private static HashMap<Integer, Game>               gameSaveMap;
    private static TypeToken<HashMap<Integer, Game>>    gameSaveGsonToken;


    private static void initSharedPref(Context context){

        String preferenceFileKey;

        gson                = new Gson();
        gameSaveGsonToken   = new TypeToken<HashMap<Integer, Game>>(){};

        preferenceFileKey   = context.getString(R.string.preference_file_key);
        sharedPref          = context.getSharedPreferences(preferenceFileKey, Context.MODE_PRIVATE);
        editor              = sharedPref.edit();

    }

    public static ArrayList<Game> getAllSavedGamesExceptRecent(Context context){

        initSharedPref(context);

        // sharedPred.getAll() returns an object of type Map<String, ?>
        // cannot user HashMap instead of ? at least at first glance as it returns an error
        // stating string cannot be cast to HashMap (or Map) if converting ? to one of those types.
        Map<String, ?>  allEntries;
        allEntries = sharedPref.getAll();

        String gameName;
        String currentGameSaveDataString;

        Game currentSavedGameAtLatestTurn;

        ArrayList<Game> savedGames = new ArrayList<>();

        if (!allEntries.isEmpty()) {

            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

                gameName = entry.getKey();
                if (!gameName.equals("recent game")) {

                    currentGameSaveDataString       = entry.getValue().toString();

                    gameSaveMap                     = gson.fromJson(currentGameSaveDataString,
                                                                    gameSaveGsonToken.getType());
                    currentSavedGameAtLatestTurn    = gameSaveMap.get(0);

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

    public static void saveGame(Game gameToSave, Context context){

        initSharedPref(context);

        String gameName;
        String gameSaveDataString;


        gameName            = gameToSave.getName();
        Integer turnCount   = gameToSave.getTurnCount();
        Date currentTime    = Calendar.getInstance().getTime();
        gameToSave.setDateSaved(currentTime);


        gameSaveDataString  = sharedPref.getString(gameName, "no save");
        if (gameSaveDataString.equals("no save")){

            gameSaveMap = new HashMap<>();
        } else {

            gameSaveMap = gson.fromJson(gameSaveDataString, gameSaveGsonToken.getType());
        }

        gameSaveMap.put(0, gameToSave);
        gameSaveMap.put(turnCount, gameToSave);

        editor.putString(gameName, gson.toJson(gameSaveMap));
        editor.apply();
    }

    public static void convertOriginalSaveToNewSave(String originalName, String newName, Context context){

        initSharedPref(context);
        String gameSaveDataString;

        HashMap<Integer, Game> intermediateSaveMap = new HashMap<>();

        gameSaveDataString  = sharedPref.getString(originalName, "no save");
        gameSaveMap         = gson.fromJson(gameSaveDataString, gameSaveGsonToken.getType());

        for (Map.Entry<Integer, Game> entry : gameSaveMap.entrySet()) {

            Game currentGame        = entry.getValue();
            Integer currentTurn     = entry.getKey();
            String currentGameName  = currentGame.getName();

            if (!currentGameName.equals(newName)){

                entry.getValue().setName(newName);
            }

            intermediateSaveMap.put(currentTurn, currentGame);
        }

        editor.putString(newName, gson.toJson(intermediateSaveMap));
        editor.apply();
    }

    public static Game loadGame(String gameName, Context context, int turnToLoadOn) {

        initSharedPref(context);
        String gameSaveDataString;

        gameSaveDataString  = sharedPref.getString(gameName, "no save");
        gameSaveMap         = gson.fromJson(gameSaveDataString, gameSaveGsonToken.getType());

        return gameSaveMap.get(turnToLoadOn);
    }


    public static void clearSaveOfGameNamed(String gameNameToClearSaveOf, Context context){

        initSharedPref(context);

        editor.remove(gameNameToClearSaveOf);
        editor.apply();
    }

    public static void clearLaterSavesOfGameNamed(String gameNameToClearSaveOf, Context context,
                                                  int turnToClearFrom, int turnToClearTo) {

        initSharedPref(context);

        String gameSaveDataString;

        gameSaveDataString  = sharedPref.getString(gameNameToClearSaveOf, "no save");
        gameSaveMap         = gson.fromJson(gameSaveDataString, gameSaveGsonToken.getType());

        for (int index = turnToClearFrom; index <= turnToClearTo; index += 1) {

            gameSaveMap.remove(index);
        }

        editor.putString(gameNameToClearSaveOf, gson.toJson(gameSaveMap));
        editor.apply();
    }
}
