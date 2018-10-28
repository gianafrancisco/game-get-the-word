package org.fransis.game.words.words;

import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameFragment extends Fragment {

    LinearLayout hud;
    ArrayAdapter<String> adapterResult = null;
    ArrayAdapter<String> adapter = null;
    GridView gridviewResult = null;
    Game myGame = null;
    GridView gridview = null;
    GameCallback callback = null;
    LevelRepository levels = null;
    TextView tvCurrent = null;
    AnimationSet animationIn = null;
    AnimationSet animationOut = null;
    Context mContext = null;
    AdapterView.OnItemClickListener listenerClickChar = null;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1000);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(500);

        animationIn = new AnimationSet(false);
        animationIn.addAnimation(fadeIn);
        animationOut = new AnimationSet(false);
        animationOut.addAnimation(fadeOut);

        levels = new MemoryRepository();


        callback = new GameCallback() {

            @Override
            public void gameOver() {
                showDialog(DialogLevel.newInstance(DialogLevel.GAME_OVER));
            }

            @Override
            public void wellDone() {
                showDialog(DialogLevel.newInstance(DialogLevel.WELL_DONE));
            }

            @Override
            public void character(String word) {
                tvCurrent.setBackgroundResource(R.drawable.good);
                adapterResult.clear();
                for (int i = 0; i < word.length(); i++) {
                    adapterResult.add(word.substring(i, i + 1));
                }
            }

            @Override
            public void fail(int tryAvailable) {
                tvCurrent.setBackgroundResource(R.drawable.wrong);
                drawNTry(myGame.getnTry());
            }
        };

        listenerClickChar = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                tvCurrent = (TextView) v.findViewById(R.id.item_id);
                String letter = tvCurrent.getText().toString();

                myGame.newTry(letter);

            }
        };

        myGame = new Game(levels.getLevel());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_game, container, false);

        gridview = (GridView) inflate.findViewById(R.id.gridview);
        gridviewResult = (GridView) inflate.findViewById(R.id.gridview_result);
        hud = (LinearLayout) inflate.findViewById(R.id.hud);

        gridview.setOnItemClickListener(listenerClickChar);

        iniciarNivel(levels.getLevel());

        return inflate;
    }


    public void reiniciarNivel(){
        Level level = levels.getLevel();
        myGame = new Game(level);
        iniciarNivel(level);
    }

    public void siguienteNivel(){
        Level level = levels.getNextLevel();
        myGame = new Game(level);
        iniciarNivel(level);
    }

    private void iniciarNivel(Level level) {
        hud.removeAllViews();
        drawNTry(myGame.getnTry());
        hud.setVisibility(View.VISIBLE);
        String word = myGame.getWord();
        adapterResult = new ArrayAdapter<String>(mContext, R.layout.item, R.id.item_id);
        for (int i= 0; i< word.length(); i++){
            adapterResult.add(word.substring(i, i + 1));
        }
        gridviewResult.setAdapter(adapterResult);

        adapter = new ArrayAdapter<String>(mContext, R.layout.item, R.id.item_id);
        String words = myGame.getAlphabet();
        for (int i=0; i<words.length();i++)
            adapter.add(words.substring(i,i+1));
        gridview.setAdapter(adapter);
        gridview.setEnabled(true);
        myGame.setCallback(callback);
    }

    private void drawNTry(int nTry){
        if(hud.getChildCount() > 0){
            View iv =  hud.getChildAt(nTry);
            iv.startAnimation(animationOut);
            hud.removeViewAt(nTry);
        }else{
            for(int i=0; i < nTry; i++){
                ImageView heart = new ImageView(mContext);
                heart.setImageResource(R.drawable.heart);
                hud.addView(heart);
                heart.startAnimation(animationIn);
            }
        }
    }

    private void showDialog(DialogFragment dialog){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        dialog.show(ft, "dialog");
    }

}

