package com.hanback.sloppy;

import static android.graphics.Color.parseColor;
import androidx.appcompat.app.AppCompatActivity;

import com.hanback.jni.DotmatrixJNI;
import com.hanback.jni.FullcolorledJNI;
import com.hanback.jni.LedJNI;
import com.hanback.jni.TextLcdJNI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Vibrator;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class level_two extends AppCompatActivity {
    final Handler handler = new Handler();

    //board parameter
    private final LedJNI ledJNI = new LedJNI();
    private final int[] lives_value = new int[]{0, 1, 3, 7};
    private final TextLcdJNI textLcdJNI = new TextLcdJNI();
    private final DotmatrixJNI dotmatrix = new DotmatrixJNI();
    private final FullcolorledJNI fullcolorledsJNI = new FullcolorledJNI();

    //game parameter
    private int currentLives;
    private int currentScore;
    private int lastScore;
    ArrayList<Integer> sequence;
    final ArrayList<Integer> userSeq;
    Vibrator mVibrator;
    boolean playWrong = false;

    public level_two(){
        userSeq = new ArrayList<>();
        sequence = new ArrayList<>();
        currentLives = 3;
        currentScore = 0;
        lastScore = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_two);

        getSupportActionBar().setTitle("Level 2");

        Button greenBtn = findViewById(R.id.greenBtn_one);
        Button purpleBtn = findViewById(R.id.purpleBtn_one);
        Button blueBtn = findViewById(R.id.blueBtn_one);
        Button redBtn = findViewById(R.id.redBtn_one);
        Button yellowBtn = findViewById(R.id.yellowBtn_one);
        Button backToHome = findViewById(R.id.backToHome2);
        Button tryAgain = findViewById(R.id.tryAgain2);
        Button nextLvl = findViewById(R.id.nextLvl2);

        TextView scoreText = findViewById(R.id.scoreDisplay);
        TextView wrongAlert = findViewById(R.id.wrongAlert2);
        TextView gameOver = findViewById(R.id.gameOver2);

        ImageView live1 = findViewById(R.id.live1);
        ImageView live2 = findViewById(R.id.live2);
        ImageView live3 = findViewById(R.id.live3);

        mVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        level_two play = new level_two();
        updateLives(play);

        TextView startView = findViewById(R.id.textView);
        startView.setOnClickListener(view -> {
            dotmatrix.isPLaying();
            startView.setVisibility(View.GONE);
            updateLivesDisplay(live1, live2, live3, play);
            startGame(0, play, greenBtn, blueBtn, redBtn, yellowBtn, purpleBtn, scoreText, wrongAlert, mVibrator, gameOver, tryAgain, backToHome, nextLvl, live1, live2, live3);
        });

        tryAgain.setOnClickListener(v -> reload());
        backToHome.setOnClickListener(v -> toHome());
        nextLvl.setOnClickListener(v -> nextLevel());
    }

    private void startGame(int time, level_two play, Button greenBtn, Button blueBtn, Button redBtn, Button yellowBtn, Button purpleBtn, TextView scoreText, TextView wrongAlert, Vibrator mVibrator, TextView gameOver, Button tryAgain, Button backToHome, Button nextLvl, ImageView live1, ImageView live2, ImageView live3) {
        playWrong = false;
        final Runnable r = () -> {
            if(play.currentLives == 0){ //game over
                new Runnable() {
                    @Override
                    public void run() {
                        updateLives(play);
                        dotmatrix.isPLaying();
                        final String str1 = "Level 1 - cake";
                        final String str2 = "uh oh.. you're ded";
                        textLcdJNI.clear();
                        textLcdJNI.print1Line(str1);
                        textLcdJNI.print2Line(str2);
                    }
                };
                runOnUiThread(() -> {
                    gameOver.setVisibility(View.VISIBLE);
                    tryAgain.setVisibility(View.VISIBLE);
                    backToHome.setVisibility(View.VISIBLE);
                });
            } else {
                updateScore(play.currentScore, scoreText);

                int numOfSeq = getNumOfSeq(play);
                if(numOfSeq == 0) {
                    new Runnable(){
                        @Override
                        public void run() {
                            final String str1 = "Level 1 - cake";
                            final String str2 = "Level Completed!! Congratulations";
                            textLcdJNI.clear();
                            textLcdJNI.print1Line(str1);
                            textLcdJNI.print2Line(str2);
                            fullcolorledsJNI.run(play.currentLives);
                            dotmatrix.off();
                        }
                    };
                    runOnUiThread(() -> nextLvl.setVisibility(View.VISIBLE));
                } else {
                    generateRandomSequence(numOfSeq, play);
                    generatePattern(numOfSeq, greenBtn, blueBtn, redBtn, yellowBtn, purpleBtn, play, scoreText, wrongAlert, mVibrator, gameOver, tryAgain, backToHome, nextLvl, live1, live2, live3);
                }
            }
        };
        handler.postDelayed(r, time);
    }

    private void generatePattern(int numOfSeq, Button greenBtn, Button blueBtn, Button redBtn, Button yellowBtn, Button purpleBtn, level_two play, TextView scoreText, TextView wrongAlert, Vibrator mVibrator, TextView gameOver, Button tryAgain, Button backToHome, Button nextLvl, ImageView live1, ImageView live2, ImageView live3) {
        int seq_size = play.sequence.size();
        for(int i = 0; i <= seq_size; i++){
            int finalI = i;
            final Runnable r = () -> {
                if(finalI == seq_size){
                    checkUserPattern(numOfSeq, play, scoreText, greenBtn, blueBtn, redBtn, yellowBtn, purpleBtn, wrongAlert, mVibrator, gameOver, tryAgain, backToHome, nextLvl, live1, live2, live3);
                } else {
                    //updateLives(play);
                    switch (play.sequence.get(finalI)) {
                        case 1:
                            colorBlink(greenBtn, "#FF77FC96", "#0077FC96");
                            Log.d("seqColor", "green");
                            break;
                        case 2:
                            colorBlink(blueBtn, "#FFAAD5FF", "#00AAD5FF");
                            Log.d("seqColor", "blue");
                            break;
                        case 3:
                            colorBlink(redBtn, "#FFFCC3B9", "#00FCC3B9");
                            Log.d("seqColor", "red");
                            break;
                        case 4:
                            colorBlink(yellowBtn, "#FFFEDD9D", "#00FEDD9D");
                            Log.d("seqColor", "yellow");
                            break;
                        case 5:
                            colorBlink(purpleBtn, "#FFF5AAFD", "#00F5AAFD");
                            Log.d("seqColor", "purple");
                            break;
                        default:
                            break;
                    }
                }
            };
            handler.postDelayed(r, 800L * i);
        }
    }

    public void colorBlink(final View v, String Color1, String Color2) {
        v.setBackgroundColor(parseColor(Color1));
        final Runnable r = () -> v.setBackgroundColor(parseColor(Color2));
        handler.postDelayed(r, 700);
    }

    private int getNumOfSeq(level_two play) {
        if(play.lastScore == 0){
            return 1; //first number of sequence
        } else if(play.lastScore == 100){ //if 100 or level finish
            return 0; //end of level
        }

        int[] lvOne_cp = {4, 12, 28, 48, 72};
        int[] seq = {2, 4, 5, 6, 7};
        for(int i = 0; i < lvOne_cp.length; i++){
            if(play.lastScore == lvOne_cp[i]){
                return seq[i];
            }
        }
        //if error
        return -1;
    }

    private void generateRandomSequence(int numOfSeq, level_two play) {
        Random rand = new Random();
        for (int i = 0; i < numOfSeq; i++) play.sequence.add(rand.nextInt(5) + 1);
    }

    private void checkUserPattern(int numOfSeq, level_two play, TextView scoreText, Button greenBtn, Button blueBtn, Button redBtn, Button yellowBtn, Button purpleBtn, TextView wrongAlert, Vibrator mVibrator, TextView gameOver, Button tryAgain, Button backToHome, Button nextLvl, ImageView live1, ImageView live2, ImageView live3) {
        for(int i = 0; i < numOfSeq; i++){
            int finalI = i;
            new Thread()  {
                public void run() {
                    play.getUserClick(greenBtn, blueBtn, redBtn, yellowBtn, purpleBtn);
                }
            }.start();
            new Thread() {
                public void run(){
                    play.compare(finalI, play, greenBtn, blueBtn, redBtn, yellowBtn, purpleBtn, scoreText, wrongAlert, mVibrator, gameOver, tryAgain, backToHome, nextLvl, live1, live2, live3);
                }
            }.start();
        }
    }

    private void compare(int i, level_two play, Button greenBtn, Button blueBtn, Button redBtn, Button yellowBtn, Button purpleBtn, TextView scoreText, TextView wrongAlert, Vibrator mVibrator, TextView gameOver, Button tryAgain, Button backToHome, Button nextLvl, ImageView live1, ImageView live2, ImageView live3) {
        synchronized (userSeq) {
            //wait for user input
            try {
                userSeq.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //compare input
            if(!Objects.equals(userSeq.size(), sequence.size()) && playWrong){
                //if current compare is after wrong do nothing
                //do nothing
            } else if (Objects.equals(userSeq.get(i), sequence.get(i))) {
                play.currentScore = play.currentScore + 4;
                sleep();
            } else {
                //if wrong pattern, display latest score
                updateLives(play);
                mVibrator.vibrate(100);
                printAlert(wrongAlert);
                playWrong = true;
                for(int j = i; j < sequence.size(); j++){
                    play.exitWait();
                }
                play.currentScore = play.lastScore;
                play.currentLives = play.currentLives - 1;
            }

            if(i+1 == sequence.size()){
                userSeq.clear();
                sequence.clear();
                play.lastScore = play.currentScore;
                updateScore(play.lastScore, scoreText);
                updateLivesDisplay(live1, live2, live3, play);
                startGame(500, play, greenBtn, blueBtn, redBtn, yellowBtn, purpleBtn, scoreText, wrongAlert, mVibrator, gameOver, tryAgain, backToHome, nextLvl, live1, live2, live3);
            }
        }
    }

    private void getUserClick(Button greenBtn, Button blueBtn, Button redBtn, Button yellowBtn, Button purpleBtn) {
        greenBtn.setOnClickListener(view -> {
            synchronized (userSeq) {
                userSeq.add(1);
                userSeq.notify();
            }
        });

        blueBtn.setOnClickListener(view -> {
            synchronized (userSeq) {
                userSeq.add(2);
                userSeq.notify();
            }
        });

        redBtn.setOnClickListener(view -> {
            synchronized (userSeq) {
                userSeq.add(3);
                userSeq.notify();
            }
        });

        yellowBtn.setOnClickListener(view -> {
            synchronized (userSeq) {
                userSeq.add(4);
                userSeq.notify();
            }
        });

        purpleBtn.setOnClickListener(view -> {
            synchronized (userSeq) {
                userSeq.add(5);
                userSeq.notify();
            }
        });
    }

    private void nextLevel() {
        Intent intent = new Intent(this, level_three.class);
        startActivity(intent);
    }

    private void reload() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void toHome() {
        Intent intent = new Intent(this, SloppyActivity.class);
        startActivity(intent);
    }

    private void updateLives(level_two play) {
        ledJNI.on((char) lives_value[play.currentLives]);
    }
    private void updateScore(int currentScore, TextView scoreText) {
        String score = currentScore + "%";
        runOnUiThread(() -> scoreText.setText(score));

        final String str1 = "Level 2 - peace";
        final String str2 = "progress: " + currentScore + "%";
        textLcdJNI.clear();
        textLcdJNI.print1Line(str1);
        textLcdJNI.print2Line(str2);
    }
    private void updateLivesDisplay(ImageView live1, ImageView live2, ImageView live3, level_two play) {
        updateLives(play);
        runOnUiThread(() -> {
            if(play.currentLives == 3) {
                live1.setVisibility(View.VISIBLE);
                live2.setVisibility(View.VISIBLE);
                live3.setVisibility(View.VISIBLE);
            } else if(play.currentLives == 2){
                live1.setVisibility(View.VISIBLE);
                live2.setVisibility(View.VISIBLE);
                live3.setVisibility(View.INVISIBLE);
            } else if(play.currentLives == 1) {
                live1.setVisibility(View.VISIBLE);
                live2.setVisibility(View.INVISIBLE);
                live3.setVisibility(View.INVISIBLE);
            } else {
                live1.setVisibility(View.INVISIBLE);
                live2.setVisibility(View.INVISIBLE);
                live3.setVisibility(View.INVISIBLE);
            }
        });


    }

    private void printAlert(View v) {
        runOnUiThread(() -> {
            v.setVisibility(View.VISIBLE);
            final Runnable r = () -> v.setVisibility(View.INVISIBLE);
            handler.postDelayed(r, 600);
        });
    }

    private void exitWait() {
        synchronized (userSeq){
            userSeq.notify();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        textLcdJNI.initialize();
        ledJNI.init();
        super.onResume();
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