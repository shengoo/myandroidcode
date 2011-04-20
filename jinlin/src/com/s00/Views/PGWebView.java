package com.s00.Views;

import android.content.Context;
import android.webkit.WebView;

public class PGWebView extends WebView{

	private int pageIndex;

	public PGWebView(Context context) {
		super(context);
	}
	
	
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}
