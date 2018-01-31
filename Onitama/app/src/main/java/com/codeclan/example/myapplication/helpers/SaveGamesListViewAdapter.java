package com.codeclan.example.myapplication.helpers;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codeclan.example.myapplication.R;
import com.codeclan.example.myapplication.models.Game;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by user on 30/01/2018.
 */

public class SaveGamesListViewAdapter extends ArrayAdapter<Game> {


    public SaveGamesListViewAdapter(Context context, ArrayList<Game> games){
        super(context, 0, games);
    }


    @Override
    public View getView(int position, View listItemView, ViewGroup parent) {
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.saved_game_list_item, parent, false);
        }

        Game currentSavedGame = getItem(position);

        TextView nameSavedGame = listItemView.findViewById(R.id.saveGameListItemNameText);
        nameSavedGame.setText(String.format("%s", currentSavedGame.getName()));
        nameSavedGame.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        TextView turnCountSavedGame =listItemView.findViewById(R.id.savedGameListItemTurnView);
        turnCountSavedGame.setText(String.format(Locale.UK, "Turn: %d", currentSavedGame.getTurnCount()));


        TextView timeSaveGame = listItemView.findViewById(R.id.savedGameListItemTimeTextView);
        timeSaveGame.setText(String.format("%s", currentSavedGame.getDateSaved().toString()));

        listItemView.setTag(currentSavedGame);

        return listItemView;

    }

}
