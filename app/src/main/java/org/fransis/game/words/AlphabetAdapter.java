package org.fransis.game.words;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.fransis.game.words.words.R;

import java.util.List;

public class AlphabetAdapter extends BaseAdapter {

    private List<Game.CharStatus> mChars;
    private Context mContext;
    private LayoutInflater mInflater;

    public AlphabetAdapter(Context mContext, List<Game.CharStatus> mChars) {
        this.mChars = mChars;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mChars.size();
    }

    @Override
    public Game.CharStatus getItem(int position) {
        return mChars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        final TextView text;
        final int fieldId = R.id.item_id;
        if (convertView == null) {
            view = mInflater.inflate(R.layout.item, parent, false);
        } else {
            view = convertView;
        }
        try {
            if (fieldId == 0) {
                //  If no custom field is assigned, assume the whole resource is a TextView
                text = (TextView) view;
            } else {
                //  Otherwise, find the TextView field within the layout
                text = view.findViewById(fieldId);

                if (text == null) {
                    throw new RuntimeException("Failed to find view with ID "
                            + mContext.getResources().getResourceName(fieldId)
                            + " in item layout");
                }
            }
        } catch (ClassCastException e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                    "ArrayAdapter requires the resource ID to be a TextView", e);
        }

        final Game.CharStatus item = getItem(position);
        switch (item.getStatus()){
            case CORRECT:
                text.setBackgroundResource(R.drawable.good);
                break;
            case INCORRECT:
                text.setBackgroundResource(R.drawable.wrong);
                break;
            case UNCLICKED:
                break;
        }
        text.setText(item.toString());
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return mChars.isEmpty();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        boolean bRet = mChars.get(position).getStatus() == Game.Status.UNCLICKED;
        return bRet;
    }
}
