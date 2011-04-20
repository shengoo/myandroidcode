package com.s00.Managers;

import com.s00.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PrefsManager {

	
	private Context mContext;
	private SharedPreferences sprefs;
	
	public PrefsManager(Context context) {
		mContext = context;
		sprefs = PreferenceManager.getDefaultSharedPreferences(mContext);
	}
	
	public void SetFontSize(int val) {
		prefsSetInt(mContext.getString(R.string.font_size), val);
	}
	
	public int GetFontSize() {
		int defaultSize = 26;
		if(prefsGetInt(mContext.getString(R.string.font_size)) != 0)
			defaultSize = prefsGetInt(mContext.getString(R.string.font_size));
		return defaultSize;
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

	public int readAllPageNo() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
