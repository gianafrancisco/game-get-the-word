package org.fransis.game.words;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.fransis.game.words.words.R;

public class MainMenuFragment extends Fragment implements View.OnClickListener {

    public interface OnFragmentInteractionListener {
        void onPlayButtonClicked();
        void onExitButtonClicked();
        void onResumeButtonClicked();
        void onSignInButtonClicked();
        void onSignOutButtonClicked();
    }

    private OnFragmentInteractionListener mListener;
    private View mSignInBarView;
    private View mSignOutBarView;
    private boolean mShowSignInButton = true;
    private TextView mGreetingTextView;
    private ImageView mPlayerPicture;
    private Uri mPlayerPictureUri = null;
    private String mGreeting;

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
        Button resume = inflate.findViewById(R.id.btn_resume);
        resume.setOnClickListener(this);

        mGreetingTextView = inflate.findViewById(R.id.text_greeting);
        mSignInBarView = inflate.findViewById(R.id.sign_in_bar);
        mSignOutBarView = inflate.findViewById(R.id.sign_out_bar);
        mPlayerPicture = inflate.findViewById(R.id.player_picture);

        inflate.findViewById(R.id.sign_in_button).setOnClickListener(this);
        inflate.findViewById(R.id.sign_out_button).setOnClickListener(this);

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
        if(mPlayerPictureUri != null) {
            mPlayerPicture.setImageURI(mPlayerPictureUri);
        }
        mGreetingTextView.setText(mGreeting);
        mSignInBarView.setVisibility(mShowSignInButton ? View.VISIBLE : View.GONE);
        mSignOutBarView.setVisibility(mShowSignInButton ? View.GONE : View.VISIBLE);
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
        }
    }
    public void setGreeting(String greeting) {
        mGreeting = greeting;
        updateUI();
    }

    public void setPlayerPicture(Uri mPlayerPictureUri) {
        this.mPlayerPictureUri = mPlayerPictureUri;
        updateUI();
    }
}
