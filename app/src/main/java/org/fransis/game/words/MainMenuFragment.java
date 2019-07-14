package org.fransis.game.words;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.fransis.game.words.words.R;

public class MainMenuFragment extends Fragment implements View.OnClickListener {

    public interface OnFragmentInteractionListener {
        void onPlayButtonClicked();
        void onExitButtonClicked();
        void onResumeButtonClicked();
        void onSignInButtonClicked();
        void onSignOutButtonClicked();
        void onShowLeaderboardClicked();
    }

    private OnFragmentInteractionListener mListener;
    private View mSignInButtonView;
    private View mSignOutButtonView;
    private View mShowLeaderboardButtonView;
    private boolean mShowSignInButton = true;
    private boolean mShowLeaderboardButton = false;
    private TextView mGreetingTextView;
    private String mGreeting = "";
    private boolean mShowResumeGame = false;
    private Button mResumeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_main_menu, container, false);

        Button play = inflate.findViewById(R.id.btn_play);
        play.setOnClickListener(this);
        Button exit = inflate.findViewById(R.id.btn_exit);
        exit.setOnClickListener(this);
        mResumeButton = inflate.findViewById(R.id.btn_resume);
        mResumeButton.setOnClickListener(this);

        mGreetingTextView = inflate.findViewById(R.id.text_greeting);
        mSignInButtonView = inflate.findViewById(R.id.sign_in_button);
        mSignInButtonView.setOnClickListener(this);
        mSignOutButtonView = inflate.findViewById(R.id.sign_out_button);
        mSignOutButtonView.setOnClickListener(this);
        mShowLeaderboardButtonView = inflate.findViewById(R.id.btn_leaderboard);
        mShowLeaderboardButtonView.setOnClickListener(this);

        updateUI();
        return inflate;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void updateUI() {
        mGreetingTextView.setText(mGreeting);
        mSignInButtonView.setVisibility(mShowSignInButton ? View.VISIBLE : View.GONE);
        mSignOutButtonView.setVisibility(mShowSignInButton ? View.GONE : View.VISIBLE);
        mResumeButton.setVisibility(mShowResumeGame ? View.VISIBLE : View.GONE);
        mShowLeaderboardButtonView.setVisibility(mShowLeaderboardButton ? View.VISIBLE : View.GONE);
    }

    public void setShowSignInButton(boolean showSignInButton) {
        mShowSignInButton = showSignInButton;
        updateUI();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                mListener.onPlayButtonClicked();
                break;
            case R.id.btn_exit:
                mListener.onExitButtonClicked();
                break;
            case R.id.btn_resume:
                mListener.onResumeButtonClicked();
                break;
            case R.id.sign_in_button:
                mListener.onSignInButtonClicked();
                break;
            case R.id.sign_out_button:
                mListener.onSignOutButtonClicked();
                break;
            case R.id.btn_leaderboard:
                mListener.onShowLeaderboardClicked();
                break;
        }
    }
    public void setGreeting(String greeting) {
        mGreeting = greeting;
        updateUI();
    }

    public void setShowResumeGame(boolean mShowResumeGame) {
        this.mShowResumeGame = mShowResumeGame;
        updateUI();
    }

    public void setShowLeaderboardButton(boolean mShowLeaderboardButton) {
        this.mShowLeaderboardButton = mShowLeaderboardButton;
        updateUI();
    }
}
