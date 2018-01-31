package com.codeclan.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.models.Game;
import com.codeclan.example.myapplication.models.cards.Card;
import com.codeclan.example.myapplication.models.squares.Square;
import com.google.gson.Gson;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    ImageView           blueCardOne;
    ImageView           blueCardTwo;
    ImageView           redCardOne;
    ImageView           redCardTwo;
    ImageView           blueFloatingCard;
    ImageView           redFloatingCard;
    Game                game;
    BoardGridAdapter    boardGridAdapter;
    GridView            gridView;


    Gson    gson;
    String  gameSaveDataString;

    SharedPreferences           sharedPref;
    SharedPreferences.Editor    editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blueCardOne = findViewById(R.id.blueCardOne);
        blueCardTwo = findViewById(R.id.blueCardTwo);

        redCardOne  = findViewById(R.id.redCardOne);
        redCardTwo  = findViewById(R.id.redCardTwo);

        blueFloatingCard    = findViewById(R.id.blueFloatingCard);
        redFloatingCard     = findViewById(R.id.redFloatingCard);

        // getSharedPreferences is not static so cannot move to SavaDataHelper file after making
        // the helper extend Activity. Helper is now just a Java class.
        sharedPref      = getSharedPreferences(getString(R.string.preference_file_key),
                                                Context.MODE_PRIVATE);

        editor          = sharedPref.edit();
        gson            = new Gson();


        Intent intent = getIntent();
        if (intent.getStringExtra("load") != null){

            String gameName = intent.getStringExtra("load");
            loadGame(gameName, 0);

        } else {

            // onCreate runs again from after the if after reaching end of a method with no other path forward!
            // if below is not in else but just free standing code it then tries to run, and sets game to null
            // as nothing is in the intent under the chosen name if the game was loaded.
            this.game = (Game) intent.getSerializableExtra("game");

            saveGame();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main, menu);

        return true;
    }


    private void loadGame(String gameName, int turnToLoad) {

        gameSaveDataString = sharedPref.getString(gameName, "no save");
        this.game = SaveDataHelper.loadGame(gameSaveDataString, turnToLoad);

        showBoardState();
    }

    private void saveGame(){

        gameSaveDataString = sharedPref.getString(this.game.getName(), "no save");

        HashMap<Integer, Game> gameSaveMap = SaveDataHelper.saveGame(this.game, gameSaveDataString);

        editor.putString(this.game.getName(), gson.toJson(gameSaveMap));
        editor.apply();

        showBoardState();
    }

    private void showBoardState() {

        if (this.game.getGameWinner() != null){
            clearGame();
            startGame();
        }

        Card firstBlueCard = this.game.getBlueHand().get(0);
        Card secondBlueCard = this.game.getBlueHand().get(1);
        Card firstRedCard = this.game.getRedHand().get(0);
        Card secondRedCard = this.game.getRedHand().get(1);

        if (this.game.getActiveFaction().equals(FactionColour.BLUE)){
            Card floatingCardForBlue = this.game.getFloatingCardForBlue();
            blueFloatingCard.setImageResource(floatingCardForBlue.getImageBlueViewInt());
            redFloatingCard.setImageResource(0);
        } else  {
            Card floatingCardForRed = this.game.getFloatingCardForRed();
            redFloatingCard.setImageResource(floatingCardForRed.getImageRedViewInt());
            blueFloatingCard.setImageResource(0);
        }

        blueCardOne.setImageResource(firstBlueCard.getImageBlueViewInt());
        blueCardTwo.setImageResource(secondBlueCard.getImageBlueViewInt());
        redCardOne.setImageResource(firstRedCard.getImageRedViewInt());
        redCardTwo.setImageResource(secondRedCard.getImageRedViewInt());

        Card activeCard = this.game.getActiveCard();
        int activeCardBorder = R.drawable.active_card_player_hand_border;
        int nonActiveCardBorder = R.drawable.non_active_card_player_hand_border;

        blueCardOne.setBackgroundResource(nonActiveCardBorder);
        blueCardTwo.setBackgroundResource(nonActiveCardBorder);
        redCardOne.setBackgroundResource(nonActiveCardBorder);
        redCardTwo.setBackgroundResource(nonActiveCardBorder);

        if (activeCard != null) {
            if (activeCard.equals(firstBlueCard)) {
                blueCardOne.setBackgroundResource(activeCardBorder);
            } else if (activeCard.equals(secondBlueCard)) {
                blueCardTwo.setBackgroundResource(activeCardBorder);
            } else if (activeCard.equals(firstRedCard)) {
                redCardOne.setBackgroundResource(activeCardBorder);
            } else {
                redCardTwo.setBackgroundResource(activeCardBorder);
            }
        }

        boardGridAdapter = new BoardGridAdapter(this, this.game.getBoard().getCompleteBoard(), this.game.getActiveSquare());
        gridView = findViewById(R.id.boardGridView);
        gridView.setAdapter(boardGridAdapter);
    }

    private void clearGame(){

        editor.remove(this.game.getName());
        editor.apply();

    }

    private void startGame(){
        this.game        = new Game();

        showBoardState();
    }


    public void toggleCardSelectionOnClick(View view) {
        String buttonClicked = view.getTag().toString();

        Card card;

        if (buttonClicked.equals("blue one")) {
            card    = game.getBlueHand().get(0);
        } else if (buttonClicked.equals("blue two")) {
            card    = game.getBlueHand().get(1);
        } else if (buttonClicked.equals("red one")) {
            card    = game.getRedHand().get(0);
        } else {
            card    = game.getRedHand().get(1);
        }

        game.toggleActiveCardSelection(card);

        showBoardState();
    }

    public void toggleUnitSquareSelection(View view) {

        Square clickedSquare = (Square) view.getTag();

        game.toggleSquareSelection(clickedSquare);

        saveGame();
    }

    public void onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_save_game) {

            AlertDialog.Builder dialogBuilder;
            final EditText      saveGameNameField;
            Button              cancelButton;
            Button              saveButton;

            View saveGameView   = getLayoutInflater().inflate(R.layout.save_game_view, null);
            dialogBuilder       = new AlertDialog.Builder(MainActivity.this);


            saveGameNameField   = (EditText) saveGameView.findViewById(R.id.saveGameViewEnterSaveNameEditText);
            cancelButton        = (Button) saveGameView.findViewById(R.id.saveGameViewCancelButton);
            saveButton          = (Button) saveGameView.findViewById(R.id.saveGameViewSaveButton);

//            dialogBuilder.setView(saveGameView);
//            final AlertDialog dialog = dialogBuilder.create();
//            dialog.show();


            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    dialog.cancel();
                }
            });


            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String nameChosen = saveGameNameField.getText().toString();

                    if (nameChosen.isEmpty()){
                        Toast.makeText(MainActivity.this, "Please enter a name for the saved game", Toast.LENGTH_SHORT).show();
                    } else if (nameChosen.equals("recent game")){
                        Toast.makeText(MainActivity.this, "Reserved name, please enter another", Toast.LENGTH_LONG).show();
                    } else {
                        clearGame();
                        MainActivity.this.game.setName(nameChosen);
                        Toast.makeText(MainActivity.this, String.format("Game Saved As: %s", nameChosen), Toast.LENGTH_LONG).show();
                        //                    dialog.dismiss();
                        saveGame();
                    }
                }
            });

            dialogBuilder.setView(saveGameView);
            final AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        }
    }
}
