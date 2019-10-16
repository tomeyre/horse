package com.example.horseracing.Adapters.football;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.horseracing.R;
import com.example.horseracing.activities.football.FootballMatchActivity;
import com.example.horseracing.activities.football.FootballMatchesByDayActivity;
import com.example.horseracing.data.football.Match;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FootballMatchesByDayAdapter extends RecyclerView.Adapter<FootballMatchesByDayAdapter.RaceEventViewHolder> {
    private ArrayList<Match> matches;
    private FootballMatchesByDayActivity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class RaceEventViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public TextView date;
        public TextView teamName;
        public TextView time;
        public TextView outcome;
        public TextView winner;

        public RaceEventViewHolder(View view) {
            super(view);
            this.cardView = view.findViewById(R.id.matchCard);
            this.date = view.findViewById(R.id.date);
            this.teamName = view.findViewById(R.id.teamName);
            this.time = view.findViewById(R.id.time);
            this.outcome = view.findViewById(R.id.outcome);
            this.winner = view.findViewById(R.id.winner);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FootballMatchesByDayAdapter(ArrayList<Match> matches, FootballMatchesByDayActivity activity) {
        this.matches = matches;
        this.activity = activity;
        this.setHasStableIds(true);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RaceEventViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                  int viewType) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.match_card, viewGroup, false);
        return new RaceEventViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RaceEventViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(matches.get(position).getDate());
        holder.date.setText(calendar.get(Calendar.DAY_OF_MONTH) + " - " + getMonthForInt(calendar.get(Calendar.MONTH)));
        holder.teamName.setText(matches.get(position).getTeamA().getName() + " vs " +
                matches.get(position).getTeamB().getName());
        boolean pm = calendar.get(Calendar.HOUR_OF_DAY) > 12;
        String minutes = calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE) : "" + calendar.get(Calendar.MINUTE);
        String hours = calendar.get(Calendar.HOUR_OF_DAY) > 12 ? calendar.get(Calendar.HOUR_OF_DAY) - 12 < 10 ? "0" + (calendar.get(Calendar.HOUR_OF_DAY) - 12) : "" + calendar.get(Calendar.HOUR_OF_DAY) : "" + calendar.get(Calendar.HOUR_OF_DAY);
        holder.time.setText(hours + ":" + minutes + (pm ? "PM" : "AM"));
        if(matches.get(position).getOutcome() == null){
            holder.outcome.setVisibility(View.GONE);
        }else {
            holder.outcome.setVisibility(View.VISIBLE);
            holder.outcome.setText(matches.get(position).getOutcome());
        }
        if(matches.get(position).getWinner() == null){
            holder.winner.setVisibility(View.GONE);
        }else {
            holder.winner.setVisibility(View.VISIBLE);
            holder.winner.setText(matches.get(position).getWinner());
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, FootballMatchActivity.class);
                intent.putExtra("match", matches.get(position));
                activity.startActivity(intent);
            }
        });

    }

    private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(matches != null) {
            return matches.size();
        }
        else return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
