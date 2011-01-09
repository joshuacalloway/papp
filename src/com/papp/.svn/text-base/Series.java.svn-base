package com.papp;

import java.io.Serializable;

import android.util.*;
import android.widget.*;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Series implements Serializable
{
  int id;
  int series;
  String displayName;
  public void setDisplayName(String v) { displayName = v; }
  public String getDisplayName() { return displayName; }
  public void setId(int v) { id = v; }
  public int getId() { return id; }
  public void setSeries(int v) { series = v; }
  public int getSeries() { return series; }

  public static final String AUTHORITY = "com.papp.paddleprovider.series";
  private static final String[] PROJECTION = new String[] {
    Series.Columns._ID, // 0
    Series.Columns.SERIES, // 1
    Series.Columns.DISPLAYNAME
  };


  public static final class Columns implements BaseColumns {
    private Columns() {}
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/series");
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jpc.series";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.jpc.series";
    public static final String DEFAULT_SORT_ORDER = "series ASC";
    public static final String _ID = "_id";
    public static final String DISPLAYNAME = "displayname";
    public static final String SERIES = "series";
  }
  private static final String TAG = "com.papp.series";
}
