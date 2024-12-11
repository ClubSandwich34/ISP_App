package com.example.instituteofthesouthpacific;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewScheduleImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule_image);

        // Retrieve the image filename passed from SelectYearActivity
        String imageFileName = getIntent().getStringExtra("scheduleImage");

        // Log the filename for debugging
        Log.d("ViewScheduleImageActivity", "Image File: " + imageFileName);

        // Find the ImageView in the layout
        ImageView scheduleImageView = findViewById(R.id.scheduleImageView);

        // Get the resource ID for the image using the filename
        int imageResId = getResources().getIdentifier(imageFileName, "drawable", getPackageName());

        if (imageResId != 0) {
            scheduleImageView.setImageResource(imageResId);
        }
    }
}
