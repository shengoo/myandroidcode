package com.sheng00.masturbator;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;

public class main extends Activity {
    private Vibrator vibrator;
    long[] patten;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(10000);
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    }
    
    @Override
    protected void onDestroy () {
    	if (vibrator != null) {
			vibrator.cancel();
		}
    	super.onDestroy();
    }
}