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

public class PlayerProvider extends ContentProvider implements PaddleDB {
  private static final String TAG = "com.papp.playerprovider";
  
  private static final String TABLE_NAME = "player";
  private static HashMap<String, String> projectionMap;
    
  private static final int PLAYER = 1;
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
                 + Player.Columns._ID + " INTEGER PRIMARY KEY,"
                 + Player.Columns.NAME + " TEXT);");
    }

    @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
      db.execSQL("DROP TABLE IF EXISTS paddle");
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
    case PLAYER:
      qb.setProjectionMap(projectionMap);
      break;

    case PLAYER_ID:
      qb.setProjectionMap(projectionMap);
      qb.appendWhere(Player.Columns._ID + "=" + uri.getPathSegments().get(1));
      break;
    default:
      Log.v(TAG, "uriMatcher.match(uri): " + uriMatcher.match(uri));
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    // If no sort order is specified use the default
    String orderBy;
    if (TextUtils.isEmpty(sortOrder)) {
      orderBy = Player.Columns.DEFAULT_SORT_ORDER;
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
    case PLAYER:
    case PLAYER_ID:
      return Player.CONTENT_ITEM_TYPE;

    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
    // Validate the requested uri
    if (uriMatcher.match(uri) != PLAYER) {
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    ContentValues values;
    if (initialValues != null) {
      values = new ContentValues(initialValues);
    } else {
      values = new ContentValues();
    }

    Long now = Long.valueOf(System.currentTimeMillis());

    // Make sure that the fields are all set
    if (values.containsKey(Player.Columns._ID) == false) {
      Log.v(TAG,"values.containsKey(Player.Columns._ID) == false ");
      values.put(Player.Columns._ID, 911);
    }

    if (values.containsKey(Player.Columns.NAME) == false) {
      values.put(Player.Columns.NAME, "");
    }

    SQLiteDatabase db = openHelper.getWritableDatabase();
    long rowId = db.insert(TABLE_NAME, Player.Columns.NAME, values);
    if (rowId > 0) {
      Uri tmpuri = ContentUris.withAppendedId(Player.CONTENT_URI, rowId);
      getContext().getContentResolver().notifyChange(tmpuri, null);
      return tmpuri;
    }

    // not supposed top get here
    throw new SQLException("Failed to insert row into " + uri);
  }

  @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
    SQLiteDatabase db = openHelper.getWritableDatabase();
    int count;
    switch (uriMatcher.match(uri)) {
    case PLAYER:
      count = db.delete(TABLE_NAME, where, whereArgs);
      break;

    case PLAYER_ID:
      String paddleId = uri.getPathSegments().get(1);
      count = db.delete(TABLE_NAME, Player.Columns._ID + "=" + paddleId
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
    case PLAYER:
      count = db.update(TABLE_NAME, values, where, whereArgs);
      break;

    case PLAYER_ID:
      String paddleId = uri.getPathSegments().get(1);
      count = db.update(TABLE_NAME, values, Player.Columns._ID + "=" + paddleId
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
    uriMatcher.addURI(Player.AUTHORITY, "player", PLAYER);
    uriMatcher.addURI(Player.AUTHORITY, "player/#", PLAYER_ID);
        
    projectionMap = new HashMap<String, String>();
    projectionMap.put(Player.Columns._ID, Player.Columns._ID);
    projectionMap.put(Player.Columns.NAME, Player.Columns.NAME);
  }
}
