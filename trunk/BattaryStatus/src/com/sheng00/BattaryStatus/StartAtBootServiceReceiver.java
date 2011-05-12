/**
 * 
 */
package com.sheng00.BattaryStatus;

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
public class StartAtBootServiceReceiver extends BroadcastReceiver  {
	

	private SharedPreferences prefs;

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		boolean checked = prefs.getBoolean(context.getString(R.string.auto_boot_checkbox), true);
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			if (checked) {
				Intent i = new Intent();
				i.setAction("com.sheng00.BattaryStatus.BatteryWatcher");
				context.startService(i);
			}
		}
	}
}
