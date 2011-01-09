package com.papp;

import java.util.*;
import java.io.*;

import com.papp.R;

import android.util.*;
import android.widget.*;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.content.*;
import android.net.Uri;
import android.database.*;
import android.provider.BaseColumns;
import android.widget.AdapterView.OnItemClickListener;
import android.view.*;
import android.database.sqlite.*;

public class PaddleApp extends Activity
{

  private void installDatabaseIfNeeded() {
    InstallDatabase installDb = new InstallDatabase(this);
    installDb.createDataBase();
  }


  /** Called when the activity is first created. */
  @Override
    public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    installDatabaseIfNeeded();
    setContentView(R.layout.nextmatch);
    setTitle(R.string.version);
    Intent intent = getIntent();
    if (intent.getData() == null) {
      intent.setData(Player.CONTENT_URI);
    }
    //    Player me = getMe();
    final Context paddleAppCtx = this;
    ContentResolver cr = getContentResolver();
  }
}
