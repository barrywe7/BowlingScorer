package com.barryirvine.bowlingscorer.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.barryirvine.bowlingscorer.R;
import com.barryirvine.bowlingscorer.model.Frame;
import com.barryirvine.bowlingscorer.model.Game;
import com.barryirvine.bowlingscorer.model.ScoreValue;
import com.barryirvine.bowlingscorer.prefs.SharedPrefs;
import com.barryirvine.bowlingscorer.ui.adapter.GameAdapter;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Game mGame;
    private ImageButton mDeleteButton;
    private Button mZeroButton;
    private Button mOneButton;
    private Button mTwoButton;
    private Button mThreeButton;
    private Button mFourButton;
    private Button mFiveButton;
    private Button mSixButton;
    private Button mSevenButton;
    private Button mEightButton;
    private Button mNineButton;
    private Button mSpareButton;
    private Button mStrikeButton;
    private Button mNewButton;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO: Store game in shared prefs
        mZeroButton = (Button) findViewById(R.id.zero);
        mOneButton = (Button) findViewById(R.id.one);
        mTwoButton = (Button) findViewById(R.id.two);
        mThreeButton = (Button) findViewById(R.id.three);
        mFourButton = (Button) findViewById(R.id.four);
        mFiveButton = (Button) findViewById(R.id.five);
        mSixButton = (Button) findViewById(R.id.six);
        mSevenButton = (Button) findViewById(R.id.seven);
        mEightButton = (Button) findViewById(R.id.eight);
        mNineButton = (Button) findViewById(R.id.nine);
        mSpareButton = (Button) findViewById(R.id.spare);
        mStrikeButton = (Button) findViewById(R.id.strike);
        mNewButton = (Button) findViewById(R.id.new_game);
        mDeleteButton = (ImageButton) findViewById(R.id.delete);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mGame = SharedPrefs.getGame(this);
        createGame();
    }

    private void createGame() {
        mAdapter = new GameAdapter(mGame);
        mRecyclerView.setAdapter(mAdapter);
        enableButtons();
    }

    @Override
    protected void onStop() {
        SharedPrefs.setGame(this, mGame);
        super.onStop();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.delete:
                mGame.removeScore();
                mAdapter.notifyDataSetChanged();
                enableButtons();
                break;
            case R.id.new_game:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.dialog_title_new_game)
                        .setMessage(R.string.dialog_message_new_game)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mGame = new Game();
                                createGame();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //noop
                            }
                        })
                        .create()
                        .show();
                break;
            default:
                // All the buttons that are related to score have a String tag value that matches the enum name
                mGame.addScore(ScoreValue.valueOf((String) v.getTag()));
                mAdapter.notifyDataSetChanged();
                enableButtons();
        }
    }

    private void enableButtons() {
        final Frame currentFrame = mGame.getCurrentFrame();
        final int frameScore = currentFrame.getFrameScore();
        mSpareButton.setEnabled(mGame.isSparePossible());
        mStrikeButton.setEnabled(mGame.isStrikePossible());
        mZeroButton.setEnabled(!mGame.isGameOver());
        mOneButton.setEnabled(10 - frameScore > 1 && !mGame.isGameOver());
        mTwoButton.setEnabled(10 - frameScore > 2 && !mGame.isGameOver());
        mThreeButton.setEnabled(10 - frameScore > 3 && !mGame.isGameOver());
        mFourButton.setEnabled(10 - frameScore > 4 && !mGame.isGameOver());
        mFiveButton.setEnabled(10 - frameScore > 5 && !mGame.isGameOver());
        mSixButton.setEnabled(10 - frameScore > 6 && !mGame.isGameOver());
        mSevenButton.setEnabled(10 - frameScore > 7 && !mGame.isGameOver());
        mEightButton.setEnabled(10 - frameScore > 8 && !mGame.isGameOver());
        mNineButton.setEnabled(10 - frameScore > 9 && !mGame.isGameOver());
        mNewButton.setEnabled(!mGame.isNewGame());
        mDeleteButton.setEnabled(!mGame.isNewGame());
    }
}
