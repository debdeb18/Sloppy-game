package com.hanback.sloppy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.hanback.jni.DotmatrixJNI;
import com.hanback.jni.LedJNI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import com.hanback.sloppy.databinding.ActivityMainBinding;
import com.hanback.jni.TextLcdJNI;

public class SloppyActivity extends AppCompatActivity {
    //create db
    //private DBHandler dbHandler;
    private char ledData = (char)0;
    private LedJNI ledJNI;

    private final TextLcdJNI textLcdJNI = new TextLcdJNI();
    private final DotmatrixJNI dotmatrix = new DotmatrixJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.home_bar);

        com.hanback.sloppy.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialize db
        //dbHandler = new DBHandler(SloppyActivity.this);

        //to add info to db
        //dbHandler.addNewCourse(playerName, highestlvl, lvlTime, lvlDescription);
        turnLedOn();
        printHome();
        dotmatrix.isHome();

        //button to move to subactivity
        Button button = findViewById(R.id.StartButton);
        button.setOnClickListener(v -> openSubActivity());
        //ganti dari sini
        Button button_HTP = findViewById(R.id.HTPButton); // ini buat button di main screen How to Play
        button_HTP.setOnClickListener(v -> openSubActivity_HTP());
        Button button_LS = findViewById(R.id.LevelButton); // ini buat button di main screen level select
        button_LS.setOnClickListener(v -> openSubActivity_LS());
    }

    private void turnLedOn() {
        ledJNI = new LedJNI();
        ledData = (char)0;
        ledJNI.on(ledData);
    }

    @Override
    public void onStart() {
        turnLedOn();
        printHome();
        dotmatrix.isHome();
        super.onStart();
    }

    private void openSubActivity() {
        Intent intent = new Intent(this, LevelOne.class);
        startActivity(intent);
    }

    private void openSubActivity_HTP() { // ini buat ke screen how to play
        Intent intent_HTP = new Intent(this, How_to_play2.class);
        startActivity(intent_HTP);
    }
    private void openSubActivity_LS() { // ini buat ke screen level select
        Intent intent_LS = new Intent(this, LevelSelect2.class);
        startActivity(intent_LS);
    }

    @Override
    protected void onResume() {
        textLcdJNI.initialize();
        ledJNI.init();
        printHome();
        dotmatrix.isHome();
        super.onResume();
    }

    private void printHome() {
        final String str1 = "Sloppy - Home";
        final String str2 = "a game memory";
        textLcdJNI.printTextLcd(str1, str2);
    }

    @Override
    protected void onPause() {
        textLcdJNI.clear();
        textLcdJNI.off();
        ledJNI.close();
        dotmatrix.stop();
        super.onPause();
    }
}