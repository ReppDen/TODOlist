package com.example.test5.crutches;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.test5.R;
import com.example.test5.models.TaskModel;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Здесь будет ваша реклама
 *
 * @author Drepp
 * @since: 04.06.13
 */
public class MainAdapter<M extends TaskModel> extends ArrayAdapter<M> {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public MainAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {

                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.list_view, null);

            }

            M p = getItem(position);

            if (p != null) {

                TextView text = (TextView) v.findViewById(R.id.list_view_taskText);
                TextView date = (TextView) v.findViewById(R.id.list_view_taskDate);
                RatingBar raiting = (RatingBar) v.findViewById(R.id.list_view_ratingBar);
                CheckBox isCompleted = (CheckBox) v.findViewById(R.id.list_view_isCompletedCb);

                String stdDate =sdf.format(p.getDate());

                text.setText(p.getText());
                date.setText(stdDate);
                raiting.setRating((float) p.getRaiting());
                isCompleted.setChecked(p.getCompleted());
            }

            return v;

    }
}
