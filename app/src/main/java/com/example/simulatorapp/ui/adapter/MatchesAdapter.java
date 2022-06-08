package com.example.simulatorapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.hardware.lights.LightState;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simulatorapp.databinding.MatchesItemBinding;
import com.example.simulatorapp.domain.Match;
import com.example.simulatorapp.ui.DetailActivity;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder>  {

    public List<Match> getMatches() {
        return matches;
    }

    private List<Match> matches;

    public MatchesAdapter(List<Match> matches) {
        this.matches = matches;
    }

    @NonNull
    @Override
    public MatchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MatchesItemBinding binding = MatchesItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesAdapter.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
           Match match = matches.get(position);
            Glide.with(context).load(match.getHomeTeam().getImage()).circleCrop().into(holder.binding.ivHomeTeam);
            holder.binding.tvTeamHome.setText(match.getHomeTeam().getName());
            if(match.getHomeTeam().getScore() != null){
                holder.binding.tvScoreHome.setText(String.valueOf(match.getHomeTeam().getScore()));
            }

            Glide.with(context).load(match.getAwayTeam().getImage()).circleCrop().into(holder.binding.ivTeamAway);
            holder.binding.tvTeamAway.setText(match.getAwayTeam().getName());
        if(match.getAwayTeam().getScore() != null){
            holder.binding.tvScoreAway.setText(String.valueOf(match.getAwayTeam().getScore()));
        }
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.Extras.MATCH, match);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final MatchesItemBinding binding;

        public ViewHolder(MatchesItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
