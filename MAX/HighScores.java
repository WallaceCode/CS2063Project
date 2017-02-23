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

    private ArrayList<String> arraylist;
    private ArrayAdapter<String> adapter;
    private EditText txtInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listview = (ListView)findViewById(R.id.scorelistview);
        String[] items = {"Rank","Score","Player Name"};
        arraylist = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.txtitem,arraylist);
        listview.setAdapter(adapter);
        txtInput=(EditText)findViewById(R.id.txtinput);
        Button btAdd = (Button)findViewById(R.id.btadd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newItem = txtInput.getText().toString();
                arraylist.add(newItem);
                adapter.notifyDataSetChanged();
            }
        });


    }

}
