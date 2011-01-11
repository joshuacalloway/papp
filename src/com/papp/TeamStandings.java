package com.papp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.*;

public class TeamStandings extends Activity {
  class TeamRecord {
    String club;
    int wins;
    int losses;
    int pts;
    int ptsa;
  };
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.teamstandings);
    setTitle(R.string.version);
   
    TableLayout layout = (TableLayout)findViewById(R.id.teamstandings_table);
    TeamRecord record = new TeamRecord();
    record.club="Lake Bluff";
    record.wins = 12;
    record.losses = 1;
    record.pts = 29;
    record.ptsa = 8;
    appendRow(layout, record);
    appendRow(layout, record);
    appendRow(layout, record);
    appendRow(layout, record);
    appendRow(layout, record);
    appendRow(layout, record);
    appendRow(layout, record);
    appendRow(layout, record);
    appendRow(layout, record);

    record.club = "Lake Forest";
    appendRow(layout, record);

//    TableRow row = (TableRow)findViewById(R.id.teamstandings_item);
 //   layout.addView(row, new TableLayout.LayoutParams());
  }

  private void appendRow(TableLayout table, TeamRecord record) {
    TableRow row = new TableRow(this);
    TextView club = new TextView(this);
    club.setText(record.club);
    club.setPadding(3, 3, 3, 3);
//    wins.setGravity(Gravity.RIGHT | Gravity.TOP);
    row.addView(club, new TableRow.LayoutParams(0));

    TextView wins = new TextView(this);
    wins.setText(record.wins + " W");
    wins.setPadding(3, 3, 3, 3);
    row.addView(wins, new TableRow.LayoutParams());

    TextView losses = new TextView(this);
    losses.setText(record.losses + " L");
    losses.setPadding(3, 3, 3, 3);
    row.addView(losses, new TableRow.LayoutParams());

    TextView pts = new TextView(this);
    pts.setText(new Integer(record.pts).toString());
    pts.setPadding(3, 3, 3, 3);
    row.addView(pts, new TableRow.LayoutParams());

    TextView ptsagainst = new TextView(this);
    ptsagainst.setText(new Integer(record.ptsa).toString());
    ptsagainst.setPadding(3, 3, 3, 3);
    row.addView(ptsagainst, new TableRow.LayoutParams());

    table.addView(row, new TableLayout.LayoutParams());
  }
}
