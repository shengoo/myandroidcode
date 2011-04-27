package com.sheng00.epublib.Controllers;

import java.util.Random;

import com.sheng00.epubdemo.main;
import com.sheng00.epublib.R;
import com.sheng00.epublib.Listeners.ControllBarClickListener;
import com.sheng00.epublib.Managers.PrefsManager;
import com.sheng00.epublib.Models.Book;
import com.sheng00.epublib.Views.ChapterView;
import com.sheng00.epublib.Views.ContentView;

import net.youmi.android.AdManager;
import net.youmi.android.AdView;
import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;



public class BookViewController {
	static{   
		 //第一个参数为您的应用发布Id
		 //第二个参数为您的应用密码
		 //第三个参数是请求广告的间隔，有效的设置值为30至200，单位为秒
		 //第四个参数是设置测试模式，设置为true时，可以获取测试广告，正式发布请设置此参数为false
		 //第五个参数是供开发者设置的应用版本标识，开发者设置此参数后，可以通过有米广告网站的开发者管理页面看到应用的统计情况。
		 AdManager.init("8f2b780171bf1d4b", "5914f54783cba327", 30, true,"1.0"); 
		}
	private Context mContext;
	private RelativeLayout layout;
	private int currentPageNo;
	private Display display;
	private Book book;
	private int allPageNo;
	private ContentView content;
	private ChapterView chapter;
	private ControllBarClickListener controllBarClickListener;
	private PrefsManager prefsManager;
	
	private int genID = new Random().nextInt(10000);
	private TranslateAnimation chapterLeftOut;
	private TranslateAnimation chapterLeftIn;
	private AdView adView;


	public BookViewController(Context context,Book boo) {
		mContext = context;
		layout = new RelativeLayout(mContext);
		book = boo;
//		allPageNo = book.getNavPoints().size();
		allPageNo = book.getFlatNavPoints().size();
		display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
		prefsManager = new PrefsManager(mContext);
		/**
		 * chapter view
		 */
		chapter = new ChapterView(mContext,book,this);
		
		/*
		 *content view 
		 */
		content = new ContentView(mContext);
        controllBarClickListener = new ControllBarClickListener(mContext, this);
        content.setControllButtonsClickListener(controllBarClickListener);
        
        adView = new AdView(mContext);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        adView.setId(genID);
        adView.setVisibility(View.INVISIBLE);//show ad after 60 seconds
        adView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				adView.setVisibility(View.VISIBLE);
			}
		}, 1000*60);
        
        LayoutParams mainParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mainParams.addRule(RelativeLayout.BELOW,adView.getId());
        
        layout.addView(adView,params);
        layout.addView(content,mainParams);
        layout.addView(chapter,mainParams);
        
        chapterLeftOut = new TranslateAnimation(0, -display.getWidth(), 0, 0);
        chapterLeftOut.setDuration(300);
        chapterLeftOut.setAnimationListener(new AnimationListener() {
			
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
				chapter.setVisibility(View.INVISIBLE);
			}
		});
        chapterLeftIn = new TranslateAnimation(-display.getWidth(), 0,0,0);
        chapterLeftIn.setDuration(300);
        
	}
	
	/**
	 * Show chapter view
	 * @param scrollto if scroll in
	 */
	public void showChapters(boolean scrollto) {
		savehistory();
		chapter.setVisibility(View.VISIBLE);
//		content.setVisibility(View.);
		if(scrollto){
			showChapterAnimation();
		}else {
		}
	}
	
	public void showNext() {
		if (currentPageNo == allPageNo) {
			Toast.makeText(mContext, R.string.last_chapter, Toast.LENGTH_SHORT).show();
			return;
		}
		currentPageNo++;
		String path = book.getFlatNavPoints().get(currentPageNo - 1).getContent().getPath();
		content.loadContent(path, 0);
	}
	
	public void showPre() {
		if (currentPageNo == 1) {
			Toast.makeText(mContext, R.string.first_chapter, Toast.LENGTH_SHORT).show();
			return;
		}
		currentPageNo--;
		String path = book.getFlatNavPoints().get(currentPageNo - 1).getContent().getPath();
		content.loadContent(path, 0);
	}
	
	public void showLastRead() {
		int num = prefsManager.getLastnum();
		float per = prefsManager.getLastPer();
		currentPageNo = num;
		String path = book.getFlatNavPoints().get(currentPageNo - 1).getContent().getPath();
		content.loadContent(path, per);
		chapter.startAnimation(chapterLeftOut);
	}
	
	public void showPage(int number) {
		currentPageNo = number;
		String path = book.getFlatNavPoints().get(currentPageNo - 1).getContent().getPath();
		content.loadContent(path, 0);
		chapter.startAnimation(chapterLeftOut);
	}
	
	public void goBack() {
		if (chapter.getVisibility() == View.INVISIBLE) {
			chapter.setVisibility(View.VISIBLE);
			chapter.startAnimation(chapterLeftIn);
			savehistory();
		}else {
			((main)mContext).exitApp();
		}
	}

	private void savehistory(){
		float per = content.getScrollPer();
		prefsManager.savehistory(currentPageNo, per);
	}

	private void showChapterAnimation() {
		chapter.startAnimation(chapterLeftIn);
	}









	public RelativeLayout getLayout() {
		return layout;
	}









	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
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


}
