package com.codeclan.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void welcomeScreenButtonOnClick(View view) {
        Button chosenButton = (Button) view;
        String textOnView = chosenButton.getText().toString();

        if (textOnView.equals(getString(R.string.new_game))){
            startNewGame();
        }

//        else if (textOnView.equals(getString(R.string.quit_game))){
//            finish();
//            System.exit(0);
//        }
    }


    private void startNewGame() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
