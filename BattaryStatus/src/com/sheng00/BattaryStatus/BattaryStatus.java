package com.sheng00.BattaryStatus;

import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;

public class BattaryStatus extends PreferenceActivity {
	/** Called when the activity is first created. */

	BatteryManager bManager;
	private PrefsSetting setting;
	private CheckBoxPreference checkboxPreference;
	
	private Preference controllSvcState;
	private boolean serviceRunning;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setting = new PrefsSetting(this);
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
		String svcRunning = setting.prefsGetString(getString(R.string.svc_state));
		serviceRunning = svcRunning.equals("running");
		if(serviceRunning){
			controllSvcState.setTitle("Service is running");
			controllSvcState.setSummary("service is running, touch to stop");
		}else {
			controllSvcState.setTitle("Service is not running");
			controllSvcState.setSummary("service is not running, touch to start");
		}
		controllSvcState.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				switchServiceState();
				return true;
			}
		});
	}
	
	private void switchServiceState(){
		if(!serviceRunning){
			if (startBatteryService()) {
				showTxt("service started");
				if (controllSvcState == null) {
					controllSvcState = findPreference(getString(R.string.controll_service_key));
				}
				controllSvcState.setTitle("Service is running");
				controllSvcState.setSummary("service is running, touch to stop");
				serviceRunning = !serviceRunning;
			}else {
				showTxt("service cannot start");
			}
		}else {
			if(stopBatteryService()){
				showTxt("service stopped");
				if (controllSvcState == null) {
					controllSvcState = findPreference(getString(R.string.controll_service_key));
				}
				controllSvcState.setTitle("Service is not running");
				controllSvcState.setSummary("service is not running, touch to start");
				serviceRunning = !serviceRunning;
			}else {
				showTxt("service cannot stop");
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
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}
}