package cn.qing.DisplayHtml;

import android.content.Context;
import android.webkit.WebView;

public class AWebView extends WebView {

	private int loffset;

	public AWebView(Context context,int offset) {
		super(context);
		loffset = offset;
	}
	
	@Override
	protected void onLayout (boolean changed, int left, int top, int right, int bottom){
		this.offsetLeftAndRight(loffset);
	}

}