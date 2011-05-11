package com.sheng00.BattaryStatus;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class BattaryStatus extends PreferenceActivity {
	/** Called when the activity is first created. */

	BatteryManager bManager;
	private ComponentName s;
	private PrefsSetting setting;
	private boolean startOnBoot;
	private CheckBox startOnBootBox;
	private CheckBoxPreference checkboxPreference;
	private SharedPreferences prefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		// setting = new PrefsSetting(this);
		//
		// startOnBoot = setting.getStartOnBoot();
		// startOnBootBox = (CheckBox) findViewById(R.id.checkBox1);
		// startOnBootBox.setChecked(startOnBoot);
		
		addPreferencesFromResource(R.xml.preferences);
		checkboxPreference = (CheckBoxPreference) findPreference(getString(R.string.auto_boot_checkbox));
		if (checkboxPreference.isChecked()) {
			checkboxPreference
					.setSummary(R.string.start_onboot_enable_summary);
		} else {
			checkboxPreference
					.setSummary(R.string.start_onboot_disable_summary);
		}
		checkboxPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					public boolean onPreferenceClick(Preference preference) {
						if (((CheckBoxPreference) preference).isChecked()) {
							preference
									.setSummary(R.string.start_onboot_enable_summary);
						} else {
							preference
									.setSummary(R.string.start_onboot_disable_summary);
						}
						return true;
					}

				});
		startBatteryService();
	}

	private void startBatteryService() {
		s = startService(new Intent(BattaryStatus.this, BatteryWatcher.class));
		System.out.println(s == null);
	}

	private void showTxt(String string) {
		Toast.makeText(this, string, Toast.LENGTH_LONG).show();
	}
}