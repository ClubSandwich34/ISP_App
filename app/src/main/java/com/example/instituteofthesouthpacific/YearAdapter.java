package com.example.instituteofthesouthpacific;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class YearAdapter extends ArrayAdapter<String> {

    public YearAdapter(Context context, List<String> years) {
        super(context, 0, years);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.yearrowlist, parent, false);
        }

        String year = getItem(position);

        TextView yearTextView = convertView.findViewById(R.id.yearTextView);
        yearTextView.setText(year);

        ImageView arrowImageView = convertView.findViewById(R.id.arrowImageView);
        arrowImageView.setImageResource(R.drawable.right_arrow);

        return convertView;
    }
}
