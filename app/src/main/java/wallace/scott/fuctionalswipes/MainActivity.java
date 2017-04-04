package wallace.scott.fuctionalswipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static baseList list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = new baseList();
        HighScores.setBaseList(list);
        HighScores.initializeFirebase();

        Button plybtn = (Button) findViewById(R.id.playbtn);
        plybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
        Button highscrbtn = (Button) findViewById(R.id.highscoresbtn);
        highscrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HighScores.class);
                startActivity(intent);
            }
        });

    }
}
