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

public class SeriesProvider extends ContentProvider implements PaddleDB {
  private static final String TAG = "com.papp.seriesprovider";
  private static final String TABLE_NAME = SERIES_TABLE_NAME;
  public static final String PROVIDER_NAME = "com.papp.paddleprovider.series";
  public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME + "/series");
  private static HashMap<String, String> projectionMap;
  private static final int SERIES = 1;
  private static final int DISPLAYNAME = 2;
  private static final int SERIES_ID = 3;
    
  private static final UriMatcher uriMatcher;

  /**s
   * This class helps open, create, and upgrade the database file.
   */
  private static class DatabaseHelper extends SQLiteOpenHelper {
    DatabaseHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // public String[] getAllSeries() {
    //   Log.v(TAG, "String[] getAllSeries()");
    //   SQLiteDatabase db = getReadableDatabase();d
    //   Cursor cursor = db.query(TABLE_NAME, new String[] {Series.Columns.DISPLAYNAME}, null, null, null, null, null);
    //   if(cursor.getCount() >0) {
    //     String[] str = new String[cursor.getCount()];
    //     int i = 0;
    //     while (cursor.moveToNext()) {
    //       str[i] = cursor.getString(cursor.getColumnIndex(Series.Columns.DISPLAYNAME));
    //       i++;
    //     }
    //     return str;
    //   }
    //   else {
    //     return new String[] {};
    //   }
    // }

    @Override
      public void onCreate(SQLiteDatabase db) {
      Log.v(TAG, "public void onCreate(SQLiteDatabase db)");
      db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                 + Series.Columns._ID + " INTEGER PRIMARY KEY,"
                 + Series.Columns.DISPLAYNAME + " TEXT,"
                 + Series.Columns.SERIES + " INTEGER,"
                 + ");");
    }

    @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
      db.execSQL("DROP TABLE IF EXISTS series");
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
    Log.v(TAG, "uri : " + uri.toString());
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    qb.setTables(TABLE_NAME);

    switch (uriMatcher.match(uri)) {
    case SERIES:
      qb.setProjectionMap(projectionMap);
      break;
    case SERIES_ID:
      qb.setProjectionMap(projectionMap);
      qb.appendWhere(Series.Columns._ID + "=" + uri.getPathSegments().get(1));
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
    Log.v(TAG, "selection : " + selection);
    Log.v(TAG, "selectionArgs : " + selectionArgs);
    Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
    c.setNotificationUri(getContext().getContentResolver(), uri);
    return c;
  }

  @Override
    public String getType(Uri uri) {
    switch (uriMatcher.match(uri)) {
    case SERIES:
    case SERIES_ID:
      return Series.Columns.CONTENT_ITEM_TYPE;

    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
    // Validate the requested uri
    if (uriMatcher.match(uri) != SERIES) {
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    ContentValues values;
    if (initialValues != null) {
      values = new ContentValues(initialValues);
    } else {
      values = new ContentValues();
    }

    Long now = Long.valueOf(System.currentTimeMillis());

    if (values.containsKey(Series.Columns._ID) == false) {
      values.put(Series.Columns._ID, 911);
    }

    if (values.containsKey(Series.Columns.DISPLAYNAME) == false) {
      values.put(Series.Columns.DISPLAYNAME, "");
    }

    SQLiteDatabase db = openHelper.getWritableDatabase();
    long rowId = db.insert(TABLE_NAME, Series.Columns.DISPLAYNAME, values);
    if (rowId > 0) {
      Uri tmpuri = ContentUris.withAppendedId(Series.Columns.CONTENT_URI, rowId);
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
    case SERIES:
      count = db.delete(TABLE_NAME, where, whereArgs);
      break;

    case SERIES_ID:
      String id = uri.getPathSegments().get(1);
      count = db.delete(TABLE_NAME, Series.Columns._ID + "=" + id
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
    case DISPLAYNAME:
      count = db.update(TABLE_NAME, values, where, whereArgs);
      break;

    case SERIES_ID:
      String id = uri.getPathSegments().get(1);
      count = db.update(TABLE_NAME, values, Series.Columns._ID + "=" + id
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
    uriMatcher.addURI(Series.AUTHORITY, "series", SERIES);
    uriMatcher.addURI(Series.AUTHORITY, "series/#", SERIES_ID);
    
    projectionMap = new HashMap<String, String>();
    projectionMap.put(Series.Columns._ID, Series.Columns._ID);
    projectionMap.put(Series.Columns.DISPLAYNAME, Series.Columns.DISPLAYNAME);
    projectionMap.put(Series.Columns.SERIES, Series.Columns.SERIES);
  }
}
