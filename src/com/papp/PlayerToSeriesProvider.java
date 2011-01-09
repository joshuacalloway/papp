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

public class PlayerToSeriesProvider extends ContentProvider implements PaddleDB {
  private static final String TAG = "com.papp.playertoseriesprovider";
  private static final String TABLE_NAME = PLAYER_TO_SERIES_TABLE_NAME;
  private static HashMap<String, String> projectionMap;
    
  private static final int SERIES_ID = 1;
  private static final int PLAYER_ID = 2;
    
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
      db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                 + PlayerToSeries.Columns._ID + " INTEGER PRIMARY KEY,"
                 + PlayerToSeries.Columns.PLAYER_ID + " INTEGER,"
                 + PlayerToSeries.Columns.SERIES_ID + " INTEGER"
                 + ");");
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
    case SERIES_ID:
      qb.setProjectionMap(projectionMap);
      // qb.appendWhere(PlayerToSeries.Columns.SERIES_ID + "=" + uri.getPathSegments().get(0));
      break;

    case PLAYER_ID:
      qb.setProjectionMap(projectionMap);
      qb.appendWhere(PlayerToSeries.Columns.PLAYER_ID + "=" + uri.getPathSegments().get(1));
      break;
    default:
      Log.v(TAG, "PlayerToSeriesProvider...uriMatcher.match(uri) : " + uriMatcher.match(uri));
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    // // If no sort order is specified use the default
    // String orderBy;
    // if (TextUtils.isEmpty(sortOrder)) {
    //     orderBy = PlayerToSeries.Columns.DEFAULT_SORT_ORDER;
    // } else {
    //     orderBy = sortOrder;
    // }

    // Get the database and run the query
    SQLiteDatabase db = openHelper.getReadableDatabase();
    Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, null); // orderBy);

    // Tell the cursor what uri to watch, so it knows when its source data changes
    c.setNotificationUri(getContext().getContentResolver(), uri);
    return c;
  }

  @Override
    public String getType(Uri uri) {
    switch (uriMatcher.match(uri)) {
    case PLAYER_ID:
      return PlayerToSeries.CONTENT_ITEM_TYPE;

    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
    // Validate the requested uri
    throw new RuntimeException("unimplemented");
  }
  @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
    SQLiteDatabase db = openHelper.getWritableDatabase();
    int count;
    switch (uriMatcher.match(uri)) {
    case SERIES_ID:
      count = db.delete(TABLE_NAME, where, whereArgs);
      break;

    case PLAYER_ID:
      String paddleId = uri.getPathSegments().get(1);
      count = db.delete(TABLE_NAME, PlayerToSeries.Columns._ID + "=" + paddleId
                        + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
      break;

    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);
    return count;
  }

  @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
    SQLiteDatabase db = openHelper.getWritableDatabase();
    int count;
    switch (uriMatcher.match(uri)) {
    case SERIES_ID:
      count = db.update(TABLE_NAME, values, where, whereArgs);
      break;

    case PLAYER_ID:
      String paddleId = uri.getPathSegments().get(1);
      count = db.update(TABLE_NAME, values, PlayerToSeries.Columns._ID + "=" + paddleId
                        + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
      break;

    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);
    return count;
  }

  static {
    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(PlayerToSeries.AUTHORITY, "player", PLAYER_ID);
    uriMatcher.addURI(PlayerToSeries.AUTHORITY, "series", SERIES_ID);
    
    projectionMap = new HashMap<String, String>();
    projectionMap.put(PlayerToSeries.Columns._ID, PlayerToSeries.Columns._ID);
    projectionMap.put(PlayerToSeries.Columns.PLAYER_ID, PlayerToSeries.Columns.PLAYER_ID);
    projectionMap.put(PlayerToSeries.Columns.SERIES_ID, PlayerToSeries.Columns.SERIES_ID);
  }
}
