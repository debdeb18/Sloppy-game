package com.hanback.sloppy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.hanback.jni.DotmatrixJNI;
import com.hanback.jni.LedJNI;
import com.hanback.jni.TextLcdJNI;

public class LevelSelect2 extends AppCompatActivity {
    private char ledData = (char)0;
    private LedJNI ledJNI;
    private final TextLcdJNI textLcdJNI = new TextLcdJNI();
    private final DotmatrixJNI dotmatrix = new DotmatrixJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select2);

        turnLedOn();
        final String str1 = "Sloppy";
        final String str2 = "choose a level";
        textLcdJNI.printTextLcd(str1, str2);

        dotmatrix.isLevelSelect();

        Button buttonMenu = findViewById(R.id.MenuButton);
        buttonMenu.setOnClickListener(v -> openSloppyActivity());

        Button button1 = findViewById(R.id.Level1Button);
        button1.setOnClickListener(v -> openLevelOne());

        Button button2 = findViewById(R.id.Level2Button);
        button2.setOnClickListener(v -> openLevelTwo());

        Button button3 = findViewById(R.id.Level3Button);
        button3.setOnClickListener(v -> openLevelThree());
    }

    private void turnLedOn() {
        ledJNI = new LedJNI();
        ledData = (char)255;
        ledJNI.on(ledData);
    }

    private void openSloppyActivity() { // ini buat ke screen main menu
        Intent intent_SA = new Intent(this, SloppyActivity.class);
        startActivity(intent_SA);
        //finish();
    }
    private void openLevelOne() { // ini buat ke screen level 1
        Intent intent_L1 = new Intent(this, LevelOne.class);
        startActivity(intent_L1);
        //finish();
    }
    private void openLevelTwo() { // ini buat ke screen level 2
        Intent intent_L2 = new Intent(this, level_two.class);
        startActivity(intent_L2);
        //finish();
    }
    private void openLevelThree() { // ini buat ke screen level 3
        Intent intent_L3 = new Intent(this, level_three.class);
        startActivity(intent_L3);
        //finish();
    }

    @Override
    protected void onResume() {
        textLcdJNI.initialize();
        ledJNI.init();
        turnLedOn();
        final String str1 = "Sloppy - Level";
        final String str2 = "choose a level";
        textLcdJNI.printTextLcd(str1, str2);

        dotmatrix.isLevelSelect();
        super.onResume();
    }

    @Override
    protected void onPause() {
        textLcdJNI.off();
        ledJNI.close();
        dotmatrix.stop();
        super.onPause();
    }

    @Override
    public void onStart() {
        turnLedOn();
        final String str1 = "Sloppy - Level";
        final String str2 = "choose a level";
        textLcdJNI.printTextLcd(str1, str2);

        dotmatrix.isLevelSelect();
        super.onStart();
    }

}