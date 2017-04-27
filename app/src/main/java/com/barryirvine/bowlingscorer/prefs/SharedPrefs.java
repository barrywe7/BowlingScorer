package com.barryirvine.bowlingscorer.prefs;

import android.content.Context;
import android.preference.PreferenceManager;

import com.barryirvine.bowlingscorer.model.Game;
import com.google.gson.GsonBuilder;

public class SharedPrefs {

    private static final String GAME = "com.barryirvine.bowlingscorer.GAME";

    public static Game getGame(final Context context) {
        final String jsonString = PreferenceManager.getDefaultSharedPreferences(context).getString(GAME, null);
        return jsonString == null ? new Game() : new GsonBuilder().create().fromJson(jsonString, Game.class);
    }

    public static void setGame(final Context context, final Game game) {
        final String jsonString = new GsonBuilder().create().toJson(game);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(GAME, jsonString).apply();
    }
}
