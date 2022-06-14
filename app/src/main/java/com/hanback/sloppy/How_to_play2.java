package com.hanback.sloppy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.hanback.jni.DotmatrixJNI;
import com.hanback.jni.LedJNI;
import com.hanback.jni.TextLcdJNI;

public class How_to_play2 extends AppCompatActivity {
    private char ledData = (char)0;
    private LedJNI ledJNI;
    private final TextLcdJNI textLcdJNI = new TextLcdJNI();
    private final DotmatrixJNI dotmatrix = new DotmatrixJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play2);

        turnLedOn();
        final String str1 = "Sloppy";
        final String str2 = "how to play?";
        textLcdJNI.printTextLcd(str1, str2);
        dotmatrix.isTutorial();

        Button buttonBack = findViewById(R.id.BackButton);
        buttonBack.setOnClickListener(v -> openSloppyActivity());
    }

    private void turnLedOn() {
        ledJNI = new LedJNI();
        ledData = (char)255;
        ledJNI.on(ledData);
    }

    private void openSloppyActivity() { // ini buat ke screen level select
        Intent intent_SA = new Intent(this, SloppyActivity.class);
        startActivity(intent_SA);
        finish();
    }

    @Override
    protected void onResume() {
        textLcdJNI.initialize();
        ledJNI.init();
        turnLedOn();

        final String str1 = "Sloppy - tutorial";
        final String str2 = "how to play?";
        textLcdJNI.printTextLcd(str1, str2);
        dotmatrix.isTutorial();
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
        final String str1 = "Sloppy - tutorial";
        final String str2 = "how to play?";
        textLcdJNI.printTextLcd(str1, str2);
        dotmatrix.isTutorial();
        super.onStart();
    }
}