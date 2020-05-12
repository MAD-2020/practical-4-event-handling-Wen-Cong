package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */
    private final String TAG = "Whack-A-Mole 2.0";
    private Integer advancedScore;
    private TextView score;
    int previousLocation;
    CountDownTimer countDownTimer;
    CountDownTimer placeMoleTimer;



    private void readyTimer(){

        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                Log.v(TAG, "Ready Countdown!" + l/1000);
                Toast.makeText(Main2Activity.this, "Get Ready in " + l/1000 + " Seconds", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                Toast.makeText(Main2Activity.this, "GO!", Toast.LENGTH_SHORT).show();
                placeMoleTimer();
            }
        };
        countDownTimer.start();

    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        placeMoleTimer = new CountDownTimer(50000, 1000) {
            @Override
            public void onTick(long l) {
                Log.v(TAG, "New Mole Location!");
                setNewMole();
            }

            @Override
            public void onFinish() {
                placeMoleTimer.start();
            }
        };
        placeMoleTimer.start();
    }
    private static final int[] BUTTON_IDS = {
        /* HINT:
            Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
            You may use if you wish to change or remove to suit your codes.*/
        R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        advancedScore = (Integer) getIntent().getIntExtra("Score", 0);
        Log.v(TAG, "Current User Score: " + advancedScore);
        score = findViewById(R.id.scoreview);
        score.setText("" + advancedScore);
        readyTimer();

        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            final Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v(TAG, "Button "+ button.getId() +" Clicked");
                    doCheck(button);

                }
            });
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
    }
    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        if (checkButton.getText() == "*") {
            advancedScore += 1;
            Log.v(TAG, "Scores a Point\n" + advancedScore);
            score.setText("" + advancedScore);
            setNewMole();
        } else if (checkButton.getText() != "*") {
            advancedScore -= 1;
            Log.v(TAG, "Missed a Whack!\n" + advancedScore);
            score.setText("" + advancedScore);
            setNewMole();
        }
    }

    public void setNewMole()
    {
        int randomLocation = 0;
        Random ran = new Random();
        randomLocation = ran.nextInt(8);
        int ranButton = (int) Array.get(BUTTON_IDS, randomLocation);
        Button b = findViewById(ranButton);
        b.setText("*");
        int previousButtonId = (int) Array.get(BUTTON_IDS, previousLocation);
        Button previousButton = findViewById(previousButtonId);
        previousButton.setText("o");
        previousLocation = randomLocation;
    }
}

