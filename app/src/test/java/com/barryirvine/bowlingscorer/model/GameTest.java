package com.barryirvine.bowlingscorer.model;

import org.junit.Before;
import org.junit.Test;

import static com.barryirvine.bowlingscorer.model.ScoreValue.*;
import static org.junit.Assert.*;

public class GameTest {

    private Game mGame;

    @Before
    public void setUp() {
        mGame = new Game();
    }

    private void rollMany(final int rolls, final ScoreValue scoreValue) {
        for (int i=0; i< rolls; i++) {
            mGame.addScore(scoreValue);
        }
    }

    private void rollPair(final int pairs, final ScoreValue firstBall, final ScoreValue secondBall) {
        for (int i=0; i< pairs; i++) {
            mGame.addScore(firstBall);
            mGame.addScore(secondBall);
        }
    }

    @Test
    public void TestZero() {
        assertTrue(mGame.isNewGame());
        rollMany(20, ZERO);
        assertEquals(0, mGame.getCurrentScore());
        assertTrue(mGame.isGameOver());
    }

    @Test
    public void TestOne() {
        assertTrue(mGame.isNewGame());
        rollMany(20, ONE);
        assertEquals(20, mGame.getCurrentScore());
        assertTrue(mGame.isGameOver());
    }

    @Test
    public void TestPerfect() {
        assertTrue(mGame.isNewGame());
        rollMany(12, STRIKE);
        assertEquals(300, mGame.getCurrentScore());
        assertTrue(mGame.isGameOver());
    }

    @Test
    public void testOneStrike() {
        assertTrue(mGame.isNewGame());
        assertEquals(mGame.getCurrentFrameNumber(), 1);
        mGame.addScore(STRIKE);
        assertEquals(mGame.getCurrentFrameNumber(), 2);
        mGame.addScore(THREE);
        assertEquals(mGame.getCurrentFrameNumber(), 2);
        mGame.addScore(FOUR);
        assertEquals(mGame.getCurrentFrameNumber(), 3);
        rollMany(16, ZERO);
        assertEquals(24, mGame.getCurrentScore());
        assertTrue(mGame.isGameOver());
    }

    @Test
    public void testOneSpare() {
        assertTrue(mGame.isNewGame());
        mGame.addScore(FIVE);
        mGame.addScore(SPARE);
        mGame.addScore(THREE);
        rollMany(17, ZERO);
        assertEquals(16, mGame.getCurrentScore());
        assertTrue(mGame.isGameOver());
    }

    @Test
    public void testWastedSpare() {
        assertTrue(mGame.isNewGame());
        mGame.addScore(FIVE);
        mGame.addScore(SPARE);
        mGame.addScore(ZERO);
        rollMany(17, ZERO);
        assertEquals(10, mGame.getCurrentScore());
        assertTrue(mGame.isGameOver());
    }

    @Test
    public void testAllNines() {
        assertTrue(mGame.isNewGame());
        rollPair(10, NINE, ZERO);
        assertEquals(90, mGame.getCurrentScore());
        assertTrue(mGame.isGameOver());
    }

    @Test
    public void testAllSpares() {
        assertTrue(mGame.isNewGame());
        rollPair(10, FIVE, SPARE);
        mGame.addScore(FIVE);
        assertEquals(150, mGame.getCurrentScore());
        assertTrue(mGame.isGameOver());
    }

    @Test
    public void variedScores() {
        assertTrue(mGame.isNewGame());
        assertEquals(mGame.getCurrentFrameNumber(), 1);
        mGame.addScore(STRIKE);
        assertEquals(mGame.getCurrentFrameNumber(), 2);
        mGame.addScore(SEVEN);
        mGame.addScore(SPARE);
        assertEquals(mGame.getCurrentFrameNumber(), 3);
        mGame.addScore(NINE);
        mGame.addScore(ZERO);
        assertEquals(mGame.getCurrentFrameNumber(), 4);
        mGame.addScore(STRIKE);
        assertEquals(mGame.getCurrentFrameNumber(), 5);
        mGame.addScore(ZERO);
        mGame.addScore(EIGHT);
        assertEquals(mGame.getCurrentFrameNumber(), 6);
        mGame.addScore(EIGHT);
        mGame.addScore(SPARE);
        assertEquals(mGame.getCurrentFrameNumber(), 7);
        mGame.addScore(ZERO);
        mGame.addScore(SIX);
        assertEquals(mGame.getCurrentFrameNumber(), 8);
        rollMany(3, STRIKE);
        assertEquals(mGame.getCurrentFrameNumber(), 10);
        mGame.addScore(EIGHT);
        mGame.addScore(ONE);
        assertEquals(167, mGame.getCurrentScore());
        assertTrue(mGame.isGameOver());
    }

}