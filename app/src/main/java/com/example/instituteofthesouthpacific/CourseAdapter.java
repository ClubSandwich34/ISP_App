package com.example.instituteofthesouthpacific;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CourseAdapter extends android.widget.BaseAdapter {
    private Context context;
    private List<Course> courses;

    public CourseAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int position) {
        return courses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.course, parent, false);

        Course course = courses.get(position);

        Log.d("CourseAdapter", "getView: Course Name: " + course.getCourseName());
        Log.d("CourseAdapter", "getView: Course ID: " + course.getCourseID());
        Log.d("CourseAdapter", "getView: Semester: " + course.getSemester());
        Log.d("CourseAdapter", "getView: Credit: " + course.getCredit());
        Log.d("CourseAdapter", "getView: Lecture: " + course.getLecture());
        Log.d("CourseAdapter", "getView: Lab: " + course.getLab());

        // Set course name to TextView
        TextView courseName = convertView.findViewById(R.id.courseName);
        courseName.setText(course.getCourseName());

        // Set course ID to TextView
        TextView courseID = convertView.findViewById(R.id.courseID);
        courseID.setText(course.getCourseID());

        // Set semester to TextView
        TextView courseSemester = convertView.findViewById(R.id.semester);
        courseSemester.setText("Semester: " + course.getSemester());

        // Set Credits to TextView
        TextView courseCredit = convertView.findViewById(R.id.credit);
        courseCredit.setText(course.getCredit() + " credits");

        // Set Lecture hours to TextView
        TextView courseLecture = convertView.findViewById(R.id.lect);
        courseLecture.setText(course.getLecture() + " lecture hours");

        // Set Lab Hours to TextView
        TextView courseLab = convertView.findViewById(R.id.lab);
        courseLab.setText(course.getLab() + " lab hours");

        return convertView;
    }
}
