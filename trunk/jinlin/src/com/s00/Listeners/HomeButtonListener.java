package com.s00.Listeners;

import com.s00.Controllers.PGBookViewController;

import android.view.View;
import android.view.View.OnClickListener;


public class HomeButtonListener implements OnClickListener {

	private PGBookViewController mBookViewController;
	public HomeButtonListener(PGBookViewController bookViewController) {
		mBookViewController = bookViewController;
	}

	@Override
	public void onClick(View v) {
//		mBookViewController.showFrontPage(true);
		mBookViewController.showChapters(true);
	}

}
