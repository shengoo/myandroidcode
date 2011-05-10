/**
 * 
 */
package com.sheng00.BattaryStatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author ShengQing on 2011-5-10
 *
 */
public class StartAtBootServiceReceiver extends BroadcastReceiver  {
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			Intent i = new Intent();
			i.setAction("com.sheng00.BattaryStatus.BatteryWatcher");
			context.startService(i);
		}
	}
}
