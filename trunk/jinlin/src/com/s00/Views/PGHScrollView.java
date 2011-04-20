/**
 * 
 */
package com.s00.Views;


import com.s00.Controllers.PGBookViewController;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.widget.HorizontalScrollView;

/**
 * @author Qing
 * 
 */
public class PGHScrollView extends HorizontalScrollView {

	private int scrollX;
	private boolean needScorll = false;
	private Display display;
	private PGBookViewController mBookViewController;
//	private boolean playSoundFlag = false;

	/**
	 * @param context
	 */
	public PGHScrollView(Context context,
			PGBookViewController bookViewController) {
		super(context);
		// TODO Auto-generated constructor stub
		mBookViewController = bookViewController;
		display = ((Activity) context).getWindowManager().getDefaultDisplay();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public PGHScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public PGHScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (needScorll) {
			Log.i("PGHScrollView [onLayout]", "scroll to "
					+ Integer.toString(scrollX));
			Log.i("PGHScrollView [onLayout]", "width:"
					+ Integer.toString(getWidth()) + " PaddingLeft:"
					+ getPaddingLeft() + " PaddingRight:" + getPaddingRight());
			Log.i("PGHScrollView [onLayout]", "my:" + this.getWidth()
					+ "child:" + getChildAt(0).getWidth() + "n:" + scrollX);
			this.scrollTo(scrollX, 0);
			needScorll = false;
		}
	}

	public void setScrollX(int scrollX) {
		this.scrollX = scrollX;
	}

	public void setNeedScorll(boolean needScorll) {
		this.needScorll = needScorll;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		mBookViewController.showPageforRect(getScrollX(), 0, display
				.getWidth(), 0);
		
	}

	public void setBookViewController(PGBookViewController bookViewController) {
		this.mBookViewController = bookViewController;
	}

//	public void setPlaySoundFlag(boolean playSoundFlag) {
//		this.playSoundFlag = playSoundFlag;
//	}

}
