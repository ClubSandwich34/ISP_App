package com.example.instituteofthesouthpacific;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewSchedulesActivity extends AppCompatActivity {

    static final String[] PROGRAMS = new String[]{"Architectural", "Civil", "Geomatics", "Computing Systems", "Biomedical",
            "Instrumentation", "Electrical (Power)", "Chemical Processing", "Mechanical", "Manufacturing", "Petroleum"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedules);

        ListView programListView = findViewById(R.id.programListView);
        programListView.setAdapter(new ProgramAdapter(this, PROGRAMS));
        programListView.setTextFilterEnabled(true);

        programListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Log the selected program for debugging
                String selectedProgram = PROGRAMS[i];
                Log.d("ViewSchedulesActivity", "Selected Program: " + selectedProgram);

                // Start SelectYearActivity and pass the selected program
                Intent intent = new Intent(ViewSchedulesActivity.this, SelectYearActivity.class);
                intent.putExtra("program", selectedProgram); // Pass the selected program
                startActivity(intent);
            }
        });

    }
}
