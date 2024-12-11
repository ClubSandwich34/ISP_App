package com.example.instituteofthesouthpacific;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProgramAdapter extends ArrayAdapter <String> {
    private Context context;
    private String[] items;

    public ProgramAdapter(Context context, String[] items) {
        super(context, R.layout.rowlist, R.id.textid, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.rowlist, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textid);
        TextView subTextView = (TextView) rowView.findViewById(R.id.subtextid);
        textView.setText(items[position]);
        subTextView.setText("Engineering Technology");
        return rowView;
    }
}
