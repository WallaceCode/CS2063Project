package wallace.scott.fuctionalswipes;

import android.os.AsyncTask;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Math.pow;
import static java.lang.Math.random;

/**
 * Author: Scott Wallace and Maxime Thibodeau
 * This is the Activity that sets up and controls the game itself
 *
 */
public class GameActivity extends AppCompatActivity {

    String DEBUG_TAG = "";
    private GestureDetectorCompat mDetector;
    Button button;
    TextView screen;
    TextView scoreView;
    TextView trackPad;
    int status = 0;
    int score = 0;
    GameManager game;
    double speedUP = 1;
    double count = 0;

    /**
     * onCreate initializes the activity
     * Creates 4 onClickListeners for the 4 different buttons that can appear on screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        trackPad = (TextView) findViewById(R.id.trackPad);
        trackPad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mDetector.onTouchEvent(event);
            }
        });

        screen = (TextView) findViewById(R.id.TextField);
        scoreView = (TextView) findViewById(R.id.scoreView);
        scoreView.setText("Score: 0" );
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game = new GameManager();
                game.setSpeed(speedUP);
                game.execute();
            }
        });
    }

    public void addToScore(){
        scoreView.setText("Score: " + ++score);
    }

    public void setAction(int choice){
        game.setAction(choice);
    }

    /**
     * Changes the value of the speedup factor. This is called to decrease the time limit on each
     * interaction in game
     * @param speed
     */
    public void setSpeedUp(double speed){
        speedUP = speedUP * pow(.98,speed);
    }

    /**
     * Resets the speedUP value to its initial value of 1
     */
    public void resetSpeed(){
        speedUP = 1;
    }

    /**
     * This is the AsycTask that handles the game logic
     */
    public class GameManager extends AsyncTask<String, Void, String> {

        private double speed;
        private final String functionalSwipes = "Swipes";
        private int currentAction;
        private Boolean action[] = new Boolean[10];

        Button button = (Button) findViewById(R.id.button);
        TextView text = (TextView) findViewById(R.id.TextField);

        /**
         * changes the value of the  Boolean array (action) to represent a user action
         * @param choice
         */
        public void setAction(int choice) {
            action[choice] = true;
        }

        /**
         * Changes the speedUP factor for the game
         * @param counter
         */
        public void setSpeed(double counter){
            speed = counter;
        }

        public void updateScore(){
            scoreView.setText("Score: " + score);
        }

        public void resetScore(){
            score = 0;
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

            Log.i(functionalSwipes, "Iteration "+ speed);
            for(int i=0;i<10;i++){
                action[i] = false;
            }

            button.setEnabled(false);
            button.setVisibility(View.INVISIBLE);

            text.setText("");

            double currVal;
            Log.i(functionalSwipes, "Entered GameController");
            currVal = random();
            Log.i(functionalSwipes, Double.toString(currVal));
            if (0 <= currVal && currVal < 0.2) {
                currentAction = 0;
                Log.i(functionalSwipes, "Tap");
            } else if (0.2 <= currVal && currVal < 0.4) {
                currentAction = 1;
                Log.i(functionalSwipes, "Swipe Up");
            } else if (0.4 <= currVal && currVal < 0.6) {
                currentAction = 2;
                Log.i(functionalSwipes, "Swipe Down");
            } else if (0.6 <= currVal && currVal < 0.8) {
                currentAction = 3;
                Log.i(functionalSwipes, "Swipe Left");
            } else if (0.8 <= currVal) {
                currentAction = 4;
                Log.i(functionalSwipes, "Swipe Right");
            }
            ReportAction(currentAction);
        }

        /**
         * Tells the User what action to perform
         * @param currentAction
         */
        private void ReportAction(int currentAction){
            String directions = "";
            switch(currentAction){
                case 0:
                    directions = "Tap";
                    break;
                case 1:
                    directions = "Swipe Up";
                    break;
                case 2:
                    directions = "Swipe Down";
                    break;
                case 3:
                    directions = "Swipe Left";
                    break;
                case 4:
                    directions = "Swipe Right";
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
            }
            text.setText(directions);
        }

        /**
         * The game logic
         * @param params
         * @return returns Success if the user succeeds, and failure otherwise
         */
        protected String doInBackground(String... params) {

            long startTime = System.currentTimeMillis();
            while(!action[currentAction] && (System.currentTimeMillis() - startTime) < 5000*speed){
                for(int i=0;i<10;i++){
                    if(action[i] && i!=currentAction){
                        resetScore();
                        return "Failure";
                    }
                }
            }
            if(action[currentAction]) {
                return "Success";
            }
            resetScore();
            return "Time Out";
        }

        /**
         * returns the screen to how it was before the game was launched
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {

            button.setEnabled(true);
            button.setVisibility(View.VISIBLE);

            button.setText(result);

            if (result.equals("Success")) {
                Log.i(functionalSwipes, "new Game");
                count++;
                addToScore();
                setSpeedUp(count);
                button.performClick();
            } else {
                count = 0;
                resetSpeed();
            }
        }
    }
    //Added by Max
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            //Log.d(DEBUG_TAG, "onDown");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            if(Math.abs(velocityX) < Math.abs(velocityY)){
                if(velocityY < 0){
                    Log.d(DEBUG_TAG, " UP: X " + Float.toString(velocityX)
                            + " Y " + Float.toString(velocityY));
                    status = 1;
                    setAction(1);
                }
                else{
                    Log.d(DEBUG_TAG, " DOWN: X " + Float.toString(velocityX)
                            + " Y " + Float.toString(velocityY));
                    status = 2;
                    setAction(2);
                }
            }else{
                if(velocityX < 0){
                    Log.d(DEBUG_TAG, " LEFT: X " + Float.toString(velocityX)
                            + " Y " + Float.toString(velocityY));
                    status = 3;
                    setAction(3);
                }
                else{
                    Log.d(DEBUG_TAG, " RIGHT: X " + Float.toString(velocityX)
                            + " Y " + Float.toString(velocityY));
                    status = 4;
                    setAction(4);
                }
            }
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent event) {
            Log.d(DEBUG_TAG, "TAP CONFIRMED");
            status = 0;
            setAction(0);
            return true;
        }
    }
}