package com.example.instituteofthesouthpacific;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import android.view.View;




public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {


    @NonNull
    @Override
    public CoursesAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesAdapter.CourseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}


class Course {
    private String id;
    private String name;
    private int lectures;
    private int labs;
    private int credits;

    // Constructor
    public Course(String id, String name, int lectures, int labs, int credits) {
        this.id = id;
        this.name = name;
        this.lectures = lectures;
        this.labs = labs;
        this.credits = credits;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLectures() {
        return lectures;
    }

    public void setLectures(int lectures) {
        this.lectures = lectures;
    }

    public int getLabs() {
        return labs;
    }

    public void setLabs(int labs) {
        this.labs = labs;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}

