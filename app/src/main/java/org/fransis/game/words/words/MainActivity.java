package org.fransis.game.words.words;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textIntentos;
    ArrayAdapter<String> adapterResult = null;
    ArrayAdapter<String> adapter = null;
    GridView gridviewResult = null;
    private Game myGame = new Game();
    GridView gridview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridview = (GridView) findViewById(R.id.gridview);
        gridviewResult = (GridView) findViewById(R.id.gridview_result);
        textIntentos = (TextView) findViewById(R.id.textIntento);
        textIntentos.setText(myGame.getnTry() + "");

        String word = myGame.getWord();
        adapterResult = new ArrayAdapter<>(this, R.layout.item, R.id.item_id);
        for (int i= 0; i< word.length(); i++){
            adapterResult.add(word.substring(i, i + 1));
        }
        gridviewResult.setAdapter(adapterResult);

        adapter = new ArrayAdapter<>(this, R.layout.item, R.id.item_id);
        String words = myGame.getAlphabet();
        for (int i=0; i<words.length();i++)
                adapter.add(words.substring(i,i+1));
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                TextView tv = (TextView) v.findViewById(R.id.item_id);
                tv.setBackgroundResource(R.drawable.button_click);
                String letter = tv.getText().toString();

                myGame.newTry(letter);

            }
        });

        myGame.setCallback(new GameCallback() {
            @Override
            public void gameOver() {
                Toast.makeText(MainActivity.this, "Game Over!",Toast.LENGTH_LONG).show();
                gridview.setEnabled(false);
            }

            @Override
            public void wellDone() {
                Toast.makeText(MainActivity.this, "Well Done!",Toast.LENGTH_LONG).show();
                gridview.setEnabled(false);
            }

            @Override
            public void letter(String word) {
                adapterResult.clear();
                for (int i = 0;i<word.length(); i++ ) {
                    adapterResult.add(word.substring(i, i + 1));
                }
            }

            @Override
            public void fail(int tryAvailable) {
                textIntentos.setText(tryAvailable + "");
            }
        });
    }
}

