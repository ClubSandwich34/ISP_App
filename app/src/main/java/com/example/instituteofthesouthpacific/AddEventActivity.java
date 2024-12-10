package com.example.instituteofthesouthpacific;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEventActivity extends AppCompatActivity {

    private EditText titleEditText, descriptionEditText, dateEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dateEditText = findViewById(R.id.dateEditText);
        saveButton = findViewById(R.id.saveButton);

        if (titleEditText == null || descriptionEditText == null || dateEditText == null || saveButton == null) {
            Log.e("AddEventActivity", "One or more views not initialized properly!");
        }

        saveButton.setOnClickListener(view -> saveEvent());
    }

    private void saveEvent() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();

        // Check if title or date is empty
        if (title.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Title and Date are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize SQLite helper
        SQLiteEvents dbHelper = new SQLiteEvents(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();  // Get writable database

        // Log the database operations
        Log.d("AddEventActivity", "Inserting event: Title = " + title + ", Date = " + date);

        // Create ContentValues to insert the event data
        ContentValues values = new ContentValues();
        values.put(SQLiteEvents.COLUMN_TITLE, title);
        values.put(SQLiteEvents.COLUMN_DESCRIPTION, description);
        values.put(SQLiteEvents.COLUMN_DATE, date);

        // Insert data into the database
        long newRowId = db.insert(SQLiteEvents.TABLE_EVENTS, null, values);
        Log.d("AddEventActivity", "New row ID: " + newRowId);

        // Check if the insertion was successful
        if (newRowId != -1) {
            Toast.makeText(this, "Event added successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error adding event", Toast.LENGTH_SHORT).show();
        }

        // Verify if the event is actually saved by querying the database
        Cursor cursor = db.query(SQLiteEvents.TABLE_EVENTS,
                new String[] {SQLiteEvents.COLUMN_TITLE, SQLiteEvents.COLUMN_DATE},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String savedTitle = cursor.getString(cursor.getColumnIndex(SQLiteEvents.COLUMN_TITLE));
                @SuppressLint("Range") String savedDate = cursor.getString(cursor.getColumnIndex(SQLiteEvents.COLUMN_DATE));
                Log.d("Database Check", "Saved Event: " + savedTitle + ", " + savedDate);
            } while (cursor.moveToNext());
        } else {
            Log.e("Database Check", "No events found in the database.");
        }

        cursor.close();  // Close the cursor
        db.close();  // Close the database connection
    }
}
