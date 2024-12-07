package com.example.instituteofthesouthpacific;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewProgramsActivity extends AppCompatActivity {

    static final String[] PROGRAMS = new String[]{"Architectural", "Civil", "Geomatics", "Computing Systems", "Biomedical",
            "Instrumentation", "Electrical (Power)", "Chemical Processing", "Mechanical", "Manufacturing", "Petroleum"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_programs);

        ListView programListView = findViewById(R.id.programListView);
        programListView.setAdapter(new ProgramAdapter(this, PROGRAMS));
        programListView.setTextFilterEnabled(true);

        programListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = (String) parent.getItemAtPosition(position);

                int xmlResId = getXmlResIdForProgram(selectedValue);
                    Intent coursesScreen = new Intent(getApplicationContext(), ViewCoursesActivity.class);
                    coursesScreen.putExtra("programXML", selectedValue);  // Send the program name to the next activity
                    startActivity(coursesScreen);

            }
        });
    }

    private int getXmlResIdForProgram(String selectedValue) {
        switch (selectedValue) {
            case "Architectural":
                return R.xml.ae;
            case "Civil":
                return R.xml.ce;
            case "Geomatics":
                return R.xml.ge;
            case "Biomedical":
                return R.xml.eb;
            case "Computing Systems":
                return R.xml.cs;
            case "Electrical (Power)":
                return R.xml.ep;
            case "Instrumentation":
                return R.xml.ei;
            case "Chemical Processing":
                return R.xml.cp;
            case "Mechanical":
                return R.xml.me;
            case "Manufacturing":
                return R.xml.mm;
            case "Petroleum":
                return R.xml.pe;
            default:
                return 0;
        }
    }
}
