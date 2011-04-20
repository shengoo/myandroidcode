package com.s00.Listeners;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;

import com.s00.main;
import com.s00.Controllers.PGBookViewController;
import com.s00.Views.PGHScrollView;
import com.s00.Views.PGScrollLayout;
import com.s00.Views.PGWebViewLayout;

public class BookViewTouchListener implements OnTouchListener {

	private Context mContext;
	private float mLastMotionX;
	private PGBookViewController bookViewController;
	private float mFirstMotionX;
	private Display display;
	// private PrefsManager prefsManager;
	private float mFirstMotionY;
	private int allPageNo;
	private float mLastMotionY;
	static String tag = "BookViewTouchListener";

	public BookViewTouchListener(Context context,
			PGBookViewController controller) {
		mContext = context;
		bookViewController = controller;
		display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
		allPageNo = bookViewController.getAllPageNo();
	}

	@Override
	public boolean onTouch(final View v, MotionEvent ev) {

		int action = ev.getAction();

		float x = ev.getX();
		float y = ev.getY();

		WebView webView = ((PGWebViewLayout)(((PGScrollLayout) (((PGHScrollView) v).getChildAt(0)))
		.getChildAt(0))).getWebView();
		
		switch (action) {
		case MotionEvent.ACTION_DOWN:

			// Remember where the motion event started
			mLastMotionX = x;
			mLastMotionY = y;
			mFirstMotionX = x;
			mFirstMotionY = y;
			Log.i(tag, "ACTION_DOWN,x:" + x + ",y:" + y);
//			webView.onTouchEvent(ev);
			break;
		case MotionEvent.ACTION_MOVE:
			display = ((Activity) mContext).getWindowManager()
					.getDefaultDisplay();
			// Scroll to follow the motion event
			int deltaX = (int) (mLastMotionX - x);
			int deltaY = (int) (mLastMotionY - y);
			mLastMotionX = x;
			mLastMotionY = y;
			Log.i(tag,"deltaX:" + deltaX + ",delteY:" + deltaY);
			if((v.getScrollX() - 150)%display.getWidth() != 0 || 
					Math.abs(deltaY) * 3 < Math.abs(deltaX)){
				v.scrollBy(deltaX, 0);
			}else {
				webView.dispatchTouchEvent(ev);
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			 if (x == mFirstMotionX && y == mFirstMotionY) {
			 ((main) mContext).onTouch(v, ev);
			 return true;
			 }
			int deltaX2 = (int) (mFirstMotionX - x);
			int delteY2 = (int) (mFirstMotionY - y);
			int eventDura = (int) (ev.getEventTime() - ev.getDownTime());
			display = ((Activity) mContext).getWindowManager()
					.getDefaultDisplay();
			if (Math.abs(delteY2)*3 > Math.abs(deltaX2)) {
				webView.dispatchTouchEvent(ev);
//				return true;
			}else {
				
			if (Math.abs(deltaX2) > Math.abs(delteY2)) {
				if (x > mFirstMotionX) {
					// show pre page
					if (bookViewController.getCurrentPageNo() == 0) {
						((HorizontalScrollView) v).smoothScrollTo(150, 0);
						return true;
					}
					// if scroll > width/3, turn page
					if ((x - mFirstMotionX > display.getWidth() / 3 || eventDura < 300)) {
						bookViewController.setCurrentPageNo(bookViewController
								.getCurrentPageNo() - 1);
					}
				} else {
					// show next page
					if (bookViewController.getCurrentPageNo() == allPageNo) {
						((HorizontalScrollView) v).smoothScrollTo(
								bookViewController.getCurrentPageNo()
										* display.getWidth() + 150, 0);
						return true;
					}
					if ((mFirstMotionX - x > display.getWidth() / 3 || eventDura < 300)) {
						bookViewController.setCurrentPageNo(bookViewController
								.getCurrentPageNo() + 1);
					}

				}

			}
			}
			((HorizontalScrollView) v).smoothScrollTo(bookViewController
					.getCurrentPageNo()
					* display.getWidth() + 150, 0);
		}
		return true;

	}
	
	/**
	 * When webview in a scrollview and touch event occurs, 
	 * action down only dispatched in webview
	 * so invoke event in scrollview
	 * @param x : x position of the event
	 * @param y : y position of the event
	 */
	public void invokeActionDown(float x,float y) {

		mLastMotionX = x;
		mLastMotionY = y;
		mFirstMotionX = x;
		mFirstMotionY = y;
	}
	
}
