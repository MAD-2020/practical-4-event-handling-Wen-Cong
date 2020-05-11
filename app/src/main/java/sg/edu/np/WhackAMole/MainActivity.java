package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button ButtonLeft;
    private Button ButtonMiddle;
    private Button ButtonRight;
    private TextView scores;
    private int point;
    private static final String TAG = "ButtonActivity";
    private static final String TAG1 = "Whack-A-Mole";

    /* Hint
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButtonLeft = (Button) findViewById(R.id.ButtonLeft);
        ButtonMiddle = (Button) findViewById(R.id.ButtonMiddle);
        ButtonRight = (Button) findViewById(R.id.ButtonRight);
        scores = (TextView) findViewById(R.id.Score);
        point = 0;
        Log.v(TAG, "Finished Pre-Initialisation!");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "Starting GUI!");
        setNewMole();

        ButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Button Left pressed!");
                doCheck(ButtonLeft);
                setNewMole();

            }
        });
        ButtonMiddle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "Button Middle Pressed!");
                doCheck(ButtonMiddle);
                setNewMole();
            }
        });
        ButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Button Right Pressed!");
                doCheck(ButtonRight);
                setNewMole();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        if (checkButton.getText() == "*") {
            point += 1;
            Log.v(TAG1, "Scores a Point\n" + point);
            scores.setText("" + point);
        } else if (checkButton.getText() != "*") {
            point -= 1;
            Log.v(TAG1, "Missed a Whack!\n" + point);
            scores.setText("" + point);
        }

        if(point == 10){
            nextLevelQuery();
        }
    }

    private void nextLevelQuery() {
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning! Insane Whack-A-Mole Coming!");
        builder.setMessage("Would you like to proceed to advance Whack-A-Mole?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG1, "User accepts!");
                nextLevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG1, "User declined!");
            }
        });
        Log.v(TAG1, "Advance option given to user!");
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void nextLevel() {
        /* Launch advanced page */
        Intent advanceWhackAMole = new Intent(this, Main2Activity.class);
        advanceWhackAMole.putExtra("Scores", point);
        startActivity(advanceWhackAMole);
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        if (randomLocation == 0) {
            ButtonLeft.setText("*");
        } else if (randomLocation != 0) {
            ButtonLeft.setText("o");
        }
        if (randomLocation == 1) {
            ButtonMiddle.setText("*");
        } else if (randomLocation != 1) {
            ButtonMiddle.setText("o");
        }
        if (randomLocation == 2) {
            ButtonRight.setText("*");
        } else if (randomLocation != 2) {
            ButtonRight.setText("o");
        }
    }
}