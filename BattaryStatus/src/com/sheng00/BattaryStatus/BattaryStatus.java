package com.sheng00.BattaryStatus;


import android.app.Activity;
import android.content.BroadcastReceiver;
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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
//            int scale = -1;
//            int level = -1;
//            int voltage = -1;
//            int temp = -1;
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//                scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//                temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
//                voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
//                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
//                showTxt(
//                			"level is "+level+"/"+scale+", " +
//                			"temp is "+temp+", " +
//                			"voltage is "+voltage +
//                			"plugged is " + plugged);
//                
//            }
//        };
//        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        registerReceiver(batteryReceiver, filter);

        startService(new Intent(BattaryStatus.this, 
                BatteryWatcher.class));
        
    }
    
    private void showTxt(String string){
    	Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }
}