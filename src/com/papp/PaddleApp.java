package com.papp;

import java.util.*;
import java.io.*;

import com.papp.R;

import android.util.*;
import android.widget.*;
import android.view.View;
import android.app.TabActivity;
import android.app.Activity;
import android.os.Bundle;
import android.content.*;
import android.net.Uri;
import android.content.res.*;
import android.database.*;
import android.provider.BaseColumns;
import android.widget.AdapterView.OnItemClickListener;
import android.view.*;
import android.database.sqlite.*;

public class PaddleApp extends TabActivity
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
    setTitle(R.string.version);
    setContentView(R.layout.main);

    Resources res = getResources(); // Resource object to get Drawables
    TabHost tabHost = getTabHost();  // The activity TabHost
    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
    Intent intent;  // Reusable Intent for each tab

    // Create an Intent to launch an Activity for the tab (to be reused)
    intent = new Intent().setClass(this, NextMatch.class);

    // Initialize a TabSpec for each tab and add it to the TabHost
    spec = tabHost.newTabSpec("nextmatch").setIndicator("NextMatch",
                      res.getDrawable(R.drawable.ic_tab_nextmatch))
                  .setContent(intent);
    tabHost.addTab(spec);

    intent = new Intent().setClass(this, TeamStandings.class);
    spec = tabHost.newTabSpec("teamstandings").setIndicator("TeamStandings",
                      res.getDrawable(R.drawable.ic_tab_teamstandings))
                  .setContent(intent);
    tabHost.addTab(spec);

    // Do the same for the other tabs
    intent = new Intent().setClass(this, YourTeam.class);
    spec = tabHost.newTabSpec("yourteam").setIndicator("YourTeam",
                      res.getDrawable(R.drawable.ic_tab_yourteam))
                  .setContent(intent);
    tabHost.addTab(spec);


    tabHost.setCurrentTab(1);

    // setContentView(R.layout.nextmatch);
    // Intent intent = getIntent();
    // if (intent.getData() == null) {
    //   intent.setData(Player.CONTENT_URI);
    // }
    // //    Player me = getMe();
    // final Context paddleAppCtx = this;
    // ContentResolver cr = getContentResolver();
  }
}
