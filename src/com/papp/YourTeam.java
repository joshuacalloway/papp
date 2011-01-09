package com.papp;
import java.util.*;
import android.app.Activity;
import android.os.Bundle;
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

public class YourTeam extends Activity {

  public void showPlayer(List<Matches> matches, Player player) {
    Log.v(TAG, "showPlayer...");
    Uri uri = ContentUris.withAppendedId(getIntent().getData(), 1);
    Log.v(TAG, "uri is : " + uri.toString());
    Intent i = new Intent(Intent.ACTION_VIEW);
    i.setData(uri);
    i.putExtra("player", player);
    if (matches.size() > 0) {
      i.putExtra("match1", matches.get(0));
    }
    i.setType(Player.CONTENT_ITEM_TYPE);
    startActivity(i);
  }

  boolean isCaptain(Player player) {
    SQLiteDatabase paddleDB = openOrCreateDatabase("paddle.db", Activity.MODE_PRIVATE, null);
    Cursor cursor = paddleDB.rawQuery(PaddleDB.captains_query, new String[] { (new Integer(player.getId())).toString() });
    boolean captain = cursor.getCount() > 0;
    cursor.close();
    paddleDB.close();
    return captain;
  }

  List<Matches> getMatches(Cursor cur){ 
    List<Matches> ret = new ArrayList<Matches>();
    Matches match = null;
    if (cur.moveToFirst()) {
      match = new Matches();
      match.setWinPlayer1(getPlayer(cur.getInt(cur.getColumnIndex(Matches.Columns.WIN_PLAYER1_ID))));
      // int winPlayer2Id = cur.getInt(cur.getColumnIndex(Matches.Columns.WIN_PLAYER2_ID));
      // Log.v(TAG, "winPlayer2Id is " + winPlayer2Id);
      match.setWinPlayer2(getPlayer(5));
      match.setLosePlayer1(getPlayer(cur.getInt(cur.getColumnIndex(Matches.Columns.LOSE_PLAYER1_ID))));
      match.setLosePlayer2(getPlayer(cur.getInt(cur.getColumnIndex(Matches.Columns.LOSE_PLAYER2_ID))));
      match.setScore(cur.getString(cur.getColumnIndex(Matches.Columns.SCORE)));
      match.setId(cur.getInt(cur.getColumnIndex(Matches.Columns._ID)));
      ret.add(match);
    }
    return ret;
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

    
    //player.setName(cur.getString(cur.getColumnIndex(Player.Columns.NAME)));
    //player.setId(cur.getInt(cur.getColumnIndex(Player.Columns._ID)));
    //return player;
  }
  PlayerToSeries getPlayerToSeries(Cursor cur){ 
    PlayerToSeries pToS = null;
    if (cur != null && cur.moveToFirst()) {
      pToS = new PlayerToSeries();
      pToS.setPlayerId(cur.getInt(cur.getColumnIndex(PlayerToSeries.Columns.PLAYER_ID)));
      pToS.setSeriesId(cur.getInt(cur.getColumnIndex(PlayerToSeries.Columns.SERIES_ID)));
      pToS.setId(cur.getInt(cur.getColumnIndex(PlayerToSeries.Columns._ID)));
    }
    return pToS;
  }
	private static int TEXT_SIZE=20;
  
  private static final String TAG = "PaddleApp";
    
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

	/** Called when the activity is first created. */
	@Override
		public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yourteam);
    setTitle(R.string.version);
    Intent intent = getIntent();
    if (intent.getData() == null) {
      intent.setData(Player.CONTENT_URI);
    }
    //    Player me = getMe();
    final Context searchPlayer = this;
    ContentResolver cr = getContentResolver();
    Cursor cursor = managedQuery(Player.CONTENT_URI, Player.COLUMN_NAMES, null, null,
                                 Player.Columns.DEFAULT_SORT_ORDER);

    final TextSwitcher captainView = (TextSwitcher) findViewById(R.id.captain_tfield);
    final YourTeam thisRef = this;
    captainView.setFactory(new ViewSwitcher.ViewFactory() {
        public View makeView() {
          TextView t = new TextView(thisRef);
          t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
          t.setTextSize(TEXT_SIZE);
          t.setWidth(100);
          return t;
        }
      });
    
    
    final TextSwitcher textView = (TextSwitcher) findViewById(R.id.name);
    textView.setFactory(new ViewSwitcher.ViewFactory() {
        public View makeView() {
          TextView t = new TextView(thisRef);
          t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
          t.setTextSize(TEXT_SIZE);
          t.setWidth(500);
          return t;
        }
      });

    cursor.close();
    final Spinner clubView = (Spinner) findViewById(R.id.club);
    Cursor clubCursor = managedQuery(Club.Columns.CONTENT_URI, new String[] { Club.Columns._ID, Club.Columns.NAME }, null, null, Club.Columns.DEFAULT_SORT_ORDER);
     
    SimpleCursorAdapter clubAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, clubCursor, new String[] { Club.Columns.NAME }, new int[] {  android.R.id.text1 });
    clubAdapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
        public CharSequence convertToString(Cursor cursor) {
          Log.v(TAG,"convertToString... : " + cursor.getString(0));
          return cursor.getString(cursor.getColumnIndex(Club.Columns.NAME));
        }
      });
                                         
    clubAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    clubView.setAdapter(clubAdapter);

    final Spinner seriesView = (Spinner) findViewById(R.id.series);
    Cursor seriesCursor = managedQuery(Series.Columns.CONTENT_URI, new String[] { Series.Columns._ID, Series.Columns.SERIES, Series.Columns.DISPLAYNAME }, null, null, Series.Columns.DEFAULT_SORT_ORDER);
     
    final SimpleCursorAdapter seriesAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, seriesCursor, new String[] { Series.Columns.DISPLAYNAME }, new int[] {  android.R.id.text1 });
    seriesAdapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
        public CharSequence convertToString(Cursor cursor) {
          Log.v(TAG,"convertToString... : " + cursor.getString(0));
          return cursor.getString(cursor.getColumnIndex(Series.Columns.DISPLAYNAME));
        }
      });
                                         
    seriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    seriesView.setAdapter(seriesAdapter);
    
    final PlayerPicAdapter playerPicAdapter = new PlayerPicAdapter(searchPlayer, R.layout.player_pic, null, new String[] { PlayerPic.Columns.PIC_URL}, new int[] { R.id.playerpic });
    seriesView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onNothingSelected(AdapterView<?> parent) {}
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          Log.v(TAG,"seriesView onItemSelected... view : " + view + ", position : " + position);
          int seriesId = seriesView.getSelectedItemPosition() + 1;
          int clubId = clubView.getSelectedItemPosition() + 1;
          
          filterGalleryBySeriesNClub(seriesId, clubId, playerPicAdapter);
        }
      });
  
   clubView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onNothingSelected(AdapterView<?> parent) {}
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
 
         int seriesId = seriesView.getSelectedItemPosition() + 1;
          int clubId = clubView.getSelectedItemPosition() + 1;
          
          filterGalleryBySeriesNClub(seriesId, clubId, playerPicAdapter);
        }
      });
 
    Gallery g = (Gallery) findViewById(R.id.picgallery);
    g.setOnItemClickListener(new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
          Log.v(TAG,"Gallery onItemClick... view : " + v + ", position : " + position);
          int playerid = playerPicAdapter.getPlayerId(position);
          Player selPlayer = getPlayer(playerid);
          Cursor c = getContentResolver().query(Matches.CONTENT_URI,
                                         Matches.COLUMNS,
                                         Matches.Columns.WIN_PLAYER1_ID + "=" + playerid + " or " + Matches.Columns.WIN_PLAYER2_ID + "=" + playerid + " or " + Matches.Columns.LOSE_PLAYER1_ID + "=" + playerid + " or " + Matches.Columns.LOSE_PLAYER2_ID + "=" + playerid, null, null);
          List<Matches> matches = getMatches(c);
          Log.v(TAG, "matches.size () : " + matches.size());
          for (Matches next : matches) {
            Log.v(TAG, "next : " + next.toString());
          }
          showPlayer(matches, selPlayer); 
        }
      });


    g.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          Log.v(TAG,"Gallery onItemSelected... view : " + view + ", position : " + position);
          
          int playerid = playerPicAdapter.getPlayerId(position);
          Log.v(TAG, "playerid is " + playerid);
          Player selPlayer = getPlayer(playerid);
          textView.setText(selPlayer.getName());
          if (isCaptain(selPlayer)) {
            captainView.setText("Captain");
          }
          else {
            captainView.setText("");
          }
        }
        
        public void onNothingSelected(AdapterView<?> parent) {}
      });

    Player me = getMe();
    
    seriesView.setSelection(me.getSeries().getSeries()-1, true);
    clubView.setSelection(me.getClub().getId() - 1, true);

    }


  private void filterGalleryBySeriesNClub(int seriesId, int clubId, PlayerPicAdapter playerPicAdapter) {
    Uri.Builder builder = new Uri.Builder();
    builder.authority(PlayerToSeries.AUTHORITY);
    builder.query("series");
    builder.fragment(new Integer(seriesId).toString());
    Uri uri = builder.build();
    Log.v(TAG, "uri.toString(): " + uri.toString());

    SQLiteDatabase paddleDB = openOrCreateDatabase("paddle.db", MODE_PRIVATE, null);
    Cursor cursor = paddleDB.rawQuery(PaddleDB.players_by_series_n_club_query, new String[] { new Integer(seriesId).toString(), new Integer(clubId).toString() });

    String playeridsSQLWhere = " ";
    if (cursor.moveToFirst()) {
      List<Integer> playerids = new ArrayList<Integer>();
      do {
        int playerid = cursor.getInt(cursor.getColumnIndex(Player.Columns._ID));
        playerids.add(playerid);
              
        playeridsSQLWhere += playerid;
        playeridsSQLWhere += ",";
      } while (cursor.moveToNext());
    }
    cursor.close();
    playeridsSQLWhere = playeridsSQLWhere.subSequence(0, playeridsSQLWhere.length()-1).toString();
    Log.v(TAG, "playeridsSQLWhere : " + playeridsSQLWhere);
    Gallery g = (Gallery) findViewById(R.id.picgallery);
          
    cursor = managedQuery(PlayerPic.CONTENT_URI, PlayerPic.COLUMN_NAMES, "player_id in ("+playeridsSQLWhere+")", null,
                          PlayerPic.Columns.DEFAULT_SORT_ORDER);

    playerPicAdapter.resetWithCursor(cursor);
    g.setAdapter(playerPicAdapter);
    Player me = getMe();
    int posid = playerPicAdapter.getPositionIdFromPlayerId(me.getId());
    Log.v(TAG, "posid is " + posid + ", for playerid : " + me.getId());
    g.setSelection(playerPicAdapter.getPositionIdFromPlayerId(me.getId()));

  }
  
}