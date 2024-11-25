package com.example.instituteofthesouthpacific;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProgramsAdapter extends RecyclerView.Adapter<ProgramsAdapter.ProgramViewHolder> {

    private final List<String> programs;
    private final OnProgramClickListener onProgramClickListener;

    public interface OnProgramClickListener {
        void onProgramClick(String program);
    }

    public ProgramsAdapter(List<String> programs, OnProgramClickListener listener) {
        this.programs = programs;
        this.onProgramClickListener = listener;
    }

    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_program, parent, false);
        return new ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {
        String program = programs.get(position);
        holder.programNameTextView.setText(program);

        holder.itemView.setOnClickListener(v -> onProgramClickListener.onProgramClick(program));
    }

    @Override
    public int getItemCount() {
        return programs.size();
    }

    static class ProgramViewHolder extends RecyclerView.ViewHolder {
        TextView programNameTextView;

        ProgramViewHolder(View itemView) {
            super(itemView);
            programNameTextView = itemView.findViewById(R.id.programNameTextView);
        }
    }
}

