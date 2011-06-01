package com.papp;

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
import android.util.Log;
import android.widget.Gallery.LayoutParams;


import android.app.*;
import java.util.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.*;
import android.widget.*;
import android.view.View;
import android.view.*;
import android.app.Activity;
import android.os.Bundle;
import android.content.*;
import android.net.Uri;
import android.database.*;
import android.provider.BaseColumns;
import android.widget.AdapterView.OnItemClickListener;
import android.view.*;
import android.database.sqlite.*;

public class YourTeam extends ListActivity {
    private static int TEXT_SIZE=20;
    private static final String TAG = "com.papp.YourTeam";
    static Vector<Player> players = new Vector<Player>();
    class YourTeamAdapter extends BaseAdapter {
	public YourTeamAdapter(Context c) {
	    this.c =c;
	}
	Context c;
	@Override public long getItemId(int position) {
	    return position;
	}
        @Override public int getCount() {
            return players.size();
        }

        @Override public Object getItem(int position) {
            return position;
        }
	
	@Override public View getView (int position, View convertView, ViewGroup parent) {
	    Player player = players.get(position);
	    LinearLayout l = new LinearLayout(c);
	    ImageView i = new ImageView(c);
	    Bitmap resized = resizeImage( PlayerPicAdapter.getImageIdFromPlayerId(player.getId()));
	    BitmapDrawable bmd = new BitmapDrawable(resized);
	    i.setImageDrawable(bmd);
	    i.setScaleType(ScaleType.FIT_XY);
	    l.addView(i);
	    TextView t = new TextView(c);
	    t.setText(player.getName());
	    l.addView(t);
	    return l;
	}
    };

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
    }

    /** Called when the activity is first created. */
    @Override public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setListAdapter(new YourTeamAdapter(this));
	setTitle(R.string.version);
	Intent intent = getIntent();
	if (intent.getData() == null) {
	    intent.setData(Player.CONTENT_URI);
	}
	Player player = new Player();
	// addPlayer(scroll, player);
	player.setName("Roopak Chakavarty");
	player.setId(5);
	addPlayer(player);

	player.setName("Cushman Andrews");
	player.setId(20);

	addPlayer(player);

	player.setName("Mike Gardner");
	player.setId(20);

	addPlayer(player);

	player.setName("Mike Turner");
	player.setId(20);
	addPlayer(player);

	player.setName("Joshua Calloway");
	player.setId(3);
	addPlayer(player);

	// player.setName("Jason McCabe");
	// addPlayer(scroll, player);

	// player.setName("Adam Whitehead");
	// addPlayer(scroll, player);

	// player.setName("Cory Minton");
	// addPlayer(scroll, player);

	// player.setName("Colin Schiewe");
	// addPlayer(scroll, player);

	// player.setName("Vivek Admin");
	// addPlayer(scroll, player);

	// player.setName("Jake Pepper");
	// addPlayer(scroll, player);
      
    }

    private void addPlayer(Player player) {
	players.add(player);
    }
}