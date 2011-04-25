/**
 * 
 */
package com.sheng00.epublib.Listeners;


import com.sheng00.epublib.R;
import com.sheng00.epublib.Controllers.BookViewController;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author ShengQing on 2011-4-23
 * 
 */
public class ControllBarClickListener implements OnClickListener {

	private BookViewController mBookViewController;
	private Context mContext;

	/**
	 * 
	 */
	public ControllBarClickListener(Context contex,
			BookViewController controller) {
		// TODO Auto-generated constructor stub
		mBookViewController = controller;
		mContext = contex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v instanceof Button) {
			Button clickedButton = (Button) v;
			String string = clickedButton.getText().toString();
			if (string.equals(mContext.getString(R.string.pre_button_text))){
				mBookViewController.showPre();
				return;
			}
			if (string.equals(mContext.getString(R.string.chapter_button_text))) {
				mBookViewController.showChapters(true);
				return;
			}
			if (string.equals(mContext.getString(R.string.next_button_text))) {
				mBookViewController.showNext();
				return;
			}
		}

	}

}
