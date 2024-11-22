package com.example.instituteofthesouthpacific;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import java.util.List;


public class ViewProgramsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_programs);

        List<String> programsList = Arrays.asList(
                "Architectural Engineering Technology",
                "Chemical Process Engineering Technology (Co-Op)",
                "Civil Engineering Technology (Co-Op)",
                "Computing Systems Engineering Technology (Co-op)",
                "Electrical Engineering Technology (Power & Controls) Co-op",
                "Electronics Engineering Technology (Biomedical)",
                "Geomatics/Surveying Engineering Technology (Co-op)",
                "Instrumentation and Controls Engineering Technology",
                "Management Systems Engineering Technology (Co-op)",
                "Mechanical Engineering Technology",
                "Mechanical Engineering Technology (Manufacturing) Co-op",
                "Petroleum Engineering Technology (Co-op)",
                "Refrigeration & Air Conditioning Mechanic"
        );

        RecyclerView programsRecyclerView = findViewById(R.id.programsRecyclerView);
        programsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProgramsAdapter adapter = new ProgramsAdapter(programsList, programName -> {
            Intent intent = new Intent(ViewProgramsActivity.this, ViewCoursesActivity.class);
            intent.putExtra("PROGRAM_NAME", programName); // Pass selected program name
            startActivity(intent);
        });


        programsRecyclerView.setAdapter(adapter);
    }
}
