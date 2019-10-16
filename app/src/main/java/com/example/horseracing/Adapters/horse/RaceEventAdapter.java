package com.example.horseracing.Adapters.horse;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.horseracing.R;
import com.example.horseracing.activities.horse.RaceCourseActivity;
import com.example.horseracing.activities.horse.RaceDayEventsActivity;
import com.example.horseracing.data.horse.RaceEvent;

import java.util.ArrayList;

public class RaceEventAdapter extends RecyclerView.Adapter<RaceEventAdapter.RaceEventViewHolder> {
    private ArrayList<RaceEvent> races;
    private RaceDayEventsActivity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class RaceEventViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public TextView location;
        public TextView country;
        public TextView going;
        public TextView surface;
        public TextView weather;
        public TextView totalRaces;

        public RaceEventViewHolder(View view) {
            super(view);
            this.cardView = view.findViewById(R.id.raceEventCard);
            this.location = view.findViewById(R.id.location);
            this.country = view.findViewById(R.id.country);
            this.going = view.findViewById(R.id.going);
            this.surface = view.findViewById(R.id.surface);
            this.weather = view.findViewById(R.id.weather);
            this.totalRaces = view.findViewById(R.id.totalRaces);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RaceEventAdapter(ArrayList<RaceEvent> races, RaceDayEventsActivity activity) {
        this.races = races;
        this.activity = activity;
        this.setHasStableIds(true);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RaceEventViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                  int viewType) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.race_event_card, viewGroup, false);
        return new RaceEventViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RaceEventViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.location.setText("Location: " + races.get(position).getLocation());
        holder.country.setText("Country: " + races.get(position).getCountry());
        holder.going.setText("Going: " + races.get(position).getGoing());
        holder.surface.setText("Surface: " + races.get(position).getSurface());
        holder.weather.setText("Weather: " + races.get(position).getWeather());
        holder.totalRaces.setText("Total Races: " + races.get(position).getRaces().size());
        holder.cardView.setTag(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, RaceCourseActivity.class);
                intent.putExtra("races", races.get(position).getRaces());
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
