/**
 * 
 */
package com.sheng00.epublib.Views;

import java.util.Random;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * @author ShengQing on 2011-4-23
 * 
 */
public class ContentView extends RelativeLayout {

	private ControllBar controllBar;
	private ProgressBar progressBar;
	private WebView webView;
	private Context mContext;

	private int genID = new Random().nextInt(10000);
	
	private float scrollPercent;

	/**
	 * @param context
	 */
	public ContentView(Context context) {
		super(context);
		mContext = context;
		// TODO Auto-generated constructor stub
		controllBar = new ControllBar(context);
		controllBar.setId(genID);
		addView(controllBar, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);

		webView = new WebView(context);
		webView.setWebViewClient(new MyWebChromeClient());
		webView.setInitialScale(100);
		webView.getSettings().setDefaultFontSize(26);
		LayoutParams webParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.FILL_PARENT);
		webParams.addRule(RelativeLayout.BELOW, controllBar.getId());
		webView.setLayoutParams(webParams);
		addView(webView);
		webView.setVisibility(View.INVISIBLE);

		progressBar = new ProgressBar(mContext, null,
				R.attr.progressBarStyleLarge);
		LayoutParams progressBarParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		progressBarParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		progressBar.setLayoutParams(progressBarParams);
		addView(progressBar);

	}

	public void loadContent(String path,float pers) {
		if (webView.getVisibility() != INVISIBLE) {
			webView.setVisibility(INVISIBLE);
		}
		if (progressBar.getVisibility() != VISIBLE) {
			progressBar.setVisibility(VISIBLE);
		}
		webView.loadUrl("file://" +path);//"file://" + 
		scrollPercent = pers;
	}
	
	public float getScrollPer() {
		float result = 0;
		
		result = (float)webView.getScrollY()/(float)webView.getContentHeight();
		
		return result;
	}

	class MyWebChromeClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if (scrollPercent != 0) {
				webView.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						progressBar.setVisibility(INVISIBLE);
						webView.setVisibility(VISIBLE);
						// TODO Auto-generated method stub
						int max = webView.getContentHeight();
						int pos = (int) (max * scrollPercent);
						webView.scrollTo(0, pos);
//						Toast.makeText(mContext, "pers:" + scrollPercent + " " + pos, Toast.LENGTH_SHORT).show();
					}
				}, 500);
			}else {
				progressBar.setVisibility(INVISIBLE);
				webView.setVisibility(VISIBLE);
				webView.scrollTo(0, 0);
			}
		}
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ContentView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public void setControllButtonsClickListener(OnClickListener l) {
		controllBar.setButtonsClickListener(l);
	}

}
