package com.example.instituteofthesouthpacific;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewInfoActivity extends AppCompatActivity {

    private ImageView logoImageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info);

        logoImageView = findViewById(R.id.logoImageView);

    }
}
