
package com.papp;

import android.widget.*;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class ShowTeam extends ListActivity
{
  private static final String TAG = "ShowTeam";  // boilerplate: debug logging

  /** Called when the activity is first created. */
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
	// layout
    setContentView( R.layout.showteam );
    // team
	mTeam = (Team) getIntent().getSerializableExtra("team");
	TextView teamLabel = (TextView) this.findViewById(R.id.showteam_label);
	teamLabel.setText( "Team id: " + mTeam.id );
	// players
	mAdpt = new ArrayAdapter<String>( this, R.layout.showteamitem, R.id.showteamitem_player_name	);
	//SQLiteDatabase db = openOrCreateDatabase("paddle.db", MODE_PRIVATE, null);
	//try {
	//	String strQuery = 
	//		"select p.name as p_name, c.name as c_name, s.displayname as s_name " +
	//		"from team t " +
	//		     "left join player_to_team p2t on t._id = p2t.team_id " +
	//		     "left join club c on t.club_id = c._id " +
	//		     "left join series s on t.series_id = s._id " +
	//		"where t._id = " + mTeam.id;
	//	
	//	Cursor cursor = db.rawQuery(strQuery, null);
	//	cursor.moveToFirst();
	//	do {
	//		mAdpt.add( cursor.getString(0) );
	//	} while ( cursor.moveToNext() );
	//} finally {
	//	db.close();
	//}

	mAdpt.add( "Joshua Calloway" );
	mAdpt.add( "David Gorodess" );
	setListAdapter( mAdpt );
  }
  // representation
  private Team mTeam;
  private ArrayAdapter<String> mAdpt;
}
