/**
 * 
 */
package com.sheng00.BatteryStatus;

import com.sheng00.BatteryStatus.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * @author ShengQing on 2011-5-10
 * 
 */
public class StartAtBootServiceReceiver extends BroadcastReceiver {

	private SharedPreferences prefs;
	private boolean debug = false;

	@Override
	public void onReceive(Context context, Intent intent) {
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		boolean checked = prefs.getBoolean(context.getString(R.string.auto_boot_checkbox), true);
		if (debug)
			Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
		if (checked) {
			Intent i = new Intent();
			i.setAction(BatteryWatcher.class.getName());
			context.startService(new Intent(context, BatteryWatcher.class));
		}
	}
}
