/**
 * 
 */
package com.s00.Views;

import com.s00.Listeners.WebViewOnTouchListener;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


/**
 * @author Qing
 * 
 */
public class PGWebViewLayout extends ViewGroup{

	private int pageIndex;
	private Context mContext;
	PGWebViewLayout container;
	private Display display;
	static String tag = "PGWebViewLayout";
	private ProgressBar progressBar;
	private WebView webView;
	private WebViewOnTouchListener webViewOnTouchListener;
	private PGHScrollView hScrollView;

	/**
	 * @param context
	 */
	public PGWebViewLayout(Context context,String path) {
		super(context);
		// TODO Auto-generated constructor stub
		setBackgroundColor(Color.WHITE);
		container = this;
		mContext = context;
		display = ((Activity) context).getWindowManager().getDefaultDisplay();
		webView = new WebView(context);
//		webView.setClickable(false);
		webViewOnTouchListener = new WebViewOnTouchListener();
		webView.setOnTouchListener(webViewOnTouchListener);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setInitialScale(100);
		webView.setWebViewClient(new MyWebChromeClient());
		webView.loadUrl("file://" + path);
		webView.setPadding(0, 40, 0, 0);
		addView(webView,display.getWidth(),display.getHeight());
		webView.setVisibility(View.INVISIBLE);
		progressBar = new ProgressBar(mContext,null,R.attr.progressBarStyleLarge);
		addView(progressBar);
	}
	

	/**
	 * @param context
	 * @param attrs
	 */
	public PGWebViewLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}


	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	class MyWebChromeClient extends WebViewClient {
		@Override
		public void  onPageFinished  (WebView view, String url){
			super.onPageFinished(view, url);
			webView.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					container.removeView(progressBar);
					webView.setVisibility(View.VISIBLE);
				}
			},10);
		}
    }
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.i(tag + " onLayout" ,"");
		int count = this.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = this.getChildAt(i);
			if (child instanceof ProgressBar) {
				child.layout(display.getWidth()/2 -100, display.getHeight()/2 -100, display.getWidth()/2 +100, display.getHeight()/2 + 100);
			}else {
				child.layout(0, 40, display.getWidth(), display.getHeight());
			}
		}
	}

	public WebView getWebView() {
		return webView;
	}


	public PGHScrollView gethScrollView() {
		return hScrollView;
	}

	public void sethScrollView(PGHScrollView hScrollView) {
		this.hScrollView = hScrollView;
	}

}
