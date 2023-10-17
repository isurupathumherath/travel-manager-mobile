package com.eadsliit.travel_manager_mobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class TrainScheduleAdapter extends RecyclerView.Adapter<TrainScheduleAdapter.ViewHolder> {
    private List<TrainScheduleItem> trainScheduleItemList;

    public TrainScheduleAdapter(List<TrainScheduleItem> trainScheduleItemList) {
        this.trainScheduleItemList = trainScheduleItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrainScheduleItem item = trainScheduleItemList.get(position);

        holder.nameTextView.setText(item.getName());
        holder.startName.setText(item.getStartStation());
        holder.endName.setText(item.getEndStations());

        // Bind other data fields to their respective TextViews
    }

    @Override
    public int getItemCount() {
        return trainScheduleItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView startName;
        TextView endName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            startName = itemView.findViewById(R.id.startName);
            endName = itemView.findViewById(R.id.endName);
        }
    }
}

