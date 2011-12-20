/**
 * 
 */
package com.sheng00.BatteryStatus;

import com.sheng00.BattaryStatus.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * @author Qing
 * @date 2011-5-11
 * 
 */
public class PrefsSetting {

	private Context mContext;
	private SharedPreferences sprefs;
	
	public PrefsSetting(Context context) {
		mContext = context;
		sprefs = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	
	public boolean getStartOnBoot() {
//		return prefsGetBool(mContext.getString(R.string.start_on_boot));
		//default value is true
		boolean boo = true;
		try {
			boo = sprefs.getBoolean(mContext.getString(R.string.start_on_boot), true);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return boo;
	}
	
	public void setStartOnBoot(boolean val) {
		prefsSetBool(mContext.getString(R.string.start_on_boot), val);
	}
	
	
	
	
	
	
	public void prefsSetBool(String key, boolean val) {
		Editor e = sprefs.edit();
		e.putBoolean(key, val);
		e.commit();
	}
	
	public boolean prefsGetBool(String key) {
		boolean boo = false;
		try {
			boo = sprefs.getBoolean(key, false);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return boo;
	}
	
	public void prefsSetFloat(String key,float val) {
		Editor e = sprefs.edit();
		e.putFloat(key, val);
		e.commit();
	}
	
	public float prefsGetFloat(String key) {
		float result = 0;
		try {
			result = sprefs.getFloat(key, 0);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	public void prefsSetInt(String key, int val) {
		Editor e = sprefs.edit();
		e.putInt(key, val);
		e.commit();
	}

	public int prefsGetInt(String key) {
		int no = 0;
		try {
			no = sprefs.getInt(key, 0);
		} catch (ClassCastException e) {
		}
		return no;
	}

	public void prefsSetString(String key, String val) {
		Editor e = sprefs.edit();
		e.putString(key, val);
		e.commit();
	}

	public String prefsGetString(String key) {
		String result = "";
		try {
			result = sprefs.getString(key, "");
		} catch (ClassCastException e) {
		}
		return result;
	}
}
