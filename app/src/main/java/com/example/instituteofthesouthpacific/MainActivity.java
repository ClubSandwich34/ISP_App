package com.example.instituteofthesouthpacific;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private ImageView logoImageView;
    private ConstraintLayout buttonLayout;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;

    private final Class<?> viewPrograms = ViewProgramsActivity.class;
    private final Class<?> viewSchedules = ViewSchedulesActivity.class;
    private final Class<?> viewCalendar = ViewCalendarActivity.class;
    private final Class<?> viewPublicTransitInfo = ViewPublicTransitInfoActivity.class;
    private final Class<?> viewNews = ViewNewsActivity.class;
    private final Class<?> viewContacts = ViewContactsActivity.class;
    private final Class<?> viewInfo = ViewInfoActivity.class;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoImageView = findViewById(R.id.logoImageView);
        buttonLayout = findViewById(R.id.buttonLayout);

        findViewById(android.R.id.content).setBackgroundColor(Color.WHITE);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);

        button1.setOnClickListener(view -> startNewActivity(viewPrograms));
        button2.setOnClickListener(view -> startNewActivity(viewSchedules));
        button3.setOnClickListener(view -> startNewActivity(viewCalendar));
        button4.setOnClickListener(view -> startNewActivity(viewPublicTransitInfo));
        button5.setOnClickListener(view -> startNewActivity(viewNews));
        button6.setOnClickListener(view -> startNewActivity(viewContacts));
        button7.setOnClickListener(view -> startNewActivity(viewInfo));

    }

    private void startNewActivity(Class<?> activityClass) {
        if (activityClass != null){
            Intent intent = new Intent(this, activityClass);
            startActivity(intent);
        }
    }


}