package com.sheng00.BattaryStatus;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
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
	private boolean debug = false;

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
//		serviceRunning = svcRunning.equals("running");
		serviceRunning = isMyServiceRunning();
		if(serviceRunning){
			controllSvcState.setTitle(R.string.service_running);
			controllSvcState.setSummary(R.string.touch_stop_service);
		}else {
			controllSvcState.setTitle(R.string.service_not_running);
			controllSvcState.setSummary(R.string.touch_start_service);
		}
		controllSvcState.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				switchServiceState();
				return true;
			}
		});
	}
	
	private boolean isMyServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	    	System.out.println(service.service.getClassName());
	        if ("com.sheng00.BattaryStatus.BatteryWatcher".equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}

	
	private void switchServiceState(){
		if(!serviceRunning){
			if (startBatteryService()) {
				showTxt("service started");
				if (controllSvcState == null) {
					controllSvcState = findPreference(getString(R.string.controll_service_key));
				}
				controllSvcState.setTitle(R.string.service_running);
				controllSvcState.setSummary(R.string.touch_stop_service);
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
				controllSvcState.setTitle(R.string.service_not_running);
				controllSvcState.setSummary(R.string.touch_start_service);
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
		if(!debug)
			return;
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}
}