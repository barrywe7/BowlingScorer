package com.barryirvine.bowlingscorer.model;

import java.util.ArrayList;
import java.util.List;

import static com.barryirvine.bowlingscorer.model.Game.NUM_FRAMES;

public class Frame {

    private final int mNumber;
    private int mBall = 1;
    private final List<ScoreValue> mScoreValues = new ArrayList<>();

    Frame(final int number) {
        mNumber = number;
    }

    public int getNumber() {
        return mNumber;
    }

    boolean isComplete() {
        return mScoreValues.size() == getNumberOfBallsInFrame();
    }

    boolean isNewFrame() {
        return mBall == 1;
    }

    public ScoreValue getFirstBallScore() {
        return mScoreValues.size() > 0 ? mScoreValues.get(0) : ScoreValue.EMPTY;
    }

    public ScoreValue getSecondBallScore() {
        return mScoreValues.size() > 1 ? mScoreValues.get(1) : ScoreValue.EMPTY;
    }

    public ScoreValue getThirdBallScore() {
        return mScoreValues.size() > 2 ? mScoreValues.get(2) : ScoreValue.EMPTY;
    }

    /*
    * Used to calculate which keys are enabled - does not compound scores across frames.
     */
    public int getFrameScore() {
        int score = 0;
        if (!isNewFrame() && !(getNumberOfBallsInFrame() == 3 && (mScoreValues.get(mScoreValues.size() -1) == ScoreValue.SPARE || mScoreValues.get(mScoreValues.size() -1) == ScoreValue.STRIKE))) {
            for (int i = mScoreValues.size() - 1; i >= 0; i--) {
                score += mScoreValues.get(i).getValue();
                // This is if the last ball is a spare
                if (score == 10) {
                    break;
                }
            }
            // Special case ignore STRIKE score the final bonus ball
            if (getNumberOfBallsInFrame() == 3 && mScoreValues.get(0) == ScoreValue.STRIKE) {
                score = mScoreValues.get(1).getValue();
            }
        }
        return score;
    }

    public int getNumberOfBallsInFrame() {
        return mNumber == NUM_FRAMES && (mScoreValues.contains(ScoreValue.STRIKE) || mScoreValues.contains(ScoreValue.SPARE)) ? 3 : 2;
    }

    void addScore(final ScoreValue scoreValue) {
        mScoreValues.add(scoreValue);
        mBall++;
        if (scoreValue == ScoreValue.STRIKE) {
            if (mNumber < NUM_FRAMES) {
                mScoreValues.add(ScoreValue.EMPTY);
            }
        }
    }

    void removeScore() {
        for (int i = mScoreValues.size() - 1; i >= 0; i--) {
            final ScoreValue removed = mScoreValues.remove(i);
            // If the first ball was a strike then we still need to remove that score
            if (removed != ScoreValue.EMPTY) {
                break;
            }
        }
        mBall--;
    }

    ScoreValue getLastScore() {
        return mScoreValues.size() == 0 ? null : mScoreValues.get(mScoreValues.size() - 1);
    }
}
