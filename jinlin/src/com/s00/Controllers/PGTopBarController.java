package com.s00.Controllers;


import com.s00.Views.TopBar;

import android.content.Context;

public class PGTopBarController {
	private Context mContext;
	private TopBar bar;
	
	public PGTopBarController(Context context){
		mContext = context;
		bar = new TopBar(mContext);
	}

	public TopBar getBar() {
		return bar;
	}
	
	
}
