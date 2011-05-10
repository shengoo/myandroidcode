package com.sheng00.BattaryStatus;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

public class BattaryStatus extends Activity {
    /** Called when the activity is first created. */
	
	BatteryManager bManager;
	private ComponentName s;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        s = startService(new Intent(BattaryStatus.this, 
                BatteryWatcher.class));
        System.out.println(s == null);
        
    }
    
    private void showTxt(String string){
    	Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }
}