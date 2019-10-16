package com.example.horseracing.Adapters.horse;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.horseracing.R;
import com.example.horseracing.activities.horse.RaceActivity;
import com.example.horseracing.data.horse.RaceParticipant;

import java.util.ArrayList;

public class RaceAdapter extends RecyclerView.Adapter<RaceAdapter.RaceViewHolder> {
    private ArrayList<RaceParticipant> participants;
    private RaceActivity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class RaceViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public TextView horse;
        public TextView age;
        public TextView sex;
        public TextView lastRan;
        public TextView rating;
        public TextView owner;
        public TextView trainer;
        public TextView jockey;
        public TextView position;
        public TextView expectedPosition;
        public TextView odds;
        public TextView myScore;

        public RaceViewHolder(View view) {
            super(view);
            this.cardView = view.findViewById(R.id.raceCard);
            this.horse = view.findViewById(R.id.horse);
            this.age = view.findViewById(R.id.age);
            this.sex = view.findViewById(R.id.sex);
            this.lastRan = view.findViewById(R.id.lastRan);
            this.rating = view.findViewById(R.id.rating);
            this.owner = view.findViewById(R.id.owner);
            this.trainer = view.findViewById(R.id.trainer);
            this.jockey = view.findViewById(R.id.jockey);
            this.position = view.findViewById(R.id.position);
            this.expectedPosition = view.findViewById(R.id.expectedPosition);
            this.odds = view.findViewById(R.id.odds);
            this.myScore = view.findViewById(R.id.myScore);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RaceAdapter(ArrayList<RaceParticipant> participants, RaceActivity activity) {
        this.participants = participants;
        this.activity = activity;
        this.setHasStableIds(true);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RaceViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.race_card, viewGroup, false);
        return new RaceViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RaceViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(participants.get(position).getHorse() != null) {
            if(position == 0){
                if(participants.get(position).getChanceAtWinning() > participants.get(1).getChanceAtWinning() + 5){
                    holder.cardView.setCardBackgroundColor(Color.GREEN);
                }else{
                    holder.cardView.setCardBackgroundColor(Color.WHITE);
                }
            }
            holder.horse.setText("Horse: " + participants.get(position).getHorse().getName());
            holder.age.setText("Age: " + participants.get(position).getHorse().getAge());
            holder.sex.setText("Sex: " + participants.get(position).getHorse().getSex());
            holder.lastRan.setText("Days Since Last Race: " + participants.get(position).getHorse().getLastRan());
            holder.rating.setText("Rating: " + participants.get(position).getHorse().getOfficialRating());
            holder.owner.setText("Owner: " + participants.get(position).getHorse().getOwner());
            holder.trainer.setText("Trainer: " + participants.get(position).getTrainer().getName());
            holder.jockey.setText("Jockey: " + participants.get(position).getJockey().getName());
            holder.odds.setText("Odds: " + participants.get(position).getHorse().getOdds());
            holder.expectedPosition.setText("Expected Position: " + participants.get(position).getHorse().getExpectedPosition());
            if(position != 0 && !participants.get(position).getHorse().isRunner()){
                holder.cardView.setCardBackgroundColor(Color.RED);
            }
            else if(position != 0){
                holder.cardView.setCardBackgroundColor(Color.WHITE);
            }
            holder.myScore.setText("My Score: " + participants.get(position).getChanceAtWinning());
            if(participants.get(position).getHorse().getPosition() == 0){
                holder.position.setText("Finished: unknown");
            }else {
                holder.position.setText("Finished: " + (participants.get(position).getHorse().getPosition()));
            }
        }else{
            holder.horse.setText("Horse: ");
            holder.age.setText("Age: ");
            holder.sex.setText("Sex: ");
            holder.lastRan.setText("Days Since Last Race: ");
            holder.rating.setText("Rating: ");
            holder.owner.setText("Owner: ");
            holder.trainer.setText("Trainer: ");
            holder.jockey.setText("Jockey: ");
            holder.odds.setText("Odds: ");
            holder.expectedPosition.setText("Expected Position:");
            holder.myScore.setText("My Score:");
            holder.position.setText("Finished: ");
        }holder.cardView.setTag(position);
        Log.i("VIEW INFLATED:: ", "view " + position + " inflated");

    }

    @Override
    public int getItemCount() {
        if(participants != null) {
            return participants.size();
        }
        else return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
//31st redcar 20 riders