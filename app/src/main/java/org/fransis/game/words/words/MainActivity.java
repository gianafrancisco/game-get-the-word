package org.fransis.game.words.words;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView textResult;
    String words = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
    String word = "Chucrut".toUpperCase();
    StringBuilder sb = null;
    ArrayAdapter<String> adapter_result = null;
    int numTry = 5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridView gridview = (GridView) findViewById(R.id.gridview);
        GridView gridviewResult = (GridView) findViewById(R.id.gridview_result);

        sb = new StringBuilder();
        adapter_result = new ArrayAdapter<>(this, R.layout.item, R.id.item_id);
        for (int i= 0; i< word.length(); i++){
            sb.append("_");
            adapter_result.add("_");
        }
        gridviewResult.setAdapter(adapter_result);


        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item, R.id.item_id);
        for (int i=0; i<words.length();i++)
                adapter.add(words.substring(i,i+1));
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                TextView tv = (TextView) v.findViewById(R.id.item_id);
                tv.setBackgroundResource(R.drawable.button_click);
                String letter = tv.getText().toString();
                //String word = textResult.getText().toString() + letter;
                if(numTry > 0){
                    if(sb.indexOf(letter) < 0){
                        boolean found = false;
                        int index = word.indexOf(letter);
                        while( index >= 0 ) {
                            found = true;
                            sb.replace(index, index + 1, letter);
                            index = word.indexOf(letter, index + 1);
                        }
                        if(found){
                            adapter_result.clear();
                            for (int i = 0;i<sb.length(); i++ ) {
                                adapter_result.add(sb.substring(i, i + 1));
                            }
                            if(sb.indexOf("_") < 0){
                                Toast.makeText(MainActivity.this, "Well Done!",Toast.LENGTH_LONG).show();
                                gridview.setEnabled(false);
                            }
                        }else{
                            numTry--;
                        }
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Game Over!",Toast.LENGTH_LONG).show();
                    gridview.setEnabled(false);
                }
            }
        });
    }
}
