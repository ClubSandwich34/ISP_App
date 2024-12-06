package com.example.instituteofthesouthpacific;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.ListView;
import android.content.Intent;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;

public class ViewProgramsActivity extends AppCompatActivity {

    static final String[] PROGRAMS = new String[] { "Architectural",
            "Civil", "Geomatics", "Computing Systems", "Biomedical",
            "Instrumentation", "Electrical (Power)", "Chemical Processing", "Mechanical",
            "Manufacturing", "Petroleum" };

    private String title = new String();

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
                title = selectedValue;

                int xmlResId = getXmlResIdForProgram(selectedValue);
                if (xmlResId == 0) {
                    new AlertDialog.Builder(ViewProgramsActivity.this)
                            .setTitle("Error")
                            .setMessage("No courses found for the selected program.")
                            .setNeutralButton("Close", null)
                            .show();
                } else {
                    String xmlData = loadXmlFromResources(xmlResId);
                    if (xmlData == null) {
                        new AlertDialog.Builder(ViewProgramsActivity.this)
                                .setTitle("Error")
                                .setMessage("Failed to load courses.")
                                .setNeutralButton("Close", null)
                                .show();
                    } else {
                        Intent coursesScreen = new Intent(getApplicationContext(), ViewCoursesActivity.class);
                        coursesScreen.putExtra("source", xmlData);
                        coursesScreen.putExtra("programTitle", title);
                        startActivity(coursesScreen);
                    }
                }
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

    private String loadXmlFromResources(int resId) {
        StringBuilder sb = new StringBuilder();
        XmlResourceParser parser = getResources().getXml(resId);

        try {
            int eventType = parser.getEventType();
            String currentSemester = null;

            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String tagName = parser.getName();

                    if (tagName.equals("semester")) {
                        currentSemester = parser.getAttributeValue(null, "number");
                        sb.append("Semester ").append(currentSemester).append("\n");
                    }

                    if (tagName.equals("course")) {
                        String courseId = parser.getAttributeValue(null, "cid");
                        String courseName = parser.getAttributeValue(null, "cname");
                        String credit = parser.getAttributeValue(null, "credit");
                        String lecture = parser.getAttributeValue(null, "lect");
                        String lab = parser.getAttributeValue(null, "lab");

                        sb.append("    Course ID: ").append(courseId).append("\n")
                                .append("    Course Name: ").append(courseName).append("\n")
                                .append("    Credits: ").append(credit).append("\n")
                                .append("    Lecture Hours: ").append(lecture).append("\n")
                                .append("    Lab Hours: ").append(lab).append("\n\n");
                    }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        } finally {
            parser.close();
        }

        String result = sb.toString();
        Log.d("XMLData", result);  // Verify data
        return result;
    }



}
