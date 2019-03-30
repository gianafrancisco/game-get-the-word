package org.fransis.game.words;

import android.animation.ValueAnimator;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import org.fransis.game.words.words.R;

public class GameFragment extends Fragment implements GameCallback {

    public static final String SCORE_FORMAT = "%1d";
    LinearLayout hudAttempts = null;
    TextView hudAttemptsText = null;
    TextView hudScore = null;
    ArrayAdapter<String> adapterResult = null;
    ArrayAdapter<String> adapter = null;
    GridView gridviewResult = null;
    Game myGame = null;
    Player player = null;
    GridView gridview = null;
    LevelRepository levels = null;
    TextView tvCurrent = null;
    AnimationSet animationIn = null;
    AnimationSet animationOut = null;
    ValueAnimator scoreAnimator = null;
    Context mContext = null;
    AdapterView.OnItemClickListener listenerClickChar = null;
    int lastScore = 0;
    int prevScore = 0;
    ValueAnimator.AnimatorUpdateListener listenerScoreAnimation = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1000);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(500);

        listenerScoreAnimation = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                String score = String.format(SCORE_FORMAT, (int)animation.getAnimatedValue());
                hudScore.setText(score);
            }
        };

        animationIn = new AnimationSet(false);
        animationIn.addAnimation(fadeIn);
        animationOut = new AnimationSet(false);
        animationOut.addAnimation(fadeOut);

        levels = new MemoryRepository();

        listenerClickChar = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                tvCurrent = (TextView) v.findViewById(R.id.item_id);
                String letter = tvCurrent.getText().toString();

                myGame.newTry(letter);

            }
        };

        player = new Player();
        myGame = new Game(levels.getLevel());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_game, container, false);

        gridview = (GridView) inflate.findViewById(R.id.gridview);
        gridviewResult = (GridView) inflate.findViewById(R.id.gridview_result);
        hudAttempts = (LinearLayout) inflate.findViewById(R.id.hud_attempts);
        hudScore = (TextView) inflate.findViewById(R.id.hud_score);
        hudAttemptsText = (TextView) inflate.findViewById(R.id.hud_attempts_text);
        gridview.setOnItemClickListener(listenerClickChar);

        String score = String.format(SCORE_FORMAT, player.getScore());
        hudScore.setText(score);
        startLevel();

        return inflate;
    }

    public void restartLevel(){
        Level level = levels.getLevel();
        myGame = new Game(level);
        startLevel();
    }

    public void nextLevel(){
        Level level = levels.getNextLevel();
        myGame = new Game(level);
        startLevel();
    }

    private void startLevel() {
        drawNTry();
        hudAttempts.setVisibility(View.VISIBLE);
        String word = myGame.getWord();
        adapterResult = new ArrayAdapter<>(mContext, R.layout.item, R.id.item_id);
        for (int i= 0; i< word.length(); i++){
            adapterResult.add(word.substring(i, i + 1));
        }
        gridviewResult.setAdapter(adapterResult);

        adapter = new ArrayAdapter<>(mContext, R.layout.item, R.id.item_id);
        String words = myGame.getAlphabet();
        for (int i=0; i<words.length();i++)
            adapter.add(words.substring(i,i+1));
        gridview.setAdapter(adapter);
        gridview.setEnabled(true);
        myGame.setCallback(this);
    }

    private void drawNTry(){
        hudAttemptsText.setText(String.format(SCORE_FORMAT,myGame.getNTry()));
    }

    private void showDialog(DialogFragment dialog){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        dialog.show(ft, "dialog");
    }

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
        drawNTry();
    }

    @Override
    public void score(int score) {
        lastScore = score;
        prevScore = player.getScore();
        player.addScore(lastScore);
        scoreAnimator = ValueAnimator.ofInt(prevScore, player.getScore());
        scoreAnimator.setDuration(1000);
        scoreAnimator.addUpdateListener(listenerScoreAnimation);
        scoreAnimator.start();
    }
}

