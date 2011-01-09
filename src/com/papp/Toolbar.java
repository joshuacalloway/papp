package com.papp;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Toolbar extends LinearLayout {
    public Toolbar(final Context context) {
	super(context);
    }

    public Toolbar(final Context con, AttributeSet attrs) {
	super(con, attrs);
	setOrientation(HORIZONTAL);
	setBackgroundColor(getResources().getColor(android.R.color.transparent));
	LayoutInflater inflater = (LayoutInflater) 
	    con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	inflater.inflate(R.layout.navigation, this);
	
	TypedArray a = con.obtainStyledAttributes(attrs, R.styleable.Toolbar);
	String option = a.getString(R.styleable.Toolbar_tab_id);
	
	String resourceId = "com.papp:id/"+option;
	int optionId = getResources().getIdentifier(resourceId,null,null);        		
	TextView currentOption = (TextView) findViewById(optionId);
	currentOption.setBackgroundColor(getResources().
					 getColor(android.R.color.white));
	currentOption.setTextColor(getResources().
				   getColor(android.R.color.black));
	currentOption.requestFocus(optionId);
	currentOption.setFocusable(false);
	currentOption.setClickable(false);
	
	TextView nextMatch = (TextView) findViewById(R.id.nextmatch);
	nextMatch.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		    Intent intent = new Intent(con, NextMatch.class);
		    con.startActivity(intent);
		}
	    });
	
	TextView teamStandings = (TextView) findViewById(R.id.teamstandings);
	teamStandings.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		    Intent intent = new Intent(con, TeamStandings.class);
		    con.startActivity(intent);
		}
	    });
	
	TextView yourTeam = (TextView) findViewById(R.id.yourteam);
	yourTeam.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		    Intent intent = new Intent(con, YourTeam.class);
		    con.startActivity(intent);
		}
	    });
    }
}
