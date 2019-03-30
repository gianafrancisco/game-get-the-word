package org.fransis.game.words;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.fransis.game.words.words.R;

public class DialogLevel extends DialogFragment {

    public static int WELL_DONE = 1;
    public static int GAME_OVER = 2;

    View.OnClickListener btnListener = null;
    Game game;
    LevelRepository levelRepository;
    int dialogType = 0;

    public static DialogLevel newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);

        DialogLevel fragment = new DialogLevel();
        fragment.setArguments(args);
        fragment.setCancelable(false);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialogType = this.getArguments().getInt("type");

        btnListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OnDialogInteractionListener game = ((OnDialogInteractionListener)getActivity());

                if(v.getId() == R.id.btnSiguienteNivel){
                    game.nextLevel();
                }else if(v.getId() == R.id.btnReintentar) {
                    game.restartLevel();
                }
                dismiss();
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_next_level, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.unselected);
        getDialog().setCanceledOnTouchOutside(false);
        Button btnNextLevel = (Button) v.findViewById(R.id.btnSiguienteNivel);
        btnNextLevel.setBackgroundResource(R.drawable.good);

        TextView tv = (TextView)v.findViewById(R.id.dialog_msg);
        Button btnVolverIntentar = (Button) v.findViewById(R.id.btnReintentar);
        if(dialogType == WELL_DONE){
            btnNextLevel.setOnClickListener(btnListener);
            tv.setText(R.string.well_done);
            btnVolverIntentar.setVisibility(View.GONE);
        }else{
            btnNextLevel.setVisibility(View.GONE);
            tv.setText(R.string.game_over);
            btnVolverIntentar.setBackgroundResource(R.drawable.wrong);
            btnVolverIntentar.setOnClickListener(btnListener);
        }

        return v;
    }

    public interface OnDialogInteractionListener {
        void nextLevel();
        void restartLevel();
    }

}
