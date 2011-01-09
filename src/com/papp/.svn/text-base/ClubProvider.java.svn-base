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

public class ClubProvider extends ContentProvider implements PaddleDB {
  private static final String TAG = "com.papp.clubprovider";
  private static final String TABLE_NAME = CLUB_TABLE_NAME;
  public static final String PROVIDER_NAME = "com.papp.paddleprovider.club";
  public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME + "/club");
  private static HashMap<String, String> projectionMap;
    
  private static final int CLUB = 1;
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
      db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                 + Club.Columns._ID + " INTEGER PRIMARY KEY,"
                 + Club.Columns.NAME + " TEXT,"
                 + Club.Columns.ADDRESS + " TEXT,"
                 + ");");
    }

    @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
      db.execSQL("DROP TABLE IF EXISTS club");
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
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    Log.v(TAG,"public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)");
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    qb.setTables(TABLE_NAME);

    switch (uriMatcher.match(uri)) {
    case CLUB:
      qb.setProjectionMap(projectionMap);
      break;
    case CLUB_ID:
      qb.setProjectionMap(projectionMap);
      qb.appendWhere(Club.Columns._ID + "=" + uri.getPathSegments().get(1));
      break;
    default:
      Log.v(TAG, "uriMatcher.match(uri): " + uriMatcher.match(uri));
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    // If no sort order is specified use the default
    String orderBy;
    if (TextUtils.isEmpty(sortOrder)) {
      orderBy = Club.Columns.DEFAULT_SORT_ORDER;
    } else {
      orderBy = sortOrder;
    }
    SQLiteDatabase db = openHelper.getReadableDatabase();
    Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
    // Tell the cursor what uri to watch, so it knows when its source data changes
    c.setNotificationUri(getContext().getContentResolver(), uri);
    return c;
  }

  @Override
    public String getType(Uri uri) {
    switch (uriMatcher.match(uri)) {
    case CLUB:
    case CLUB_ID:
      return Club.Columns.CONTENT_ITEM_TYPE;

    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
    // Validate the requested uri
    if (uriMatcher.match(uri) != CLUB) {
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    ContentValues values;
    if (initialValues != null) {
      values = new ContentValues(initialValues);
    } else {
      values = new ContentValues();
    }

    Long now = Long.valueOf(System.currentTimeMillis());

    if (values.containsKey(Club.Columns._ID) == false) {
      values.put(Club.Columns._ID, 911);
    }

    if (values.containsKey(Club.Columns.NAME) == false) {
	values.put(Club.Columns.NAME, "");
    }

    SQLiteDatabase db = openHelper.getWritableDatabase();
    long rowId = db.insert(TABLE_NAME, Club.Columns.NAME, values);
    if (rowId > 0) {
	Uri tmpuri = ContentUris.withAppendedId(Club.Columns.CONTENT_URI, rowId);
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
    case CLUB:
      count = db.delete(TABLE_NAME, where, whereArgs);
      break;

    case CLUB_ID:
      String id = uri.getPathSegments().get(1);
      count = db.delete(TABLE_NAME, Club.Columns._ID + "=" + id
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
    case CLUB:
      count = db.update(TABLE_NAME, values, where, whereArgs);
      break;

    case CLUB_ID:
      String id = uri.getPathSegments().get(1);
      count = db.update(TABLE_NAME, values, Club.Columns._ID + "=" + id
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
    uriMatcher.addURI(Club.AUTHORITY, "club", CLUB);
    uriMatcher.addURI(Club.AUTHORITY, "club/#", CLUB_ID);
    
    projectionMap = new HashMap<String, String>();
    projectionMap.put(Club.Columns._ID, Club.Columns._ID);
    projectionMap.put(Club.Columns.NAME, Club.Columns.NAME);
    projectionMap.put(Club.Columns.CLUB, Club.Columns.CLUB);
  }
}
