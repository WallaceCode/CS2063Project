package wallace.scott.fuctionalswipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HighScores extends AppCompatActivity {


    private static baseList baseList;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;
    String TAG = "Max, look here ----> ";
    private ArrayList<String> arraylist;
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

    public static void setBaseList(baseList list) {
        baseList = list;
    }

    public static void initializeFirebase(){

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
    }

    public boolean addRecord(String player, int score) {

        int rank = findPosition(score);
        String[] newPlayer = {Integer.toString(score), player};
        baseList.addToList(rank, newPlayer);
        //myRef.setValue(newPlayer);
/*
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                arraylist.add(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/

        return true;
    }

    private void updateScreen() {
        ArrayList<String> arrayList = new ArrayList<>();
        String placeHolder;
        int i = 0;

        while (baseList.getSize() > i) {
            placeHolder = baseList.getItem(i)[0];
            placeHolder += " , ";
            placeHolder += baseList.getItem(i)[1];
            if (i == 0) {
                placeHolder += " , ";
                placeHolder += baseList.getItem(i)[2];
            } else {
                String temp = placeHolder;
                placeHolder = Integer.toString(i) + " , ";
                placeHolder += temp;
            }
            arrayList.add(placeHolder);
            i++;
        }
        ListView listview = (ListView) findViewById(R.id.scorelistview);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.txtitem, arrayList);
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
