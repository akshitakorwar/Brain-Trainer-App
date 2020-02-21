package com.example.braintrainerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    androidx.gridlayout.widget.GridLayout gridLayout;
    Button button;
    TextView timerText;
    TextView scoreText;
    TextView additionText;
    TextView resultText;
    Button playAgainButton;

    int min = 1; int max = 10;
    int score;
    int totalQuestions;

    ArrayList<Integer> options = new ArrayList<Integer>();

    Button button0;
    Button button1;
    Button button2;
    Button button3;

    int positionOfCorrectAns;

    boolean buttonsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInvisible();
       // generateSum();

    }

    public void setInvisible(){
        gridLayout =(androidx.gridlayout.widget.GridLayout) findViewById(R.id.gridLayout);
        gridLayout.setVisibility(View.INVISIBLE);

        additionText = findViewById(R.id.textView);
        timerText = findViewById(R.id.textView2);
        scoreText = findViewById(R.id.textView4);
        playAgainButton = findViewById(R.id.button);

        additionText.setVisibility(View.INVISIBLE);
        timerText.setVisibility(View.INVISIBLE);
        scoreText.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
    }

    public void clickGo(final View view){
        button = findViewById(R.id.goButton);

        gridLayout.setVisibility(View.VISIBLE);
        additionText.setVisibility(View.VISIBLE);
        timerText.setVisibility(View.VISIBLE);
        scoreText.setVisibility(View.VISIBLE);

        score = 0;
        totalQuestions = 0;
        scoreText.setText(Integer.toString(score) + "/" + Integer.toString(totalQuestions));

        button.setVisibility(View.GONE);
        playAgainButton.setVisibility(View.GONE);

        generateSum();

        new CountDownTimer(30000,1000){
            public void onTick(long millisUntilFinished){
                timerText.setText(millisUntilFinished/1000 + "s");
                buttonsEnabled = true;
                setButtonVisibility(buttonsEnabled);
                //generateRandomNum();
                //generateSum();
            }

            public void onFinish(){
                gridLayout.setEnabled(false);
                showResult("Done!");
                playAgainButton.setVisibility(View.VISIBLE);
                buttonsEnabled = false;
                setButtonVisibility(buttonsEnabled);

            }
        }.start();

    }
    public void setButtonVisibility(boolean val){
        button0.setEnabled(val);
        button1.setEnabled(val);
        button2.setEnabled(val);
        button3.setEnabled(val);
    }
    /**Generates random numbers for addition values**/
    public int generateRandomNum(){
        Random random = new Random();

        int r = random.nextInt((max - min)+1) + min;
        Log.i("INFO", Integer.toString(r));
        return r;
    }

    /**The individual values are generated and displayed in the text view as an addition equation**/
    public void generateSum(){
        Random rand = new Random();
        int a = rand.nextInt(41);
                //generateRandomNum();
        int b = rand.nextInt(41);
                //generateRandomNum();
        int answer = a + b;

        options.clear();

        additionText.setText(a + " + " + b);

        positionOfCorrectAns = rand.nextInt(4);
        for(int i=0; i<4; i++){
            if(i == positionOfCorrectAns){
                options.add(a + b);
            }
            else {
                int wrongAnswer = rand.nextInt(41);
                while (wrongAnswer == a + b) {
                    wrongAnswer = rand.nextInt(41);
                }
                options.add(wrongAnswer);
            }

        }
        setOptions();
    }

    public void setOptions(){

        button0 = findViewById(R.id.button10);
        button1 = findViewById(R.id.button11);
        button2 = findViewById(R.id.button12);
        button3 = findViewById(R.id.button13);

        button0.setText(Integer.toString(options.get(0)));
        button1.setText(Integer.toString(options.get(1)));
        button2.setText(Integer.toString(options.get(2)));
        button3.setText(Integer.toString(options.get(3)));

    }

    public void buttonValue(View view){
        Button button = (Button) view;
        String buttonTag = (String) button.getTag();

        Log.i("Tag Info", button.getTag().toString());

        if(Integer.parseInt(buttonTag) == positionOfCorrectAns)
        {
            showResult("Correct");
            score++;
            Log.i("Score", Integer.toString(score));
        }
        else {
            showResult("Incorrect");
        }
        totalQuestions++;
        Log.i("Total", Integer.toString(totalQuestions));

        scoreText.setText(Integer.toString(score) + "/" + Integer.toString(totalQuestions));

    }

    public void showResult(String result){
        resultText = findViewById(R.id.resultText);
        resultText.setText(result);

        generateSum();
    }
}