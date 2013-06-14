package com.repp.todo.crutches;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import com.repp.todo.R;
import com.repp.todo.models.TaskModel;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Здесь будет ваша реклама
 *
 * @author Drepp
 * @since: 04.06.13
 */
public class MainAdapter<M extends TaskModel> extends ArrayAdapter<M> {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public MainAdapter(Context context, int textViewResourceId, List<M> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_view, null);
        }

        TextView text = (TextView) v.findViewById(R.id.list_view_taskText);
        TextView date = (TextView) v.findViewById(R.id.list_view_taskDate);
        RatingBar raiting = (RatingBar) v.findViewById(R.id.list_view_ratingBar);
        CheckBox isCompleted = (CheckBox) v.findViewById(R.id.list_view_isCompletedCb);

        isCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskModel m = getItem(position);
                m.setCompleted(!m.getCompleted());
            }
        });

        raiting.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                TaskModel m = getItem(position);
                m.setRaiting(Math.round(v));
            }
        });

        M p = getItem(position);

        if (p != null) {



            String stdDate =sdf.format(p.getDate());

            text.setText(p.getText());
            date.setText(stdDate);
            raiting.setRating((float) p.getRaiting());
            isCompleted.setChecked(p.getCompleted());
        }

        return v;
    }

    private void showDone(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage("Are u sure about it?");
        builder.create();
        builder.show();

    }

}
