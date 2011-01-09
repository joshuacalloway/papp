package com.papp;

import java.io.*;
import android.util.*;
import android.widget.*;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Matches implements Serializable
{
  int id;
  int clubId;
  int seriesId;
  String matchDate;
  String score;
  Player winPlayer1;
  Player winPlayer2;
  Player losePlayer1;
  Player losePlayer2;
  int winPoints;
  int losePoints;
  int courtId;

  static String[] COLUMNS = new String[] {
    Matches.Columns._ID,
    Matches.Columns.CLUB_ID,
    Matches.Columns.SERIES_ID,
    Matches.Columns.MATCH_DATE,
    Matches.Columns.SCORE,
    Matches.Columns.WIN_PLAYER1_ID,
    Matches.Columns.WIN_PLAYER2_ID,
    Matches.Columns.LOSE_PLAYER1_ID,
    Matches.Columns.LOSE_PLAYER2_ID,
    Matches.Columns.WIN_POINTS,
    Matches.Columns.LOSE_POINTS,
    Matches.Columns.COURT_ID
  };

  public void setWinPlayer1(Player val) { winPlayer1 = val; }
  public void setWinPlayer2(Player val) { winPlayer2 = val; }
  public void setLosePlayer1(Player val) { losePlayer1 = val; }
  public void setLosePlayer2(Player val) { losePlayer2 = val; }

  public Player getWinPlayer1() { return winPlayer1; }
  public Player getWinPlayer2() { return winPlayer2; }
  public Player getLosePlayer1() { return losePlayer1; }
  public Player getLosePlayer2() { return losePlayer2; }

  public String getScore() { return score; }
  public void setScore(String val) { score = val; }
  public void setId(int val) { id = val; }

  public String toString() { return score; }
  public static final String AUTHORITY = "com.papp.paddleprovider.matches";
  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/matches");
  public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jpc.matches";
  public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.jpc.matches";

  public static final class Columns implements BaseColumns {
    private Columns() {}
    public static final String DEFAULT_SORT_ORDER = "match_date ASC";
    public static final String _ID = "_id";

    public static final String CLUB_ID = "club_id ";
    public static final String SERIES_ID = "series_id";
    public static final String MATCH_DATE = "match_date";
    public static final String SCORE = "score";
    public static final String WIN_PLAYER1_ID = "win_player1_id";
    public static final String WIN_PLAYER2_ID = "win_player2_id ";
    public static final String LOSE_PLAYER1_ID = "lose_player1_id";
    public static final String LOSE_PLAYER2_ID = "lose_player2_id";
    public static final String WIN_POINTS = "win_points";
    public static final String LOSE_POINTS = "lose_points";
    public static final String COURT_ID = "court_id";
  }
  private static final String TAG = "com.papp.matches";
}
