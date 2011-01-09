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
import android.database.sqlite.*;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import java.io.*;

public class InstallDatabase extends SQLiteOpenHelper {
  private static String DB_PATH = "/data/data/com.papp/databases/";
  private static String DB_NAME = "paddle.db";
  private SQLiteDatabase myDataBase;
  private final Context ctx;
  private boolean overwrite=true;
  
  /**
   * Constructor Takes and keeps a reference of the passed context in order to
   * access to the application assets and resources.
   * 
   * @param context
   */
  public InstallDatabase(Context context) {
    super(context, DB_NAME, null, 1);
    this.ctx = context;
  }

  /**
   * Creates a empty database on the system and rewrites it with your own
   * database.
   * */
  public void createDataBase() {
    boolean dbExist = checkDataBase();
    if (overwrite || !dbExist) {
      // By calling this method and empty database will be created into the
      // default system path
      // of your application so we are gonna be able to overwrite that database
      // with our database.
      SQLiteDatabase db = this.getReadableDatabase();

      try {

        copyDataBase();

      } catch (IOException e) {
        throw new Error("Error copying database");
      }
      db.close();
    }

  }

  /**
   * Check if the database already exist to avoid re-copying the file each time
   * you open the application.
   * 
   * @return true if it exists, false if it doesn't
   */
  private boolean checkDataBase() {
    SQLiteDatabase checkDB = null;

    try {
      String myPath = DB_PATH + DB_NAME;
      checkDB = SQLiteDatabase.openDatabase(myPath, null,
          SQLiteDatabase.OPEN_READONLY);

    } catch (SQLiteException e) {

      // database does't exist yet.

    }

    if (checkDB != null) {

      checkDB.close();

    }

    return checkDB != null ? true : false;
  }

  /**
   * Copies your database from your local assets-folder to the just created
   * empty database in the system folder, from where it can be accessed and
   * handled. This is done by transfering bytestream.
   * */
  private void copyDataBase() throws IOException {

    // Open your local db as the input stream
    InputStream myInput = ctx.getAssets().open(DB_NAME);

    // Path to the just created empty db
    String outFileName = DB_PATH + DB_NAME;

    // Open the empty db as the output stream
    OutputStream myOutput = new FileOutputStream(outFileName);

    // transfer bytes from the inputfile to the outputfile
    byte[] buffer = new byte[1024];
    int length;
    while ((length = myInput.read(buffer)) > 0) {
      myOutput.write(buffer, 0, length);
    }

    // Close the streams
    myOutput.flush();
    myOutput.close();
    myInput.close();

  }

  public void openDataBase() throws SQLException {

    // Open the database
    String myPath = DB_PATH + DB_NAME;
    myDataBase = SQLiteDatabase.openDatabase(myPath, null,
        SQLiteDatabase.OPEN_READONLY);

  }

  @Override
  public synchronized void close() {

    if (myDataBase != null)
      myDataBase.close();

    super.close();

  }

  @Override
  public void onCreate(SQLiteDatabase db) {

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

  // Add your public helper methods to access and get content from the database.
  // You could return cursors by doing "return myDataBase.query(....)" so it'd
  // be easy
  // to you to create adapters for your views.

}