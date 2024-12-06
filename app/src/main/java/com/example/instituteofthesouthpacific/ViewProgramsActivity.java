package com.example.instituteofthesouthpacific;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ViewProgramsActivity extends AppCompatActivity {

    static final String[] PROGRAMS = new String[] { "Architectural",
            "Civil", "Geomatics", "Computing Systems", "Biomedical",
            "Instrumentation", "Electrical (Power)",
            "Telecommunications", "Chemical Processing", "Industrial", "Mechanical",
            "Manufacturing", "Petroleum" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_programs);

        ListView programListView = (ListView) findViewById(R.id.programListView);
        programListView.setAdapter(new ProgramAdapter(this, PROGRAMS));
        programListView.setTextFilterEnabled(true);

        programListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = (String) parent.getItemAtPosition(position);
                Toast.makeText(getBaseContext(), selectedValue, Toast.LENGTH_SHORT).show();
            }
        });
    }
}