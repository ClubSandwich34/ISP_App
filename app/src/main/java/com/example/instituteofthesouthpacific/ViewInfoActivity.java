package com.example.instituteofthesouthpacific;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewInfoActivity extends AppCompatActivity {

    private ImageView logoImageView;

    private TextView appNameTextView;
    private TextView developerTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info);

        logoImageView = findViewById(R.id.logoImageView);
        appNameTextView = findViewById(R.id.appNameTextView);
        developerTextView = findViewById(R.id.developerTextView);

        appNameTextView.setText("Institute of the South Pacific App");
        developerTextView.setText("Developed by: Caleb Cooper & Nathan Rideout");

    }
}
