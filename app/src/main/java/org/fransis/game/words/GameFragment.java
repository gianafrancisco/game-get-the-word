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
    private LinearLayout mHudAttempts = null;
    private TextView mHudAttemptsText = null;
    private TextView mHudScore = null;
    private ArrayAdapter<String> mAdapterResult = null;
    private AlphabetAdapter mAdapter = null;
    private GridView mGridViewResult = null;
    private Game mGame = null;
    private Player mPlayer = null;
    private GridView mGridView = null;
    private TextView mTvCurrent = null;
    private AnimationSet mAnimationIn = null;
    private AnimationSet mAnimationOut = null;
    private ValueAnimator mScoreAnimator = null;
    private Context mContext = null;
    private AdapterView.OnItemClickListener mListenerClickChar = null;
    private int mLastScore = 0;
    private int mPrevScore = 0;
    private ValueAnimator.AnimatorUpdateListener mListenerScoreAnimation = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1000);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(500);

        mListenerScoreAnimation = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                String score = String.format(SCORE_FORMAT, (int)animation.getAnimatedValue());
                mHudScore.setText(score);
            }
        };

        mAnimationIn = new AnimationSet(false);
        mAnimationIn.addAnimation(fadeIn);
        mAnimationOut = new AnimationSet(false);
        mAnimationOut.addAnimation(fadeOut);

        mListenerClickChar = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                mTvCurrent = (TextView) v.findViewById(R.id.item_id);
                String letter = mTvCurrent.getText().toString();

                mGame.newTry(letter);
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_game, container, false);

        mGridView = (GridView) inflate.findViewById(R.id.gridview);
        mGridViewResult = (GridView) inflate.findViewById(R.id.gridview_result);
        mHudAttempts = (LinearLayout) inflate.findViewById(R.id.hud_attempts);
        mHudScore = (TextView) inflate.findViewById(R.id.hud_score);
        mHudAttemptsText = (TextView) inflate.findViewById(R.id.hud_attempts_text);
        mGridView.setOnItemClickListener(mListenerClickChar);

        String score = String.format(SCORE_FORMAT, mPlayer.getScore());
        mHudScore.setText(score);
        startLevel();

        return inflate;
    }

    public void startLevel() {
        drawNTry();
        mHudAttempts.setVisibility(View.VISIBLE);
        String word = mGame.getWord();
        mAdapterResult = new ArrayAdapter<>(mContext, R.layout.item, R.id.item_id);
        for (int i= 0; i< word.length(); i++){
            mAdapterResult.add(word.substring(i, i + 1));
        }
        mGridViewResult.setAdapter(mAdapterResult);
        mAdapter = new AlphabetAdapter(mContext, mGame.getAlphabetStatus());

        mGridView.setAdapter(mAdapter);
        mGridView.setEnabled(true);
    }

    private void drawNTry(){
        mHudAttemptsText.setText(String.format(SCORE_FORMAT, mGame.getNTry()));
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
        mAdapter.notifyDataSetChanged();
        mAdapterResult.clear();
        for (int i = 0; i < word.length(); i++) {
            mAdapterResult.add(word.substring(i, i + 1));
        }
    }

    @Override
    public void fail(int tryAvailable) {
        mAdapter.notifyDataSetChanged();
        drawNTry();
    }

    @Override
    public void score(int score) {
        mLastScore = score;
        mPrevScore = mPlayer.getScore();
        mPlayer.addScore(mLastScore);
        mScoreAnimator = ValueAnimator.ofInt(mPrevScore, mPlayer.getScore());
        mScoreAnimator.setDuration(1000);
        mScoreAnimator.addUpdateListener(mListenerScoreAnimation);
        mScoreAnimator.start();
    }

    public void setGame(Game mGame) {
        this.mGame = mGame;
        this.mGame.setCallback(this);
    }

    public void setPlayer(Player mPlayer) {
        this.mPlayer = mPlayer;
    }

}

