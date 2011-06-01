package com.papp;

import android.app.Activity;
import android.os.Bundle;

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
import android.text.method.LinkMovementMethod;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.text.style.StyleSpan;
import android.text.SpannableString;

public class NextMatch extends Activity {
  private final static String TAG="NextMatch";

  /** Called when the activity is first created. */
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(R.string.version);
    setContentView(R.layout.nextmatch);
    ImageView clubPic = (ImageView)findViewById(R.id.club_imgview);
    Log.v(TAG, "clubPic : " + clubPic);
    Bitmap resized = resizeImage( R.drawable.valleylo_club);
    BitmapDrawable bmd = new BitmapDrawable(resized);
    clubPic.setImageDrawable(bmd);
    clubPic.setScaleType(ScaleType.FIT_XY);

    SpannableString ss = new SpannableString("(312) 285-0344");
    ss.setSpan(new StyleSpan(Typeface.ITALIC), 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    ss.setSpan(new URLSpan("tel:3122850344"), 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    TextView tv = (TextView)findViewById(R.id.captain_phonenumber);
    tv.setText(ss);
    tv.setMovementMethod(LinkMovementMethod.getInstance());


    ss = new SpannableString("(773) 111-9999");
    ss.setSpan(new StyleSpan(Typeface.ITALIC), 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    ss.setSpan(new URLSpan("tel:7731119999"), 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    tv = (TextView)findViewById(R.id.other_captain_phonenumber);
    tv.setText(ss);
    tv.setMovementMethod(LinkMovementMethod.getInstance());

  }
  
  private Bitmap resizeImage(int drawableId){
	
    // load the origial BitMap (500 x 500 px)
    Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), 
						    drawableId);
    
    int width = bitmapOrg.getWidth();
    int height = bitmapOrg.getHeight();
    int newWidth = 100;
    int newHeight = 100;
    
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
}