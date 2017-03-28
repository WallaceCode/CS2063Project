package wallace.scott.fuctionalswipes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class HighScores extends AppCompatActivity {

    private static baseList baseList;
    private ArrayAdapter<String> adapter;
    private EditText txtInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        updateScreen();
    }

    public static void setBaseList(baseList list){
        baseList = list;
    }

    public boolean addRecord(String player, int score){

        int rank = findPosition(score);
        String[] newPlayer = {Integer.toString(score), player};
        baseList.addToList(rank, newPlayer);
        //updateScreen();
        return true;
    }

    private void updateScreen(){
        ArrayList<String> arrayList = new ArrayList<>();
        String placeHolder;
        int i=0;

        while(baseList.getSize()>i){
            placeHolder = baseList.getItem(i)[0];
            placeHolder += " , ";
            placeHolder += baseList.getItem(i)[1];
            if(i==0) {
                placeHolder += " , ";
                placeHolder += baseList.getItem(i)[2];
            }
            else{
                String temp = placeHolder;
                placeHolder = Integer.toString(i) + " , ";
                placeHolder += temp;
            }
            arrayList.add(placeHolder);
            i++;
        }
        ListView listview = (ListView)findViewById(R.id.scorelistview);
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.txtitem,arrayList);
        listview.setAdapter(adapter);
    }

    public int findPosition(int score){
        int rank = Integer.MAX_VALUE;
        int i = 1;
        while(baseList.getSize()>i && Integer.parseInt(baseList.getItem(i)[0])>=score){
            i++;
        }
        rank = i;
        return rank;
    }

}
