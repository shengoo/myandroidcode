/**
 * 
 */
package com.s00.Views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;



/**
 * @author marvion
 *
 */
public class PGScrollLayout extends ViewGroup {

	private Display display;
	/**
	 * @param context
	 */
	public PGScrollLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		display = ((Activity) context).getWindowManager().getDefaultDisplay();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public PGScrollLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public PGScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.view.ViewGroup#onLayout(boolean, int, int, int, int)
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = this.getChildCount();
		Log.i("PGScrollLayout", "width" + Integer.toString(getWidth()));
		Log.i("PGScrollLayout child count", Integer.toString(count));
		for (int i = 0; i < count; i++) {
			View child = this.getChildAt(i);
			int pageIndex = 0;
			if (child instanceof PGWebView) {
				pageIndex = ((PGWebView) child).getPageIndex();
			}
			if (child instanceof PGWebViewLayout) {
				pageIndex = ((PGWebViewLayout)child).getPageIndex();
			}
			
			int l1 = pageIndex * display.getWidth() + 150;
			int r1 = (pageIndex + 1) * display.getWidth() + 150;
			int b1 = display.getHeight();
			
			child.layout(l1, 0, r1, b1);
			
			Log.i("PGScrollLayout child position", Integer.toString(l1));
		}

	}
	

}
