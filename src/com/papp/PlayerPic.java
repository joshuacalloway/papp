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

public class PlayerPic
{
  public static final String AUTHORITY = "com.papp.paddleprovider.playerpic";
  private String picUrl;
  private int playerId;
  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/playerpics");

  static final String[] COLUMN_NAMES = new String[] {
    PlayerPic.Columns._ID, // 0
    PlayerPic.Columns.PLAYER_ID, // 1
    PlayerPic.Columns.PIC_URL, // 2
  };
  public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jpc.paddle";
  public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.jpc.paddle";
    
  public PlayerPic() {
    
  }
  public void setPicUrl(String picUrl) {
    this.picUrl = picUrl;
  }
  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }
  public String getPicUrl() {
    return this.picUrl;
  }
  public int getPlayerId() {
    return this.playerId;
  }
  
  public static final class Columns implements BaseColumns {
    // This class cannot be instantiated
    private Columns() {}
    
    /**
     * The content:// style URL for this table
     */
    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "pic_url ASC";

    public static final String _ID = "_id";
    public static final String PLAYER_ID = "player_id";
    public static final String PIC_URL = "pic_url";

  }

  private static final String TAG = "com.papp.playerpic";
}
