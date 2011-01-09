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

public class PlayerToTeam
{
  private int id;
  private int playerId;
  private int teamId;

  public PlayerToTeam() {
  }
  public void setId(int v) {
    this.id = v;
  }
  public int getId() {
    return this.id;
  }
  public int getPlayerId() {
    return this.playerId;
  }
  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }
  public void setTeamId(int v) {
    this.teamId = v;
  }
  public int getTeamId() {
    return this.teamId;
  }
  public static final String AUTHORITY = 
    "com.papp.paddleprovider.playertoteam";

  public static final Uri CONTENT_URI =
    Uri.parse("content://"+ AUTHORITY + "/playertoteam");

    public static final String CONTENT_TYPE =      "vnd.android.cursor.dir/vnd.jpc.paddle";

    /**
     * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
     */
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.jpc.paddle";
    
  public static final class Columns implements BaseColumns {
    // This class cannot be instantiated
    private Columns() {}


    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "player_id ASC";

    public static final String _ID = "_id";
    public static final String PLAYER_ID = "player_id";
    public static final String TEAM_ID = "team_id";

  }

  private static final String TAG = "com.papp.playertoteam";
}
