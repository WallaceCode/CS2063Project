package wallace.scott.fuctionalswipes;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Math.pow;
import static java.lang.Math.random;

/**
 * Author: Scott Wallace
 * This is the Activity that sets up and controls the game itself
 */
public class MainActivity extends AppCompatActivity {

    Button button;
    Button button5;
    TextView screen;
    GameManager game;
    double speedUP = 1;
    double count = 0;
    Thread gameMusicThread;
    SoundThread sThread;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * onCreate initializes the activity
     * Creates 4 onClickListeners for the 4 different buttons that can appear on screen
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = (TextView) findViewById(R.id.TextField);

        button = (Button) findViewById(R.id.button);
        button5 = (Button) findViewById(R.id.button5);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        button4.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        //button5.setVisibility(View.INVISIBLE);

        //sThread = new SoundThread(this, 1);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setAction(1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setAction(0);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setAction(2);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gameMusicThread = new Thread(new SoundThread(MainActivity.this,1));
                gameMusicThread.start();
                button5.performClick();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game = new GameManager();
                game.setSpeed(speedUP);
                game.execute();
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Changes the value of the speedup factor. This is called to decrease the time limit on each
     * interaction in game
     *
     * @param speed
     */
    public void setSpeedUp(double speed) {
        speedUP = speedUP * pow(.98, speed);
    }

    /**
     * Resets the speedUP value to its initial value of 1
     */
    public void resetSpeed() {
        speedUP = 1;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /*
    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d(functionalSwipes,"Action was DOWN");
                swipes(5);
                screen.setText("down Swipe");
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d(functionalSwipes,"Action was MOVE");
                swipes(10);
                screen.setText("Move Swipe");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d(functionalSwipes,"Action was UP");
                screen.setText("Up Swipe");
                swipes(15);
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d(functionalSwipes,"Action was CANCEL");
                swipes(20);
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d(functionalSwipes,"Movement occurred outside bounds " +
                        "of current screen element");
                swipes(25);
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }*/

    /**
     * This is the AsycTask that handles the game logic
     */
    public class GameManager extends AsyncTask<String, Void, String> {

        private double speed;
        private final String functionalSwipes = "Swipes";
        private int currentAction;
        private Boolean action[] = new Boolean[10];

        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        TextView text = (TextView) findViewById(R.id.TextField);

        /**
         * changes the value of the  Boolean array (action) to represent a user action
         *
         * @param choice
         */
        public void setAction(int choice) {
            action[choice] = true;
        }

        /**
         * Changes the speedUP factor for the game
         *
         * @param counter
         */
        public void setSpeed(double counter) {
            speed = counter;
        }

        /**
         * This function sets the screen up for the game
         * To start it disables and hides the start game button,
         * Then it sets the potential interactions, visible and interactable
         * As well as initializing the Boolean array action
         * Finally it calls Math.random() to retrieve a pseudo-random number
         * Which is then used to select a action for the user to perform
         * And set a text box on the screen to tell what action needs to be performed
         */
        protected void onPreExecute() {

            Log.i(functionalSwipes, "Iteration " + speed);
            for (int i = 0; i < 10; i++) {
                action[i] = false;
            }

            button.setEnabled(false);
            button.setVisibility(View.INVISIBLE);
            button4.setEnabled(true);
            button2.setEnabled(true);
            button3.setEnabled(true);
            button4.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);

            text.setText("");
            button4.setText("Swipe Up");
            button2.setText("Swipe Down");
            button3.setText("Tap Screen");

            double currVal;
            Log.i(functionalSwipes, "Entered GameController");
            currVal = random();
            Log.i(functionalSwipes, Double.toString(currVal));
            if (0 <= currVal && currVal < 0.2) {
                currentAction = 0;
                Log.i(functionalSwipes, "Please Swipe Down");
            } else if (0.2 <= currVal && currVal < 0.4) {
                currentAction = 1;
                Log.i(functionalSwipes, "Please Swipe Up");
            } else if (0.4 <= currVal && currVal < 0.6) {
                currentAction = 2;
                Log.i(functionalSwipes, "Please Tap");
            } else if (0.6 <= currVal && currVal < 0.8) {
                currentAction = 2;
                Log.i(functionalSwipes, "Please Tap");
            } else if (0.8 <= currVal) {
                currentAction = 2;
                Log.i(functionalSwipes, "Please Tap");
            }
            ReportAction(currentAction);
        }

        /**
         * Tells the User what action to perform
         *
         * @param currentAction
         */
        private void ReportAction(int currentAction) {
            String directions = "";
            switch (currentAction) {
                case 0:
                    directions = "Please Swipe Down";
                    break;
                case 1:
                    directions = "Please Swipe Up";
                    break;
                case 2:
                    directions = "Please Tap";
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
            }/*
            if(currentAction==0){
                directions = "Please Swipe Down";
            }
            else if(currentAction==1){
                directions = "Please Swipe Up";
            }
            else if(currentAction==2){
                directions = "Please Tap";
            }*/
            text.setText(directions);
        }

        /**
         * The game logic
         *
         * @param params
         * @return returns Success if the user succeeds, and failure otherwise
         */
        protected String doInBackground(String... params) {

            long startTime = System.currentTimeMillis();
            while (!action[currentAction] && (System.currentTimeMillis() - startTime) < 5000 * speed) {
                for (int i = 0; i < 10; i++) {
                    if (action[i] && i != currentAction) {
                        return "Failure";
                    }
                }
            }
            if (action[currentAction]) {
                return "Success";
            }
            return "Time Out";
        }

        /**
         * returns the screen to how it was before the game was launched
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {

            button.setEnabled(true);
            button.setVisibility(View.VISIBLE);
            button4.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.INVISIBLE);
            button3.setVisibility(View.INVISIBLE);
            button.setText(result);

            if (result.equals("Success")) {
                Log.i(functionalSwipes, "Next Level");
                count++;
                setSpeedUp(count);
                button5.performClick();
            } else {
                gameMusicThread.interrupt();
                count = 0;
                Thread thread = new Thread(new SoundThread(MainActivity.this, 2));
                thread.start();
                resetSpeed();
            }
        }
    }
}