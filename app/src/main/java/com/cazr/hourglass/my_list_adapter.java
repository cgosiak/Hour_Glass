package com.cazr.hourglass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cgosiak on 8/11/2015.
 */

public class my_list_adapter extends ArrayAdapter<Recap> {
    public my_list_adapter(Context context, ArrayList<Recap> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Recap user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.daily_layout, parent, false);
        }
        // Lookup view for data population
        TextView date = (TextView) convertView.findViewById(R.id.date);
        ImageView up_down = (ImageView) convertView.findViewById(R.id.up_down);
        TextView gained_lost = (TextView) convertView.findViewById(R.id.gained_lost);
        // Populate the data into the template view using the data object
        date.setText(user.date);
        up_down.setImageResource(user.recap_drawable);
        gained_lost.setText(user.gained_lost_amt.toString());
        // Return the completed view to render on screen
        return convertView;
    }
}
