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

public class MatchesProvider extends ContentProvider implements PaddleDB {
  private static final String TAG = "com.papp.matchesprovider";
  private static final String TABLE_NAME = MATCHES_TABLE_NAME;
  private static HashMap<String, String> projectionMap;
    
  private static final int MATCHES = 1;
  private static final int MATCHES_ID = 2;
    
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
      throw new RuntimeException("onCreate unimplemented.");
    }

    @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
      onCreate(db);
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
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    qb.setTables(TABLE_NAME);

    switch (uriMatcher.match(uri)) {
    case MATCHES:
      qb.setProjectionMap(projectionMap);
      break;

    case MATCHES_ID:
      qb.setProjectionMap(projectionMap);
      qb.appendWhere(Matches.Columns._ID + "=" + uri.getPathSegments().get(1));
      break;
    default:
      Log.v(TAG, "uriMatcher.match(uri): " + uriMatcher.match(uri));
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    // If no sort order is specified use the default
    String orderBy;
    if (TextUtils.isEmpty(sortOrder)) {
      orderBy = Matches.Columns.DEFAULT_SORT_ORDER;
    } else {
      orderBy = sortOrder;
    }

    // Get the database and run the query
    SQLiteDatabase db = openHelper.getReadableDatabase();
    Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

    // Tell the cursor what uri to watch, so it knows when its source data changes
    c.setNotificationUri(getContext().getContentResolver(), uri);
    return c;
  }

  @Override
    public String getType(Uri uri) {
    switch (uriMatcher.match(uri)) {
    case MATCHES:
    case MATCHES_ID:
      return Matches.CONTENT_ITEM_TYPE;

    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
    throw new RuntimeException("insert inimplemented.");
  }

  @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
    throw new RuntimeException("delete inimplemented.");
  }

  @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
    throw new RuntimeException("update inimplemented.");
  }

  static {
    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(Matches.AUTHORITY, "matches", MATCHES);
    uriMatcher.addURI(Matches.AUTHORITY, "matches/#", MATCHES_ID);
        
    projectionMap = new HashMap<String, String>();
    projectionMap.put(Matches.Columns._ID, Matches.Columns._ID);
    projectionMap.put(Matches.Columns.CLUB_ID, Matches.Columns.CLUB_ID);
    projectionMap.put(Matches.Columns.SERIES_ID, Matches.Columns.SERIES_ID);
    projectionMap.put(Matches.Columns.MATCH_DATE, Matches.Columns.MATCH_DATE);
    projectionMap.put(Matches.Columns.SCORE,Matches.Columns.SCORE);
    projectionMap.put(Matches.Columns.WIN_PLAYER1_ID, Matches.Columns.WIN_PLAYER1_ID);
    projectionMap.put(Matches.Columns.WIN_PLAYER2_ID, Matches.Columns.WIN_PLAYER2_ID);
    projectionMap.put(Matches.Columns.LOSE_PLAYER1_ID, Matches.Columns.LOSE_PLAYER1_ID);
    projectionMap.put(Matches.Columns.LOSE_PLAYER2_ID, Matches.Columns.LOSE_PLAYER2_ID);
    projectionMap.put(Matches.Columns.WIN_POINTS, Matches.Columns.WIN_POINTS);
    projectionMap.put(Matches.Columns.LOSE_POINTS, Matches.Columns.LOSE_POINTS);
    projectionMap.put(Matches.Columns.COURT_ID, Matches.Columns.COURT_ID);
  }
}
