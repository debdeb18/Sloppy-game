package com.hanback.sloppy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.hanback.jni.LedJNI;
import com.hanback.jni.TextLcdJNI;

public class level_three extends AppCompatActivity {
    private LedJNI ledJNI = new LedJNI();
    private TextLcdJNI textLcdJNI = new TextLcdJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_three);

        ledJNI = new LedJNI();
        ledJNI.on((char)255);

        final String str1 = "Level 3 - slice";
        final String str2 = "under construction";
        textLcdJNI.clear();
        textLcdJNI.print1Line(str1);
        textLcdJNI.print2Line(str2);

        Button backToHome = (Button) findViewById(R.id.backToHome2);
        backToHome.setOnClickListener(v -> toHome());
    }

    private void toHome() {
        Intent intent = new Intent(this, SloppyActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        textLcdJNI.initialize();
        ledJNI.init();

        ledJNI = new LedJNI();
        ledJNI.on((char)255);

        final String str1 = "Level 3 - slice";
        final String str2 = "under construction";
        textLcdJNI.clear();
        textLcdJNI.print1Line(str1);
        textLcdJNI.print2Line(str2);
        super.onResume();
    }



    @Override
    public void onStart() {
        ledJNI = new LedJNI();
        ledJNI.on((char)255);

        final String str1 = "Level 3 - slice";
        final String str2 = "under construction";
        textLcdJNI.clear();
        textLcdJNI.print1Line(str1);
        textLcdJNI.print2Line(str2);
        super.onStart();
    }

    @Override
    protected void onPause() {
        textLcdJNI.clear();
        textLcdJNI.off();
        ledJNI.close();
        super.onPause();
    }
}