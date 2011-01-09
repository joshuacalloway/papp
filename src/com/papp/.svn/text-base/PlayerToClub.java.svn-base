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

public class PlayerToClub
{
  private int id;
  private int clubId;
  private int playerId;

  public PlayerToClub() {
  }
  public void setId(int v) {
    this.id = v;
  }
  public int getId() {
    return this.id;
  } 
  public void setClubId(int v) {
    this.clubId = v;
  }
  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }
  public int getClubId() {
    return this.clubId;
  }
  public int getPlayerId() {
    return this.playerId;
  }
  public static final String AUTHORITY = "com.papp.paddleprovider.playertoclub";
  public static final Uri URI = Uri.parse("content://"+ AUTHORITY + "/playertoclub");
  public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jpc.playertoclub";
  public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.jpc.playertoclub";

  static final String[] COLUMN_NAMES = new String[] {
    PlayerToClub.Columns._ID, // 0
    PlayerToClub.Columns.CLUB_ID, // 1
    PlayerToClub.Columns.PLAYER_ID
  };
  
  public static final class Columns implements BaseColumns {
    private Columns() {}
    public static final String _ID = "_id";
    public static final String PLAYER_ID = "player_id";
    public static final String CLUB_ID = "club_id";
  }

  private static final String TAG = "com.papp.playertoclub";
}
