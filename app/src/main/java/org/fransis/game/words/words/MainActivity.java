package org.fransis.game.words.words;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textIntentos;
    LinearLayout hud;
    ArrayAdapter<String> adapterResult = null;
    ArrayAdapter<String> adapter = null;
    GridView gridviewResult = null;
    Game myGame = null;
    GridView gridview = null;
    Button btnVolverIntentar = null;
    Button btnNextLevel = null;
    GameCallback callback = null;
    LevelRepository levels = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridview = (GridView) findViewById(R.id.gridview);
        gridviewResult = (GridView) findViewById(R.id.gridview_result);
        textIntentos = (TextView) findViewById(R.id.textIntento);
        btnVolverIntentar = (Button) findViewById(R.id.btnReintentar);
        btnNextLevel = (Button) findViewById(R.id.btnSiguienteNivel);
        hud = (LinearLayout) findViewById(R.id.hud);
        levels = new MemoryRepository();

        btnVolverIntentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarNivel(levels.getLevel());
            }
        });

        btnNextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarNivel(levels.getNextLevel());
            }
        });

        callback = new GameCallback() {
            @Override
            public void gameOver() {
                Toast.makeText(MainActivity.this, "Game Over!", Toast.LENGTH_LONG).show();
                gridview.setEnabled(false);
                hud.setVisibility(View.GONE);
                btnVolverIntentar.setVisibility(View.VISIBLE);

            }

            @Override
            public void wellDone() {
                Toast.makeText(MainActivity.this, "Well Done!", Toast.LENGTH_LONG).show();
                gridview.setEnabled(false);
                hud.setVisibility(View.GONE);
                btnNextLevel.setVisibility(View.VISIBLE);
            }

            @Override
            public void letter(String word) {
                adapterResult.clear();
                for (int i = 0; i < word.length(); i++) {
                    adapterResult.add(word.substring(i, i + 1));
                }
            }

            @Override
            public void fail(int tryAvailable) {
                textIntentos.setText(tryAvailable + "");
            }
        };

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                TextView tv = (TextView) v.findViewById(R.id.item_id);
                tv.setBackgroundResource(R.drawable.button_click);
                String letter = tv.getText().toString();

                myGame.newTry(letter);

            }
        });

        iniciarNivel(levels.getLevel());



    }

    private void iniciarNivel(Level level) {
        myGame = new Game(level);
        textIntentos.setText(myGame.getnTry() + "");
        hud.setVisibility(View.VISIBLE);
        btnVolverIntentar.setVisibility(View.GONE);
        btnNextLevel.setVisibility(View.GONE);
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
        gridview.setEnabled(true);
        myGame.setCallback(callback);
    }
}

