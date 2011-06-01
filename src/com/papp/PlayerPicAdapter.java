package com.papp;

import java.util.*;

import com.papp.R;

import android.util.*;
import android.content.res.*;
import android.widget.*;
import android.content.*;
import android.database.*;
import android.view.*;
import android.widget.AdapterView.OnItemClickListener;

public class PlayerPicAdapter extends SimpleCursorAdapter {
  int galleryItemBackground;
  private Context ctx;
  private static Map<Integer, Integer> playeridsToImageIds = new HashMap<Integer,Integer>();
  private static final String TAG = "com.papp.PlayerPicAdapter";
    
  private static void initMap() {
    playeridsToImageIds.put(0, R.drawable.viking_founder);
    playeridsToImageIds.put(1, R.drawable.adam_whitehead);
    playeridsToImageIds.put(2, R.drawable.cory_minton);
    playeridsToImageIds.put(3, R.drawable.joshua_calloway);
    playeridsToImageIds.put(4, R.drawable.mike_insko);
    playeridsToImageIds.put(5, R.drawable.roopak_chakravarty);
    playeridsToImageIds.put(6, R.drawable.kevin_skinner);
    playeridsToImageIds.put(7, R.drawable.nam_paik);
    playeridsToImageIds.put(8, R.drawable.michael_deweirdt);
    playeridsToImageIds.put(9, R.drawable.david_gorodess);
    playeridsToImageIds.put(10, R.drawable.viking_founder);
    playeridsToImageIds.put(11, R.drawable.vivek_amin);
    playeridsToImageIds.put(12, R.drawable.vasiliy_guryanov);
    playeridsToImageIds.put(13, R.drawable.svenerik_nilsen);
    playeridsToImageIds.put(14, R.drawable.viking_founder);
    playeridsToImageIds.put(15, R.drawable.viking_founder);
    playeridsToImageIds.put(16, R.drawable.viking_founder);
    playeridsToImageIds.put(17, R.drawable.viking_founder);
    playeridsToImageIds.put(18, R.drawable.paul_bossi);
    playeridsToImageIds.put(19, R.drawable.grant_reiss);


    playeridsToImageIds.put(20, R.drawable.viking_founder);
    playeridsToImageIds.put(21, R.drawable.viking_founder);
    playeridsToImageIds.put(22, R.drawable.viking_founder);
    playeridsToImageIds.put(23, R.drawable.viking_founder);
    playeridsToImageIds.put(24, R.drawable.viking_founder);
    playeridsToImageIds.put(25, R.drawable.viking_founder);
    playeridsToImageIds.put(26, R.drawable.viking_founder);
    playeridsToImageIds.put(27, R.drawable.viking_founder);
    playeridsToImageIds.put(28, R.drawable.viking_founder);
    playeridsToImageIds.put(29, R.drawable.viking_founder);
    playeridsToImageIds.put(30, R.drawable.viking_founder);
    playeridsToImageIds.put(31, R.drawable.viking_founder);
    playeridsToImageIds.put(32, R.drawable.viking_founder);

  }

    static {
	initMap();
    }
  public static int getImageIdFromPlayerId(int playerid) {
    return playeridsToImageIds.get(playerid);
  }
  // given a position, return the player id
  public int getPlayerId(int position) {
    return playerIds.get(position);
  }

  public int getPositionIdFromPlayerId(int playerid) {
    int imageId = getImageIdFromPlayerId(playerid);
    Log.v(TAG, "imageId is : " + imageId);
    int posid = 0;
    for (Integer n : imageIds) {
      Log.v(TAG, "n is : " + n.intValue());
      if (n.intValue() == imageId) {
        return posid;
      }
      ++posid;
    }
    return -1;
    //    return imageIds.indexOf(new Integer(imageId));
  }

  private List<Integer> imageIds = new ArrayList<Integer>();
  private List<Integer> playerIds = new ArrayList<Integer>();

  public void resetWithCursor(Cursor cursor) {
    imageIds = new ArrayList<Integer>();
    playerIds = new ArrayList<Integer>();

    if (null == cursor) return;
    if (cursor.moveToFirst()) {
      do {
        int playerid = cursor.getInt(cursor.getColumnIndex(PlayerPic.Columns.PLAYER_ID));
        imageIds.add(playeridsToImageIds.get(playerid));
        playerIds.add(playerid);
      } while (cursor.moveToNext());
    }
    cursor.close();

  }
  public PlayerPicAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
    super(context, layout, c, from, to);
    this.ctx = context;
    
    resetWithCursor(c);

    TypedArray a = ctx.obtainStyledAttributes(R.styleable.PaddleApp);
    galleryItemBackground = a.getResourceId(R.styleable.PaddleApp_android_galleryItemBackground, 0);
    a.recycle();
  }

  public int getCount() {
    return imageIds.size();
  }

  public Object getItem(int position) {
    return position;
  }

  public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    ImageView i = new ImageView(ctx);
    if (position < getCount()) { 
      Log.v(TAG, "imageIds.size() : " + imageIds.size());
      Log.v(TAG, "position : " + position);
      i.setImageResource(imageIds.get(position));
    }
    i.setLayoutParams(new Gallery.LayoutParams(GALLERY_WIDTH,GALLERY_HEIGHT));
    i.setScaleType(ImageView.ScaleType.FIT_XY);
    i.setBackgroundResource(galleryItemBackground);
    return i;
  }
	public final static int GALLERY_HEIGHT=100;
	public final static int GALLERY_WIDTH=100;
}
