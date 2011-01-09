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

public class PlayerPicProvider extends ContentProvider implements PaddleDB {
  private static final String TAG = "com.papp.playerpicprovider";
  private static final String TABLE_NAME = "player_pic";

  public static final String PROVIDER_NAME = "com.papp.paddleprovider.playerpic";
  public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME + "/playerpics");
  private static HashMap<String, String> projectionMap;
    
  private static final int PIC_URL = 1;
  private static final int PLAYER_ID = 2;
    
  private static final UriMatcher uriMatcher;

  /**s
   * This class helps open, create, and upgrade the database file.
   */
  private static class DatabaseHelper extends SQLiteOpenHelper {
    DatabaseHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // public List<PlayerPic> getAllPlayerPics() {
    //   List<PlayerPic> ret = new ArrayList<PlayerPic>();
    //   SQLiteDatabase db = getReadableDatabase();
    //   Cursor cursor = db.query(TABLE_NAME, new String[] {PlayerPic.Columns.PLAYER_ID, PlayerPic.Columns.PIC_URL}, null, null, null, null, null);
    //   if(cursor.getCount() >0) {
    //     while (cursor.moveToNext()) {
    //       PlayerPic pic = new PlayerPic();
    //       pic.setPlayerId(cursor.getInt(cursor.getColumnIndex(PlayerPic.Columns.PLAYER_ID)));
    //       pic.setPicUrl(cursor.getString(cursor.getColumnIndex(PlayerPic.Columns.PIC_URL)));
    //       ret.add(pic);
    //     }
    //   }
    //   return ret;
    // }

    @Override
      public void onCreate(SQLiteDatabase db) {
      Log.v(TAG, "public void onCreate(SQLiteDatabase db)");
      db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                 + PlayerPic.Columns._ID + " INTEGER PRIMARY KEY,"
                 + PlayerPic.Columns.PLAYER_ID + " INTEGER,"
                 + PlayerPic.Columns.PIC_URL + " TEXT"
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
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    qb.setTables(TABLE_NAME);

    switch (uriMatcher.match(uri)) {
    case PIC_URL:
      qb.setProjectionMap(projectionMap);
      break;

    case PLAYER_ID:
      qb.setProjectionMap(projectionMap);
      qb.appendWhere(PlayerPic.Columns._ID + "=" + uri.getPathSegments().get(1));
      break;
    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    // If no sort order is specified use the default
    String orderBy;
    if (TextUtils.isEmpty(sortOrder)) {
      orderBy = PlayerPic.Columns.DEFAULT_SORT_ORDER;
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
    case PLAYER_ID:
      return PlayerPic.CONTENT_ITEM_TYPE;

    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
    // Validate the requested uri
    if (uriMatcher.match(uri) != PIC_URL) {
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    ContentValues values;
    if (initialValues != null) {
      values = new ContentValues(initialValues);
    } else {
      values = new ContentValues();
    }

    Long now = Long.valueOf(System.currentTimeMillis());

    if (values.containsKey(PlayerPic.Columns._ID) == false) {
      Log.v(TAG,"values.containsKey(PlayerPic.Columns._ID) == false ");
      values.put(PlayerPic.Columns._ID, 911);
    }

    if (values.containsKey(PlayerPic.Columns.PIC_URL) == false) {
      values.put(PlayerPic.Columns.PIC_URL, "");
    }

    SQLiteDatabase db = openHelper.getWritableDatabase();
    long rowId = db.insert(TABLE_NAME, PlayerPic.Columns.PIC_URL, values);
    if (rowId > 0) {
      Uri tmpuri = ContentUris.withAppendedId(PlayerPic.CONTENT_URI, rowId);
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
    case PIC_URL:
      count = db.delete(TABLE_NAME, where, whereArgs);
      break;

    case PLAYER_ID:
      String paddleId = uri.getPathSegments().get(1);
      count = db.delete(TABLE_NAME, PlayerPic.Columns._ID + "=" + paddleId
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
    case PIC_URL:
      count = db.update(TABLE_NAME, values, where, whereArgs);
      break;

    case PLAYER_ID:
      String paddleId = uri.getPathSegments().get(1);
      count = db.update(TABLE_NAME, values, PlayerPic.Columns._ID + "=" + paddleId
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
    uriMatcher.addURI(PlayerPic.AUTHORITY, "playerpics", PIC_URL);
    uriMatcher.addURI(PlayerPic.AUTHORITY, "playerpics/#", PIC_URL);
    
    projectionMap = new HashMap<String, String>();
    projectionMap.put(PlayerPic.Columns._ID, PlayerPic.Columns._ID);
    projectionMap.put(PlayerPic.Columns.PLAYER_ID, PlayerPic.Columns.PLAYER_ID);
    projectionMap.put(PlayerPic.Columns.PIC_URL, PlayerPic.Columns.PIC_URL);
  }
}
