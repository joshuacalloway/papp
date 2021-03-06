package com.papp;

import com.papp.R;

import android.util.*;
import android.widget.*;
import android.widget.ImageView.ScaleType;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.content.*;
import android.net.*;

public class ShowPlayer extends Activity
{
  private Player mPlayer; // plb added 121810
  String getTeamName( Player player ) {
    String strClub = player.getClub().getName();
    String strSeries = player.getSeries().getDisplayName();
    return strClub + " series " + strSeries;
  }

  /** Called when the activity is first created. */
  @Override
    public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    
    setContentView(R.layout.showplayer);
    
    Intent intent = getIntent();
    if (intent.getData() == null) {
      intent.setData(League.CONTENT_URI);
    }
    Bundle extras = getIntent().getExtras();
    Matches match1= (Matches)getIntent().getSerializableExtra("match1");
    Player player= (Player)getIntent().getSerializableExtra("player");
    mPlayer = player;  // plb added 121810
    setTitle(R.string.version);

    TextView name = (TextView) findViewById(R.id.name2);
    name.setText(player.getName());
    
    // TextView team = (TextView) findViewById(R.id.team);
    // String teamName = getTeamName(player);
    // team.setText(teamName);
    int playerid = player.getId();
    
    Bitmap resized = resizeImage( PlayerPicAdapter.getImageIdFromPlayerId(playerid));
   
    
    BitmapDrawable bmd = new BitmapDrawable(resized);
    
    ImageView playerPic = (ImageView)findViewById(R.id.player1);
    playerPic.setImageDrawable(bmd);

    playerPic = (ImageView)findViewById(R.id.player2);
    playerPic.setImageDrawable(bmd);


    playerPic = (ImageView)findViewById(R.id.player3);
    playerPic.setImageDrawable(bmd);

    
    playerPic = (ImageView)findViewById(R.id.player4);
    playerPic.setImageDrawable(bmd);
    
        

    // if (null != match1) {
    //   Log.v(TAG, "received match1..." + match1.toString());

    //   TextView match1Score = (TextView) findViewById(R.id.match1_score);
    //   match1Score.setText(match1.getScore());
    //   TextView tv = (TextView) findViewById(R.id.match1_win_player1);
    //   tv.setText(match1.getWinPlayer1().getName());
    //   tv = (TextView) findViewById(R.id.match1_win_player2);
    //   tv.setText(match1.getWinPlayer2().getName());

    //   tv = (TextView) findViewById(R.id.match1_lose_player1);
    //   tv.setText(match1.getLosePlayer1().getName());
    //   tv = (TextView) findViewById(R.id.match1_lose_player2);
    //   tv.setText(match1.getLosePlayer2().getName());

    //   tv = (TextView) findViewById(R.id.match1_score);
    //   tv.setText(match1.getScore());

    // }
    
    // TextView teamCtl = (TextView)findViewById(R.id.team);
    // teamCtl.setOnClickListener(new View.OnClickListener() {
    //     public void onClick(View v) {
    //       Log.v(TAG, "teamCtl.onClick...");
    // 		  Team t = new Team();
    // 		  t.setId( 1 );
    //       Intent intent = new Intent( ShowPlayer.this, ShowTeam.class );
    //       intent.putExtra( "team", t );
    //       ShowPlayer.this.startActivity(intent);
    //      }
    //   });
  }
  private static final String TAG = "com.papp.displaymatches";



  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    // Handle all of the possible menu actions.
    switch (item.getItemId()) {
    case SEARCH_ID:
      //Intent intent = getIntent();
      Intent intent = new Intent(Intent.ACTION_INSERT);
      intent.setData(Player.CONTENT_URI);
      intent.setType(Player.CONTENT_ITEM_TYPE);
      startActivity( intent );
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    menu.add(0, SEARCH_ID, 0, "Search")
      .setShortcut('0', 's');
    return true;
    //            .setIcon(android.R.drawable.ic_menu_revert);

    // Build the menus that are shown when inserting.
  }
  private static final int SEARCH_ID = Menu.FIRST; 


private Bitmap resizeImage(int drawableId){
      
      // load the origial BitMap (500 x 500 px)
      Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), 
             drawableId);
      
      int width = bitmapOrg.getWidth();
      int height = bitmapOrg.getHeight();
      int newWidth = 50;
      int newHeight = 50;
      
      // calculate the scale - in this case = 0.4f
      float scaleWidth = ((float) newWidth) / width;
      float scaleHeight = ((float) newHeight) / height;
      
      // createa matrix for the manipulation
      Matrix matrix = new Matrix();
      // resize the bit map
      matrix.postScale(scaleWidth, scaleHeight);
      // rotate the Bitmap
      matrix.postRotate(0);

      // recreate the new Bitmap
      Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, 
                        width, height, matrix, true); 
      return resizedBitmap;
      // make a Drawable from Bitmap to allow to set the BitMap 
      // to the ImageView, ImageButton or what ever
      
      
     
  }
}
