package org.fransis.game.words.words;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
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
    TextView tvCurrent = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        gridview = (GridView) findViewById(R.id.gridview);
        gridviewResult = (GridView) findViewById(R.id.gridview_result);
        //textIntentos = (TextView) findViewById(R.id.textIntento);
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
                tvCurrent.setBackgroundResource(R.drawable.good);
                adapterResult.clear();
                for (int i = 0; i < word.length(); i++) {
                    adapterResult.add(word.substring(i, i + 1));
                }
            }

            @Override
            public void fail(int tryAvailable) {
                //textIntentos.setText(tryAvailable + "");
                tvCurrent.setBackgroundResource(R.drawable.wrong);
                drawNTry(myGame.getnTry());
            }
        };

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                tvCurrent = (TextView) v.findViewById(R.id.item_id);
                String letter = tvCurrent.getText().toString();

                myGame.newTry(letter);

            }
        });

        iniciarNivel(levels.getLevel());

    }

    private void iniciarNivel(Level level) {
        myGame = new Game(level);
        //textIntentos.setText(myGame.getnTry() + "");
        drawNTry(myGame.getnTry());
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

    private void drawNTry(int nTry){
        hud.removeAllViews();
        for(int i=0; i < nTry; i++){
            ImageView heart = new ImageView(this);
            heart.setImageResource(R.drawable.heart);
            hud.addView(heart);
        }
    }
}

