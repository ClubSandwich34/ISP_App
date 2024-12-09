package com.example.instituteofthesouthpacific;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
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
import java.util.HashMap;
import java.util.List;



public class ViewCalendarActivity extends AppCompatActivity {

    private RecyclerView eventsRecyclerView;
    private EventsAdapter eventsAdapter;
    private List<HashMap<String, String>> allEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        CalendarView calendarView = findViewById(R.id.calendarView);
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        allEvents = XMLParser.parseEvents(this, R.xml.events);

        eventsAdapter = new EventsAdapter();
        eventsRecyclerView.setAdapter(eventsAdapter);


        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;

            List<HashMap<String, String>> eventsForSelectedDate = getEventsForDate(selectedDate);

            eventsAdapter.updateEvents(eventsForSelectedDate);
        });
    }

    private List<HashMap<String, String>> getEventsForDate(String date) {
        List<HashMap<String, String>> eventsForSelectedDate = new ArrayList<>();
        for (HashMap<String, String> event : allEvents) {
            if (event.get("date").equals(date)) {
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



    private static String getElementContent(Element element, String tagName) {
        String content = "";
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            content = nodeList.item(0).getTextContent().trim();
        }

        return content.isEmpty() ? "No Data Available" : content;
    }
}

