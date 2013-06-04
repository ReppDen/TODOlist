package com.example.test5.crutches;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.test5.models.TaskModel;

import java.util.List;

/**
 * Здесь будет ваша реклама
 *
 * @author Drepp
 * @since: 04.06.13
 */
public class MainAdapter<M extends TaskModel> extends ArrayAdapter<M> {

    public MainAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public MainAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public MainAdapter(Context context, int textViewResourceId, M[] objects) {
        super(context, textViewResourceId, objects);
    }

    public MainAdapter(Context context, int resource, int textViewResourceId, M[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public MainAdapter(Context context, int textViewResourceId, List<M> objects) {
        super(context, textViewResourceId, objects);
    }

    public MainAdapter(Context context, int resource, int textViewResourceId, List<M> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
