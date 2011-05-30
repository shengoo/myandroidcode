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
	
	private Preference controllSvcState;
	private boolean serviceRunning;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		// setting = new PrefsSetting(this);
		//
		// startOnBoot = setting.getStartOnBoot();
		// startOnBootBox = (CheckBox) findViewById(R.id.checkBox1);
		// startOnBootBox.setChecked(startOnBoot);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
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
		controllSvcState = findPreference(getString(R.string.controll_service_key));
		String svcRunning = prefs.getString(getString(R.string.svc_state), "not");
		serviceRunning = svcRunning.equals("running");
		if(serviceRunning){
			controllSvcState.setTitle("service is running, touch to stop");
		}else {
			controllSvcState.setTitle("service is not running, touch to start");
		}
		controllSvcState.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				switchServiceState();
				return true;
			}
		});
//		startBatteryService();
	}
	
	private void switchServiceState(){
		if(!serviceRunning){
			if (startBatteryService()) {
				Toast.makeText(this, "service started", Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(this, "service cannot start", Toast.LENGTH_LONG).show();
			}
		}else {
			if(stopBatteryService()){
				Toast.makeText(this, "service stopped", Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(this, "service cannot stop", Toast.LENGTH_LONG).show();
			}
		}
	}

	private boolean startBatteryService() {
		return startService(new Intent(BattaryStatus.this, BatteryWatcher.class))!=null;
//		System.out.println(s == null);
	}
	
	private boolean stopBatteryService() {
		return stopService(new Intent(BattaryStatus.this, BatteryWatcher.class));
	}

	private void showTxt(String string) {
		Toast.makeText(this, string, Toast.LENGTH_LONG).show();
	}
}