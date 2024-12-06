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

