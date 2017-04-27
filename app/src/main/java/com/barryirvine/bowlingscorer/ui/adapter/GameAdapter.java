package com.barryirvine.bowlingscorer.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barryirvine.bowlingscorer.R;
import com.barryirvine.bowlingscorer.model.Frame;
import com.barryirvine.bowlingscorer.model.Game;

import static com.barryirvine.bowlingscorer.model.Game.NUM_FRAMES;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private Game mGame;

    public GameAdapter(final Game game) {
        mGame = game;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Frame frame = mGame.getFrames()[position];
        holder.mScoreLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), !mGame.isGameOver() && position == mGame.getCurrentFrame().getNumber() - 1 ? R.color.colorAccent: android.R.color.transparent));
        holder.mFrameTextView.setText(String.valueOf(frame.getNumber()));
        holder.mScore1TextView.setText(frame.getFirstBallScore().getDisplayString());
        holder.mScore2TextView.setText(frame.getSecondBallScore().getDisplayString());
        holder.mScore3TextView.setText(frame.getThirdBallScore().getDisplayString());
        holder.mScore3TextView.setVisibility(position == NUM_FRAMES - 1 ? View.VISIBLE : View.GONE);
        holder.mTotalTextView.setText(mGame.getFrameScoreString(position));
    }

    @Override
    public int getItemCount() {
        return mGame.getFrames().length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView mFrameTextView;
        final TextView mScore1TextView;
        final TextView mScore2TextView;
        final TextView mScore3TextView;
        final View mScoreLayout;
        final TextView mTotalTextView;


        ViewHolder(final View itemView) {
            super(itemView);
            mFrameTextView = (TextView) itemView.findViewById(R.id.frame_number);
            mScore1TextView = (TextView) itemView.findViewById(R.id.score_1);
            mScore2TextView = (TextView) itemView.findViewById(R.id.score_2);
            mScore3TextView = (TextView) itemView.findViewById(R.id.score_3);
            mScoreLayout = itemView.findViewById(R.id.score_layout);
            mTotalTextView = (TextView) itemView.findViewById(R.id.total);
        }
    }
}
