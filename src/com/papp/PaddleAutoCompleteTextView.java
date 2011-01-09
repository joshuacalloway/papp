package com.papp;

import java.util.*;
import android.widget.*;
import android.content.*;
import android.util.*;

public class PaddleAutoCompleteTextView extends AutoCompleteTextView
{
    
    public PaddleAutoCompleteTextView(Context context) {
        super(context);
    }
    public PaddleAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaddleAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    interface CompletionListener {
        public void autoCompleted();
    }

    private List <CompletionListener> listeners = new ArrayList<CompletionListener>();
    private static final String TAG = "com.papp.paddleautocompletetextview";

    @Override public void performCompletion() {
        Log.v(TAG, "peformCompletion entry... listeners.size() : " + listeners.size());
        super.performCompletion();
        for (CompletionListener l : listeners) {
            l.autoCompleted();
        }
    }

    public void addCompletionListener(CompletionListener l) {
        listeners.add(l);
    }



}
