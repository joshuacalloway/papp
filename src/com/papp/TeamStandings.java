package com.papp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class TeamStandings extends Activity {
  public Player mPlayer;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	// set player
	mPlayer = this.getMe();
	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teamstandings);
				setTitle(R.string.version);
		
	TextView lbl = (TextView) this.findViewById(R.id.teamstandings_label);
	TextView lbl2 = (TextView) this.findViewById(R.id.teamstandings_label2);
	lbl.setText( mPlayer.getName() );
	lbl2.setText( mPlayer.series.displayName);
    }
  Player getPlayer(int id){
    SQLiteDatabase paddleDB = openOrCreateDatabase("paddle.db", MODE_PRIVATE, null);
    Cursor cursor = paddleDB.rawQuery(PaddleDB.player_query, new String[] {new Integer(id).toString() });
    Player player = new Player();
    
    cursor.moveToFirst();
    player.setId(cursor.getInt(cursor.getColumnIndex(Player.Columns._ID)));
    player.setName(cursor.getString(cursor.getColumnIndex(Player.Columns.NAME)));
    cursor.close();
    // select p.* from player left join player_to_club p2c left join club on club.id 
    cursor = paddleDB.rawQuery(PaddleDB.series_by_player_id_query, new String[] { new Integer(player.getId()).toString() } );
    cursor.moveToFirst();
    Series series = new Series();
    series.setId(cursor.getInt(cursor.getColumnIndex(Series.Columns._ID)));
    series.setDisplayName(cursor.getString(cursor.getColumnIndex(Series.Columns.DISPLAYNAME)));
    series.setSeries(cursor.getInt(cursor.getColumnIndex(Series.Columns.SERIES)));
    player.setSeries(series);
    cursor.close();

    cursor = paddleDB.rawQuery(PaddleDB.club_by_player_id_query, new String[] { new Integer(player.getId()).toString() } );
    cursor.moveToFirst();
    Club club = new Club();
    club.setId(cursor.getInt(cursor.getColumnIndex(Club.Columns._ID)));
    club.setName(cursor.getString(cursor.getColumnIndex(Club.Columns.NAME)));
    club.setAddress(cursor.getString(cursor.getColumnIndex(Club.Columns.ADDRESS)));
    player.setClub(club);
    cursor.close();
    paddleDB.close();
    return player;

  }
  private Player getMe() {
    SQLiteDatabase paddleDB = openOrCreateDatabase("paddle.db", MODE_PRIVATE, null);
    Cursor cursor = paddleDB.rawQuery(PaddleDB.me_query, new String[] {});
    cursor.moveToFirst();
    int playerid = cursor.getInt(cursor.getColumnIndex(Me.Columns.PLAYER_ID));
    Player me = getPlayer(playerid);
    cursor.close();
    paddleDB.close();
    return me;
  }
}
