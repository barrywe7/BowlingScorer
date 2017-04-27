package com.barryirvine.bowlingscorer.model;

import android.support.annotation.StringRes;

import com.barryirvine.bowlingscorer.R;

public enum ScoreValue {
    ZERO(0, R.string.zero),
    ONE(1, R.string.one),
    TWO(2, R.string.two),
    THREE(3, R.string.three),
    FOUR(4, R.string.four),
    FIVE(5, R.string.five),
    SIX(6, R.string.six),
    SEVEN(7, R.string.seven),
    EIGHT(8, R.string.eight),
    NINE(9, R.string.nine),
    STRIKE(10, R.string.strike),
    SPARE(10, R.string.spare),
    EMPTY(0, R.string.blank);

    private final int mValue;
    @StringRes
    private final int mDisplayString;

    ScoreValue(final int value, @StringRes final int displayString) {
        mValue = value;
        mDisplayString = displayString;
    }

    public int getValue() {
        return mValue;
    }

    public int getDisplayString() {
        return mDisplayString;
    }
}
