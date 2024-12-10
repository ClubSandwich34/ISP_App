package com.example.instituteofthesouthpacific;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

