package com.papp;

import android.util.*;
import android.widget.*;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class PlayerToSeries
{
  private int id;
  private int seriesId;
  private int playerId;

  public PlayerToSeries() {
  }
  public void setId(int v) {
    this.id = v;
  }
  public int getId() {
    return this.id;
  }
  public void setSeriesId(int v) {
    this.seriesId = v;
  }
  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }
  public int getSeriesId() {
    return this.seriesId;
  }
  public int getPlayerId() {
    return this.playerId;
  }
  public static final String AUTHORITY = "com.papp.paddleprovider.playertoseries";
  public static final Uri SERIES_URI = Uri.parse("content://"+ AUTHORITY + "/series");
  public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jpc.playertoseries";
  public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.jpc.playertoseries";

  static final String[] COLUMN_NAMES = new String[] {
    PlayerToSeries.Columns._ID, // 0
    PlayerToSeries.Columns.SERIES_ID, // 1
    PlayerToSeries.Columns.PLAYER_ID
  };
  
  public static final class Columns implements BaseColumns {
    private Columns() {}
    public static final String DEFAULT_SORT_ORDER = "series_id ASC";
    public static final String _ID = "_id";
    public static final String PLAYER_ID = "player_id";
    public static final String SERIES_ID = "series_id";
  }

  private static final String TAG = "com.papp.playertoseries";
}
