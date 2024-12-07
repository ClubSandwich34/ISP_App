package com.example.instituteofthesouthpacific;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class ViewCoursesActivity extends AppCompatActivity {
    private ListView courseListView;
    private ArrayList<Course> courses;

    private String programName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses);

        courseListView = findViewById(R.id.courseListView);
        courses = new ArrayList<>();


        // Get the program's XML file from the Intent
        String programXML = getIntent().getStringExtra("programXML");
        Log.d("OfferedCoursesActivity", "Received programXml: " + programXML);

        // Load the courses from the XML
        if (programXML != null) {
            int xmlResourceId = getXmlResIdForProgram(programXML);
            Log.d("OfferedCoursesActivity", "Mapped program to XML: " + xmlResourceId);
            parseXML(xmlResourceId);
        }

        // Set the custom CourseAdapter to the ListView
        CourseAdapter adapter = new CourseAdapter(this, courses);
        courseListView.setAdapter(adapter);
    }


    private void parseXML(int xmlResId) {
        try {
            XmlResourceParser parser = getResources().getXml(xmlResId);
            int eventType = parser.getEventType();
            String currentTag = "";
            String courseName = "";
            String courseID = "";
            String semester = "";
            String currentSemester = "";
            String credit = "";
            String lecture = "";
            String lab = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTag = parser.getName();
                        Log.d("XML Parsing", "Start tag: " + currentTag);
                        break;

                    case XmlPullParser.TEXT:
                        String text = parser.getText().trim();
                        Log.d("XML Parsing", "Text: " + text);
                        if (!text.isEmpty()) {
                            switch (currentTag) {
                                case "courseName":
                                    Log.d("XML Parsing", "Parsed text for tag " + currentTag + ": " + text);
                                    courseName = text;
                                    break;
                                case "courseID":
                                    Log.d("XML Parsing", "Parsed text for tag " + currentTag + ": " + text);
                                    courseID = text;
                                    break;
                                case "credit":
                                    Log.d("XML Parsing", "Parsed text for tag " + currentTag + ": " + text);
                                    credit = text;
                                    break;
                                case "lecture":
                                    Log.d("XML Parsing", "Parsed text for tag " + currentTag + ": " + text);
                                    lecture = text;
                                    break;
                                case "lab":
                                    Log.d("XML Parsing", "Parsed text for tag " + currentTag + ": " + text);
                                    lab = text;
                                    break;
                                case "number":
                                    Log.d("XML Parsing", "Parsed text for tag " + currentTag + ": " + text);
                                    semester = text;
                                    currentSemester = text;
                                    break;
                                default:
                                    Log.d("XML Parsing", "Unknown tag: " + currentTag);
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("course")) {

                            if (semester.equals("")) {
                                semester = currentSemester;
                            }
                            courses.add(new Course(courseName, courseID, semester, credit, lecture, lab));
                            Log.d("XML Parsing", "Added course: " + courseName + " " + courseID + " " + semester + " " + credit + " " + lecture + " " + lab);

                            courseName = "";
                            courseID = "";
                            semester = "";
                            credit = "";
                            lecture = "";
                            lab = "";
                        }
                        break;
                }
                eventType = parser.next();
            }

            Log.d("XML Parsing", "Finished parsing. Total courses: " + courses.size());
            parser.close();
        } catch (Exception e) {
            Log.e("XML Parsing", "Error parsing XML: " + e.getMessage());
            e.printStackTrace();
        }
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
