package com.s00.Controllers;

import java.util.ArrayList;
import java.util.List;

import net.youmi.android.AdManager;
import net.youmi.android.AdView;

import libs.InMemoryTreeStateManager;
import libs.TreeBuilder;
import libs.TreeStateManager;
import libs.TreeViewList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.s00.main;
import com.s00.Listeners.BookViewTouchListener;
import com.s00.Models.Book;
import com.s00.Models.NavPoint;
import com.s00.TreeViews.SimpleStandardAdapter;
import com.s00.Views.PGHScrollView;
import com.s00.Views.PGScrollLayout;
import com.s00.Views.PGWebView;
import com.s00.Views.PGWebViewLayout;


public class PGBookViewController {
	static{   
		 //第一个参数为您的应用发布Id
		 //第二个参数为您的应用密码
		 //第三个参数是请求广告的间隔，有效的设置值为30至200，单位为秒
		 //第四个参数是设置测试模式，设置为true时，可以获取测试广告，正式发布请设置此参数为false
		 //第五个参数是供开发者设置的应用版本标识，开发者设置此参数后，可以通过有米广告网站的开发者管理页面看到应用的统计情况。
		 AdManager.init("027c3f3aa8b76173", "9f391d78a17190f0", 30, true,"1.0"); 
		}
	private Context mContext;
	private RelativeLayout layout;
	private int currentPageNo;
	private PGHScrollView hscroll;
	private Display display;
	private PGScrollLayout linearLayout;
	private BookViewTouchListener touchListener;
	private TranslateAnimation translateAnimation;
	private View lastView;
	private Book book;
	private int allPageNo;
	
	//tree view items
    private final TreeStateManager<NavPoint> manager = new InMemoryTreeStateManager<NavPoint>();
    private final TreeBuilder<NavPoint> treeBuilder = new TreeBuilder<NavPoint>(manager);
	private TreeViewList treeView;
	private SimpleStandardAdapter simpleAdapter;

	public PGBookViewController(Context context,Book boo) {
		mContext = context;
		layout = new RelativeLayout(mContext);
		linearLayout = new PGScrollLayout(mContext);
		book = boo;
//		allPageNo = book.getNavPoints().size();
		allPageNo = book.getFlatNavPoints().size();
		display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
		touchListener = new BookViewTouchListener(mContext, this);
		hscroll = new PGHScrollView(mContext, this);
		hscroll.setHorizontalScrollBarEnabled(false);
		hscroll.setHorizontalFadingEdgeEnabled(false);
		hscroll.setBackgroundColor(Color.BLACK);
		hscroll.setOnTouchListener(touchListener);
		hscroll.setBookViewController(this);
		linearLayout.setMinimumWidth(display.getWidth()
				* allPageNo + 300);
		hscroll.addView(linearLayout);
		currentPageNo = -1;
		visiblePages = new ArrayList<View>();
		recycledPage = new ArrayList<View>();
		
		/**
		 * tree view init below
		 */
		treeView = new TreeViewList(mContext);
        for (int i = 0; i < book.getNavPoints().size(); i++) {
        	NavPoint np = book.getNavPoints().get(i);
            treeBuilder.sequentiallyAddNextNode(np, 0);
            if (np.getChildren().size() > 0) {
				addChildren(np);
			}
        }
        simpleAdapter = new SimpleStandardAdapter((main) mContext, manager, 4);
        treeView.setAdapter(simpleAdapter);
        manager.collapseChildren(null);//collapse all
        treeView.setPadding(0, 40, 0, 0);
        
        layout.addView(hscroll);
        layout.addView(treeView);
        
        AdView adView = new AdView(mContext);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        layout.addView(adView,params);
	}
	
	private void addChildren(NavPoint n){
    	for(int i = 0; i < n.getChildren().size(); i++){
    		NavPoint child = n.getChildren().get(i);
    		treeBuilder.addRelation(n, child);
    		if(child.getChildren().size()>0)
    			addChildren(child);
    	}
    }
	
	public void showChapters(boolean scrollto) {
		((main)main.getCONTEXT()).hideBars();
		treeView.setVisibility(View.VISIBLE);
		hscroll.setVisibility(View.GONE);
		if(scrollto){
			lastView = linearLayout
			.getChildAt(linearLayout.getChildCount() - 1);
			linearLayout.removeAllViews();
			showChapterAnimation();
		}else {
		}
	}
	
	public void showPage(int number) {
		treeView.setVisibility(View.GONE);
		hscroll.setVisibility(View.VISIBLE);
		currentPageNo = number;
		showCurrentPage();
	}
	
	public void goBack() {
		if (treeView.getVisibility() == View.GONE) {
			hscroll.setVisibility(View.GONE);
			treeView.setVisibility(View.VISIBLE);
			currentPageNo = -1;
		}else {
			((main)mContext).exitApp();
		}
	}



	private void showChapterAnimation() {
		layout.addView(lastView);
		TranslateAnimation rightOut = new TranslateAnimation(0, display
				.getWidth(), 0, 0);
		TranslateAnimation leftIn = new TranslateAnimation(-display.getWidth(),
				0, 0, 0);
		leftIn.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
//				layout.removeViewInLayout(lastView);
				lastView.setVisibility(View.GONE);
			}
		});
		rightOut.setDuration(1000);
		leftIn.setDuration(1000);
		lastView.startAnimation(rightOut);
		treeView.startAnimation(leftIn);
	}

	public void showCurrentPage() {
		visiblePages.clear();
		linearLayout.removeAllViews();
		refreshDisplay();
		Log.i("Show current page", "display width:"
				+ Integer.toString(display.getWidth()));
		linearLayout.setMinimumWidth(display.getWidth()
				* (allPageNo + 1) + 300);
		showPageforRect(currentPageNo * display.getWidth() + 150, 0, display
				.getWidth(), 0);
		final int scrollPosition = currentPageNo * display.getWidth() + 150;
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				display.getWidth(), display.getHeight());
		hscroll.setLayoutParams(params);
		hscroll.setNeedScorll(true);
//		hscroll.setPlaySoundFlag(true);// not let the sound play again.
		hscroll.setScrollX(scrollPosition);

		Log.i("scroll layout", "width:"
				+ Integer.toString(linearLayout.getWidth()));
		Log.i("showCurrentPage", "scroll to "
				+ Integer.toString(scrollPosition));
	}

//	public void showNextPage() {
//		if (currentPageNo == allPageNo) {
//			showFrontPage(true);
//			return;
//		}
//		showPageforRect(currentPageNo * display.getWidth() + 151, 0, display
//				.getWidth(), 0);
//		currentPageNo++;
//		scrollSlowlyTo(HorizontalScrollView.FOCUS_RIGHT);
//	}

	public void scrollSlowlyTo(int direction) {
		if (direction == HorizontalScrollView.FOCUS_LEFT) {
			int startX = -(hscroll.getScrollX() % display.getWidth());
			int endX = 0;
			translateAnimation = new TranslateAnimation(startX, endX, 0, 0);
			translateAnimation
					.setDuration((3000 * (hscroll.getScrollX() % display
							.getWidth()))
							/ display.getWidth());
			translateAnimation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {

				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					hscroll.scrollTo(display.getWidth()
							* (hscroll.getScrollX() / display.getWidth())
							+ display.getWidth() / 2, 0);
				}
			});
			linearLayout.startAnimation(translateAnimation);
			if (currentPageNo != 0) {
			} else {
			}
		} else {
			int startX = display.getWidth();
			int endX = 0;
			translateAnimation = new TranslateAnimation(startX, endX, 0, 0);
			translateAnimation.setDuration(1000);
			translateAnimation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {

				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					hscroll.scrollTo(
							(currentPageNo) * display.getWidth() + 150, 0);
				}
			});
			linearLayout.startAnimation(translateAnimation);
			hscroll.scrollTo((currentPageNo) * display.getWidth() + 149, 0);
		}
	}

	public View getViewByPageNo(int pageno) {
//		PGWebView webView = createWebView();
//		webView.loadUrl("file://" + book.getNavPoints().get(pageno).getContent().getPath());
		PGWebViewLayout webView = new PGWebViewLayout(mContext, book.getFlatNavPoints().get(pageno).getContent().getPath());
		return webView;
	}





	public RelativeLayout getLayout() {
		return layout;
	}





	public PGWebView createWebView() {
		PGWebView webView = new PGWebView(mContext);
		webView.setClickable(false);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setBackgroundColor(Color.WHITE);
		return webView;
	}

	public RelativeLayout createWebViewParentLayout(WebView wView) {
		RelativeLayout relativeLayout = new RelativeLayout(mContext);
		relativeLayout.setMinimumHeight(display.getHeight());
		relativeLayout.setMinimumWidth(display.getWidth());
		relativeLayout.addView(wView);
		return relativeLayout;
	}



	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	private List<View> visiblePages;
	private List<View> recycledPage;

	private View isPageDisplayForIndex(int index) {
		for (View view : visiblePages) {
			if (view instanceof PGWebView){
				if (((PGWebView) view).getPageIndex() == index) {
					return view;
				}
			}
			if(view instanceof PGWebViewLayout){
				if (((PGWebViewLayout) view).getPageIndex() == index) {
					return view;
				}
			}
		}
		
		return null;
	}

	private void configPageforIndex(View view, int index) {
//		if (view instanceof PGWebView) {
//			((PGWebView) view).setPageIndex(index);
//		}
		if(view instanceof PGWebViewLayout){
			((PGWebViewLayout) view).setPageIndex(index);
		}
	}

	public void showPageforRect(int x, int y, int width, int height) {
		Log.i("show page rect", "x:" + x + "width" + width);
		int firstNeedIndex = (int) Math.floor((double) (x - 150)
				/ (double) display.getWidth());
		int lastNeedIndex = (int) Math.ceil((double) (x - 150)
				/ (double) display.getWidth());

		firstNeedIndex = Math.max(firstNeedIndex, 0);
		lastNeedIndex = Math.min(lastNeedIndex, allPageNo);
		Log.i("show page rect", "first:" + Integer.toString(firstNeedIndex)
				+ "\tlast:" + Integer.toString(lastNeedIndex));

		// remove unneed pages
		for (View view : visiblePages) {
			if (view instanceof PGWebView) {
				if (((PGWebView) view).getPageIndex() < firstNeedIndex
						|| ((PGWebView) view).getPageIndex() > lastNeedIndex) {

					recycledPage.add(view);
					linearLayout.removeView(view);
				}
			}
			if (view instanceof PGWebViewLayout) {
				if (((PGWebViewLayout) view).getPageIndex() < firstNeedIndex
						|| ((PGWebViewLayout) view).getPageIndex() > lastNeedIndex) {

					recycledPage.add(view);
					linearLayout.removeView(view);
				}
			}
		}

		for (View recycledView : recycledPage) {
			visiblePages.remove(recycledView);
		}
		recycledPage.clear();

		// add new need pages
		for (int i = firstNeedIndex; i <= lastNeedIndex; i++) {
			View view = this.isPageDisplayForIndex(i);
			if (view == null) {
				view = getViewByPageNo(i);
				this.configPageforIndex(view, i);
				this.visiblePages.add(view);
				linearLayout.addView(view);
			}

		}
		Log.i("visible page no", Integer.toString(visiblePages.size()));

	}

	private void refreshDisplay() {
		display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
	}


	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}


	public int getAllPageNo() {
		return allPageNo;
	}

	public PGHScrollView getHscroll() {
		return hscroll;
	}

	public BookViewTouchListener getTouchListener() {
		return touchListener;
	}

}
