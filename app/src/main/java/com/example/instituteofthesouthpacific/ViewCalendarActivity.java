package com.example.instituteofthesouthpacific;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ViewCalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private RecyclerView eventsRecyclerView;
    private EventsAdapter eventsAdapter;
    private List<HashMap<String, String>> allEvents;
    private HashSet<String> eventDatesSet;
    private SQLiteEvents dbHelper;  // Use SQLiteEvents to interact with the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        calendarView = findViewById(R.id.calendarView);
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        allEvents = new ArrayList<>();
        eventDatesSet = new HashSet<>();

        dbHelper = new SQLiteEvents(this);

        // Initialize eventsAdapter before using it
        eventsAdapter = new EventsAdapter();
        eventsRecyclerView.setAdapter(eventsAdapter);

        loadEvents(); // This should load events after the adapter is set

        Button addEventButton = findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewCalendarActivity.this, AddEventActivity.class);
            startActivity(intent);
        });

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            List<HashMap<String, String>> eventsForSelectedDate = getEventsForDate(selectedDate);
            eventsAdapter.updateEvents(eventsForSelectedDate);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEvents();
    }

    private void loadEvents() {
        allEvents.clear();
        eventDatesSet.clear();

        // Load XML events (if any)
        List<HashMap<String, String>> xmlEvents = XMLParser.parseEvents(this, R.xml.events);
        allEvents.addAll(xmlEvents);

        // Load events from the database
        List<HashMap<String, String>> dbEvents = loadEventsFromDatabase();
        allEvents.addAll(dbEvents);

        // Add the event dates to the set
        for (HashMap<String, String> event : allEvents) {
            eventDatesSet.add(event.get("date"));
        }

        // Update events for the current selected date
        eventsAdapter.updateEvents(getEventsForDate(getCurrentSelectedDate()));
    }

    @SuppressLint("Range")
    private List<HashMap<String, String>> loadEventsFromDatabase() {
        List<HashMap<String, String>> dbEvents = new ArrayList<>();

        // Use SQLiteEvents to get the readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + SQLiteEvents.COLUMN_DATE + ", "
                + SQLiteEvents.COLUMN_TITLE + ", " + SQLiteEvents.COLUMN_DESCRIPTION
                + " FROM " + SQLiteEvents.TABLE_EVENTS, null);

        while (cursor.moveToNext()) {
            HashMap<String, String> event = new HashMap<>();
            event.put("date", cursor.getString(cursor.getColumnIndex(SQLiteEvents.COLUMN_DATE)));
            event.put("title", cursor.getString(cursor.getColumnIndex(SQLiteEvents.COLUMN_TITLE)));
            event.put("description", cursor.getString(cursor.getColumnIndex(SQLiteEvents.COLUMN_DESCRIPTION)));
            dbEvents.add(event);
        }
        cursor.close();

        return dbEvents;
    }

    private String getCurrentSelectedDate() {
        Calendar currentDate = Calendar.getInstance();
        return currentDate.get(Calendar.YEAR) + "-" + (currentDate.get(Calendar.MONTH) + 1) + "-" + currentDate.get(Calendar.DAY_OF_MONTH);
    }

    private List<HashMap<String, String>> getEventsForDate(String date) {
        List<HashMap<String, String>> eventsForSelectedDate = new ArrayList<>();
        for (HashMap<String, String> event : allEvents) {
            String eventDate = event.get("date");
            if (eventDate.equals(date)) {
                eventsForSelectedDate.add(event);
            }
        }
        return eventsForSelectedDate;
    }

    class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {
        private List<HashMap<String, String>> events = new ArrayList<>();

        @Override
        public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
            return new EventViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(EventViewHolder holder, int position) {
            HashMap<String, String> event = events.get(position);
            holder.dateTextView.setText(event.get("date"));
            holder.titleTextView.setText(event.get("title"));
            holder.descriptionTextView.setText(event.get("description"));
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        public void updateEvents(List<HashMap<String, String>> newEvents) {
            events.clear();
            events.addAll(newEvents);
            notifyDataSetChanged();
        }

        public class EventViewHolder extends RecyclerView.ViewHolder {
            TextView dateTextView;
            TextView titleTextView;
            TextView descriptionTextView;

            public EventViewHolder(View itemView) {
                super(itemView);
                dateTextView = itemView.findViewById(R.id.eventDate);
                titleTextView = itemView.findViewById(R.id.eventTitle);
                descriptionTextView = itemView.findViewById(R.id.eventDescription);
            }
        }
    }
}







class XMLParser {

    private static final String TAG = "XMLParser";

    public static List<HashMap<String, String>> parseEvents(Context context, int xmlResourceId) {
        List<HashMap<String, String>> events = new ArrayList<>();

        try {
            Log.d(TAG, "Starting to parse XML resource");

            XmlResourceParser parser = context.getResources().getXml(xmlResourceId);

            int eventType = parser.getEventType();
            String date = "";
            String title = "";
            String description = "";
            HashMap<String, String> event = null;

            while (eventType != XmlResourceParser.END_DOCUMENT) {
                String tagName = parser.getName();

                switch (eventType) {
                    case XmlResourceParser.START_TAG:
                        if (tagName.equals("event")) {
                            // Prepare a new event hashmap when we start a new event
                            event = new HashMap<>();
                        } else if (event != null) {
                            // Capture the content for each tag
                            if (tagName.equals("date")) {
                                date = parser.nextText();
                            } else if (tagName.equals("title")) {
                                title = parser.nextText();
                            } else if (tagName.equals("description")) {
                                description = parser.nextText();
                            }
                        }
                        break;

                    case XmlResourceParser.END_TAG:
                        if (tagName.equals("event")) {
                            // Once an event is fully parsed, add it to the list
                            event.put("date", date);
                            event.put("title", title);
                            event.put("description", description);
                            events.add(event);

                            // Reset the values for the next event
                            date = "";
                            title = "";
                            description = "";
                        }
                        break;
                }

                eventType = parser.next();
            }

        } catch (Exception e) {
            Log.e(TAG, "Error parsing XML", e);
        }

        Log.d(TAG, "Finished parsing. Total events: " + events.size());
        return events;
    }
}

