package com.example.instituteofthesouthpacific;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class SelectYearActivity extends AppCompatActivity {

    private static final String[] YEARS = new String[]{"1st Year", "2nd Year", "3rd Year"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_year);

        final String selectedProgram = getIntent().getStringExtra("program");

        if (selectedProgram == null) {
            Toast.makeText(this, "Error: Program not found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        List<String> years = Arrays.asList("Year 1", "Year 2", "Year 3");

        ListView yearListView = findViewById(R.id.yearListView);
        YearAdapter yearAdapter = new YearAdapter(this, years);
        yearListView.setAdapter(yearAdapter);

        yearListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int selectedYear = position + 1;
                showSchedule(selectedProgram, selectedYear);
            }
        });
    }

    private void showSchedule(String program, int year) {

        String sanitizedProgram = program.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        String fileName = sanitizedProgram + "_" + year;

        Log.d("SelectYearActivity", "Selected Image File: " + fileName);

        // Display the schedule
        Intent intent = new Intent(SelectYearActivity.this, ViewScheduleImageActivity.class);
        intent.putExtra("scheduleImage", fileName);
        startActivity(intent);
    }
}

