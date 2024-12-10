package com.example.instituteofthesouthpacific;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import org.w3c.dom.Element;

import org.w3c.dom.NodeList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        calendarView = findViewById(R.id.calendarView);
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        allEvents = XMLParser.parseEvents(this, R.xml.events);

        eventDatesSet = new HashSet<>();
        for (HashMap<String, String> event : allEvents) {
            eventDatesSet.add(event.get("date"));
        }

        eventsAdapter = new EventsAdapter();
        eventsRecyclerView.setAdapter(eventsAdapter);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;

            List<HashMap<String, String>> eventsForSelectedDate = getEventsForDate(selectedDate);

            eventsAdapter.updateEvents(eventsForSelectedDate);
        });

        highlightEventDays();
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

    private void highlightEventDays() {
        for (String eventDate : eventDatesSet) {
            String[] dateParts = eventDate.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]) - 1;
            int day = Integer.parseInt(dateParts[2]);

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            long timeInMillis = calendar.getTimeInMillis();
            calendarView.setDate(timeInMillis, true, true);
        }
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

