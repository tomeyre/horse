package com.example.horseracing.Adapters.horse;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.horseracing.R;
import com.example.horseracing.activities.horse.RaceActivity;
import com.example.horseracing.activities.horse.RaceCourseActivity;
import com.example.horseracing.data.horse.RaceCourse;
import com.example.horseracing.data.horse.RaceStats;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class RaceCourseAdapter extends RecyclerView.Adapter<RaceCourseAdapter.RaceCourseViewHolder> {
    private ArrayList<RaceCourse> races;
    private RaceCourseActivity activity;
    private RaceStats raceStats = RaceStats.getInstance();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class RaceCourseViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public TextView time;
        public TextView raceClass;
        public TextView distance;
        public TextView totalRiders;
        public TextView going;
        public TextView isHandicapped;

        public RaceCourseViewHolder(View view) {
            super(view);
            this.cardView = view.findViewById(R.id.raceCourseCard);
            this.time = view.findViewById(R.id.time);
            this.raceClass = view.findViewById(R.id.raceClass);
            this.going = view.findViewById(R.id.going);
            this.distance = view.findViewById(R.id.distance);
            this.totalRiders = view.findViewById(R.id.totalRiders);
            this.isHandicapped = view.findViewById(R.id.isHandicapped);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RaceCourseAdapter(ArrayList<RaceCourse> races, RaceCourseActivity activity) {
        this.races = races;
        this.activity = activity;
        this.setHasStableIds(true);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RaceCourseViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.race_course_card, viewGroup, false);
        return new RaceCourseViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RaceCourseViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        LocalDateTime raceTime = LocalDateTime.ofInstant(races.get(position).getDate().toInstant(),
                ZoneId.systemDefault());
        String time = raceTime.getHour() + ":" + raceTime.getMinute();
        holder.time.setText("Set Off: " + time);
        holder.raceClass.setText("Class: " + races.get(position).getRaceClass());
        holder.going.setText("Going: " + races.get(position).getGoing());
        holder.distance.setText("Distance: " + races.get(position).getDistance());
        holder.totalRiders.setText("Riders: " + races.get(position).getTotalRiders());
        holder.isHandicapped.setText("Handicapped: " + races.get(position).isHandicaped());
        holder.cardView.setTag(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raceStats.setDistance(races.get(position).getDistance());
                raceStats.setId(races.get(position).getRaceReference());
                Intent intent = new Intent(activity, RaceActivity.class);
                intent.putExtra("raceId", races.get(position).getRaceReference());
                activity.startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(races != null) {
            return races.size();
        }
        else return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
