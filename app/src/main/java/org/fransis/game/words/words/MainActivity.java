package org.fransis.game.words.words;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainMenuFragment.OnFragmentInteractionListener, DialogLevel.OnDialogInteractionListener {


    Fragment mainMenu = null;
    Fragment game = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        mainMenu = new MainMenuFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, mainMenu);
        fragmentTransaction.commit();

    }

    @Override
    public void play() {
        game = new GameFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, game);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void exit() {
        finish();
    }

    @Override
    public void resume() {
        if(game != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, game);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void nextLevel() {
        ((GameFragment)game).siguienteNivel();
    }

    @Override
    public void restartLevel() {
        ((GameFragment)game).reiniciarNivel();
    }
}
