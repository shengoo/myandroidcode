/**
 * 
 */
package com.s00.Listeners;

import android.app.Activity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;

import com.s00.main;
import com.s00.Views.PGHScrollView;

/**
 * @author Qing
 * 
 */
public class WebViewOnTouchListener implements OnTouchListener {

	static String tag = "WebViewOnTouchListener";
	private float mLastMotionX;
	private float mLastMotionY;
	private float mFirstMotionX;
	private float mFirstMotionY;
	private PGHScrollView hScrollView;
	private Display display;

	/**
	 * 
	 */
	public WebViewOnTouchListener() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		float x = event.getX();
		float y = event.getY();
		hScrollView = ((main) main.getCONTEXT()).getmBookViewController()
				.getHscroll();
		switch (action) {
		case MotionEvent.ACTION_DOWN:

			// Remember where the motion event started
			mLastMotionX = x;
			mLastMotionY = y;
			mFirstMotionX = x;
			mFirstMotionY = y;
			((main) main.getCONTEXT()).getmBookViewController()
					.getTouchListener().invokeActionDown(x, y);
			Log.i(tag, "ACTION_DOWN,x:" + x + ",y:" + y);
			break;
		case MotionEvent.ACTION_MOVE:
			display = ((Activity) main.getCONTEXT()).getWindowManager()
					.getDefaultDisplay();
			// Scroll to follow the motion event
			int deltaX = (int) (mLastMotionX - x);
			int deltaY = (int) (mLastMotionY - y);
			mLastMotionX = x;
			mLastMotionY = y;
			Log.i(tag, "deltaX:" + deltaX + ",delteY:" + deltaY);
			if ((hScrollView.getScrollX() - 150) % display.getWidth() != 0
					|| Math.abs(deltaY) * 3 < Math.abs(deltaX))
				return true;
			v.scrollBy(0, deltaY);
		case MotionEvent.ACTION_UP:
			if (x == mFirstMotionX && y == mFirstMotionY) {
				((main) main.getCONTEXT()).onTouch(v, event);
				return true;
			}
			int deltaY2 = (int) (mFirstMotionY - y);
			int velocityY = (int) (1000 * deltaY2 / (event.getEventTime() - event
					.getDownTime()));
			((WebView) v).flingScroll(0, velocityY);
			return true;
		}
		return false;
	}

}
