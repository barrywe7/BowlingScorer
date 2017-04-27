package com.barryirvine.bowlingscorer.model;

public class Game {

    public static final int NUM_FRAMES = 10;
    private static final int MAX_ROLLS = 21;
    private int mCurrentFrame = 1;

    private Frame[] mFrames = new Frame[NUM_FRAMES];
    private Integer[] mFrameScores = new Integer[NUM_FRAMES];
    private ScoreValue[] mRolls = new ScoreValue[MAX_ROLLS];
    private int mCurrentRoll = 0;
    private int mCurrentScore = 0;

    public Game() {
        for (int i = 0; i < NUM_FRAMES; i++) {
            mFrames[i] = new Frame(i + 1);
        }
    }

    public Frame[] getFrames() {
        return mFrames;
    }

    public Frame getCurrentFrame() {
        return mFrames[mCurrentFrame - 1];
    }

    int getCurrentFrameNumber() {
        return mCurrentFrame;
    }

    public String getFrameScoreString(final int frameNumber) {
        return mFrameScores[frameNumber] == null ? "" : String.valueOf(mFrameScores[frameNumber]);
    }

    private void computeFrameScores() {
        int frame = 0;
        mCurrentScore = 0;
        //Clear existing values, they'll be recomputed
        for (int i = 0; i < NUM_FRAMES; i++) {
            mFrameScores[i] = null;
        }
        boolean needToHandleAnotherBall = false;
        for (int i = 0; i < mCurrentRoll; i++) {
            if (mRolls[i] == ScoreValue.STRIKE && (i + 2) < mCurrentRoll) {
                // If the most significant ball is a spare the one before it is already included
                if (mRolls[i + 2] == ScoreValue.SPARE) {
                    mCurrentScore += 20;
                } else {
                    mCurrentScore += 10 + mRolls[i + 1].getValue() + mRolls[i + 2].getValue();
                }
                mFrameScores[frame] = mCurrentScore;
                if (frame < 9) {
                    frame++;
                }
                needToHandleAnotherBall = false;
            } else if (mRolls[i] == ScoreValue.SPARE && (i + 1) < mCurrentRoll) {
                mCurrentScore += 10 + mRolls[i + 1].getValue();
                mFrameScores[frame++] = mCurrentScore;
                needToHandleAnotherBall = false;
            } else if (needToHandleAnotherBall) {
                if (mRolls[i - 1] != ScoreValue.STRIKE && mRolls[i] != ScoreValue.SPARE) {
                    mCurrentScore += mRolls[i - 1].getValue() + mRolls[i].getValue();
                    mFrameScores[frame++] = mCurrentScore;
                    needToHandleAnotherBall = false;
                }
            } else {
                // In this case we've already added the strike score in so we don't need to do it again.
                // There's probably a better way to formulate this but I ran out of time
                needToHandleAnotherBall = !(frame == NUM_FRAMES - 1 && mRolls[i - 2] == ScoreValue.STRIKE) ;
            }
        }
    }

    public void addScore(final ScoreValue scoreValue) {
        mRolls[mCurrentRoll++] = scoreValue;
        getCurrentFrame().addScore(scoreValue);
        computeFrameScores();
        if (getCurrentFrame().isComplete()) {
            if (mCurrentFrame < NUM_FRAMES) {
                mCurrentFrame++;
            }
        }
    }

    public void removeScore() {
        mRolls[--mCurrentRoll] = null;
        if (getCurrentFrame().isNewFrame()) {
            mCurrentFrame--;
        }
        getCurrentFrame().removeScore();
        computeFrameScores();
    }

    public boolean isGameOver() {
        return mCurrentFrame == 10 && getCurrentFrame().isComplete();
    }

    public boolean isStrikePossible() {
        return getCurrentFrame().isNewFrame() || mCurrentFrame == NUM_FRAMES
                && !getCurrentFrame().isComplete()
                && getCurrentFrame().getLastScore().getValue() == 10;
    }

    public boolean isSparePossible() {
        return mCurrentFrame < 10 && !getCurrentFrame().isNewFrame() ||
                mCurrentFrame == 10 && !getCurrentFrame().isComplete() && getCurrentFrame().getLastScore() != null && getCurrentFrame().getLastScore().getValue() < 10;
    }

    public boolean isNewGame() {
        return mCurrentRoll == 0;
    }

    public int getCurrentScore() {
        return mCurrentScore;
    }
}
