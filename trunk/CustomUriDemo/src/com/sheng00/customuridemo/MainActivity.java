package com.sheng00.customuridemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	
		Intent intent = getIntent();
		if (intent != null) {

		      String action = intent.getAction();
		      String dataString = intent.getDataString();
		      
		      if(dataString!=null){
		    	  
			      TextView tv = (TextView) findViewById(R.id.tv);
			      tv.append("\n" + dataString);
			      
		      }
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
