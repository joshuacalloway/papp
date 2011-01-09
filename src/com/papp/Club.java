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

public class Club implements Serializable
{
  int id;
  int club;
  String name;
  String address;
  public void setName(String v) { name = v; }
  public String getName() { return name; }
  public void setAddress(String v) { address = v; }
  public String getAddress() { return address; }
  public void setId(int v) { id = v; }
  public int getId() { return id; }
  public void setClub(int v) { club = v; }
  public int getClub() { return club; }
  public static final String AUTHORITY = "com.papp.paddleprovider.club";

  public static final class Columns implements BaseColumns {
    private Columns() {}
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/club");
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jpc.club";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.jpc.club";

    public static final String DEFAULT_SORT_ORDER = "_id ASC";
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String CLUB = "club";
  }
  private static final String TAG = "com.papp.club";
}
