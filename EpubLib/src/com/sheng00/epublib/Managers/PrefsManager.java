package com.sheng00.epublib.Managers;



import com.sheng00.epublib.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PrefsManager {

	
	private Context mContext;
	private SharedPreferences sprefs;
	
	public boolean hashistory() {
		boolean result = false;
		result = prefsGetBool(mContext.getString(R.string.history_flag));
		return result;
	}
	
	public int getLastnum() {
		return prefsGetInt(mContext.getString(R.string.last_page_num));
	}
	
	public float getLastPer() {
		return prefsGetFloat(mContext.getString(R.string.last_page_pers));
	}
	
	public void savehistory(int num,float per) {
		prefsSetBool(mContext.getString(R.string.history_flag), true);
		prefsSetInt(mContext.getString(R.string.last_page_num), num);
		prefsSetFloat(mContext.getString(R.string.last_page_pers), per);
	}
	
	public PrefsManager(Context context) {
		mContext = context;
		sprefs = PreferenceManager.getDefaultSharedPreferences(mContext);
	}
	
	public void SetFontSize(int val) {
		prefsSetInt(mContext.getString(R.string.font_size), val);
	}
	
	public int GetFontSize() {
		int defaultSize = 28;
		if(prefsGetInt(mContext.getString(R.string.font_size)) != 0)
			defaultSize = prefsGetInt(mContext.getString(R.string.font_size));
		return defaultSize;
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

	public int readAllPageNo() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
