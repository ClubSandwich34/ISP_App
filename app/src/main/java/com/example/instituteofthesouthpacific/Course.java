package com.example.instituteofthesouthpacific;

import android.util.Log;

public class Course {
    private String courseName, courseID, semester, credit, lect, lab;

    public Course(String courseName, String courseID, String semester, String credit, String lect, String lab) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.semester = semester;
        this.credit = credit;
        this.lect = lect;
        this.lab = lab;
    }


    public String getSemester() {
        Log.d("Course", "getSemester: " + semester);
        return semester;
    }

    public String getCourseID() {
        Log.d("Course", "getCourseID: " + courseID);
        return courseID;
    }

    public String getCourseName() {
        Log.d("Course", "getCourseName: " + courseName);
        return courseName;
    }

    public String getCredit() {
        Log.d("Course", "getCredit: " + credit);
        return credit;
    }

    public String getLecture() {
        Log.d("Course", "getLecture: " + lect);
        return lect;
    }

    public String getLab() {
        Log.d("Course", "getLab: " + lab);
        return lab;
    }

    public String toString() {
        return courseID + " " + courseName + " " + credit + " " + lect + " " + lab;
    }
}
