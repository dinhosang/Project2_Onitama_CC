package com.codeclan.example.myapplication;

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
    private static HashMap<Integer, Game> gameSaveMap;
    private static TypeToken<HashMap<Integer, Game>> gameSaveGsonToken = new TypeToken<HashMap<Integer, Game>>(){};


    private ArrayList<Game> getSavedGames(){

        Game currentSavedGame;
        ArrayList<Game> savedGames = new ArrayList<>();

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPref.getAll();

        Gson gson = new Gson();
        TypeToken<Game> gameGsonToken = new TypeToken<Game>(){};

        if (!allEntries.isEmpty()) {

            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

                currentSavedGame = gson.fromJson(entry.getValue().toString(), gameGsonToken.getType());
                if (!currentSavedGame.getName().equals("recent game")) {
                    savedGames.add(currentSavedGame);
                }
            }
        }



        return savedGames;
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
