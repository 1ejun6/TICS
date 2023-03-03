package com.example.tics.ui.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tics.R;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private List<Game> gameList;
    private Context context;

    public GameAdapter(List<Game> gameList, Context context) {
        this.gameList = gameList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.gameIDTextView.setText("GameID: "+game.getGameID());
        holder.gameDateTextView.setText("Date: "+game.getGameDate());
        holder.gameStartEndTimeTextView.setText(game.getGameStartTime()+"-"+game.getGameEndTime());
        holder.gameTicCountTextView.setText(String.valueOf(game.getGameTicCount()));
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView gameIDTextView;
        private TextView gameDateTextView;
        private TextView gameStartEndTimeTextView;
        private TextView gameTicCountTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            gameIDTextView = itemView.findViewById(R.id.GameID);
            gameDateTextView = itemView.findViewById(R.id.GameDate);
            gameStartEndTimeTextView = itemView.findViewById(R.id.GameStartEndTime);
            gameTicCountTextView = itemView.findViewById(R.id.GameTicCount);
        }
    }
}
