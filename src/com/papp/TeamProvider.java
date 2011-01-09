package com.papp;

import java.util.*;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

public class TeamProvider extends ContentProvider implements PaddleDB {
  private static final String TAG = "com.papp.teamprovider";
  private static final String TABLE_NAME = TEAM_TABLE_NAME;
  public static final String PROVIDER_NAME = "com.papp.paddleprovider.team";
  public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME + "/team");
  private static HashMap<String, String> projectionMap;
    
  private static final int TEAM_ID = 3;
  private static final int SERIES_ID = 1;
  private static final int CLUB_ID = 2;
    
  private static final UriMatcher uriMatcher;

  /**s
   * This class helps open, create, and upgrade the database file.
   */
  private static class DatabaseHelper extends SQLiteOpenHelper {
    DatabaseHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
      public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, "public void onCreate(SQLiteDatabase db)");
				throw new RuntimeException( "unimplemented" );
      }

    @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "public void onCreate(SQLiteDatabase db)");
				throw new RuntimeException( "unimplemented" );
      }
  }

  private DatabaseHelper openHelper;

  @Override
    public boolean onCreate() {
    Log.v(TAG,"public boolean onCreate()");
    openHelper = new DatabaseHelper(getContext());
    return true;
  }

  @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    Log.v(TAG,"public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)");
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    qb.setTables(TABLE_NAME);

    switch (uriMatcher.match(uri)) {
    case TEAM_ID:
      qb.setProjectionMap(projectionMap);
      qb.appendWhere(Team.Columns._ID + "=" + uri.getPathSegments().get(1));
      break;
    case CLUB_ID:
      qb.setProjectionMap(projectionMap);
      qb.appendWhere(Team.Columns.CLUB_ID + "=" + uri.getPathSegments().get(1));
      break;
    case SERIES_ID:
      qb.setProjectionMap(projectionMap);
      qb.appendWhere(Team.Columns.SERIES_ID + "=" + uri.getPathSegments().get(1));
      break;
    default:
      Log.v(TAG, "uriMatcher.match(uri): " + uriMatcher.match(uri));
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    // If no sort order is specified use the default
    String orderBy;
    if (TextUtils.isEmpty(sortOrder)) {
      orderBy = Series.Columns.DEFAULT_SORT_ORDER;
    } else {
      orderBy = sortOrder;
    }
    SQLiteDatabase db = openHelper.getReadableDatabase();
    Cursor c = qb.query(db, projection, null, null, null, null, null);
    // selection, selectionArgs, null, null, orderBy);

    // Tell the cursor what uri to watch, so it knows when its source data changes
    c.setNotificationUri(getContext().getContentResolver(), uri);
    return c;
  }

  @Override
    public String getType(Uri uri) {
    switch (uriMatcher.match(uri)) {
    case CLUB_ID:
    case SERIES_ID:
    case TEAM_ID:
      return Team.Columns.CONTENT_ITEM_TYPE;

    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
				throw new RuntimeException( "unimplemented" );
    }

  @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
				throw new RuntimeException( "unimplemented" );
    }
  @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
				throw new RuntimeException( "unimplemented" );
    }

  static {
    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(Team.AUTHORITY, "team/#", TEAM_ID);
    uriMatcher.addURI(Team.AUTHORITY, "club/#", CLUB_ID);
    uriMatcher.addURI(Team.AUTHORITY, "series/#", SERIES_ID);
    
    projectionMap = new HashMap<String, String>();
    projectionMap.put(Team.Columns._ID, Team.Columns._ID);
    projectionMap.put(Team.Columns.CLUB_ID, Team.Columns.CLUB_ID);
    projectionMap.put(Team.Columns.SERIES_ID, Team.Columns.SERIES_ID);
  }
}

