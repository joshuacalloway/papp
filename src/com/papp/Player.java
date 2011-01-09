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
import android.database.sqlite.*;
import android.database.*;

public class Player implements Serializable
{
  int id;
  String name;
  Club club;
  Series series;
  public void setName(String v) { name = v; }
  public String getName() { return name; }
  public void setId(int v) { id = v; }
  public int getId() { return id; }

  public void setSeries(Series v) { series = v; }
  public Series getSeries() { return series; }
  public void setClub(Club v) { club = v; }
  public Club getClub() { return club; }
    
  public static final String AUTHORITY = "com.papp.paddleprovider.player";
  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/player");
  public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jpc.player";
  public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.jpc.player";

  public static final String[] COLUMN_NAMES = new String[] {
    Player.Columns._ID, // 0f
    Player.Columns.NAME, // 1
  };

  public static final class Columns implements BaseColumns {
    private Columns() {}
    public static final String DEFAULT_SORT_ORDER = "name ASC";
    public static final String _ID = "_id";
    public static final String NAME = "name";
  }
  private static final String TAG = "com.papp.player";

}
