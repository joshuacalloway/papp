
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

import com.papp.Club;

public class Team implements Serializable
{
  int id;
  Club club;
  Series team;

  public void setId(int i) { id = i; }
  public int id() { return id; }

  public void setSeries(Series s) { team = s; }
  public Series getSeries() { return team; }

  public void setClub(Club c) { club = c; }
  public Club getClub() { return club; }

  public static final String AUTHORITY = "com.papp.paddleprovider.team";

  public static final class Columns implements BaseColumns {
    // This class cannot be instantiated
    private Columns() {}

    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/team");

    /**
     * The MIME type of {@link #CONTENT_URI} providing a directory of notes.
     */
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jpc.team";

    /**
     * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
     */
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.jpc.team";

    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "club_id";

    public static final String _ID = "_id";
    public static final String CLUB_ID = "club_id";
    public static final String SERIES_ID = "series_id";

  }
  private static final String TAG = "com.papp.team";
}
