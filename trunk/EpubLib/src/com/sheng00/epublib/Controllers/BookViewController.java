package com.sheng00.epublib.Controllers;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import com.guohead.sdk.GuoheAdLayout;
import com.guohead.sdk.GuoheAdManager;
import com.guohead.sdk.GuoheAdStateListener;
import com.sheng00.epubdemo.main;
import com.sheng00.epublib.R;
import com.sheng00.epublib.Config.GlobalConfig;
import com.sheng00.epublib.Listeners.ControllBarClickListener;
import com.sheng00.epublib.Managers.PrefsManager;
import com.sheng00.epublib.Models.Book;
import com.sheng00.epublib.Views.ChapterView;
import com.sheng00.epublib.Views.ContentView;



public class BookViewController {
	
	

	
	
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
//	private AdView adView;


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
        
        
        /**
         * ad
         */
        GuoheAdManager.init(mContext.getString(R.string.adkey));
		GuoheAdLayout adLayout = new GuoheAdLayout((Activity) mContext);
        adLayout.setListener(new GuoheAdStateListener() {
	        @Override
	        public void onFail() {
	        }
	        @Override
	        public void onClick() {
	        //adLayout.setVisibility(View.GONE);
	        }
	        @Override
	        public void onReceiveAd(){
	        }
	        @Override
	        public void onRefreshAd(){
	        }
        });
        
//        adView = new AdView(mContext);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//        adView.setId(genID);
//        adView.setVisibility(View.INVISIBLE);//show ad after 60 seconds
//        adView.postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				adView.setVisibility(View.VISIBLE);
//			}
//		}, 1000*60);
        
        adLayout.setId(genID);
        
        LayoutParams mainParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mainParams.addRule(RelativeLayout.BELOW,adLayout.getId());
        
        layout.addView(adLayout,params);
        layout.addView(content,mainParams);
        layout.addView(chapter,mainParams);
        
        chapterLeftOut = new TranslateAnimation(0, -display.getWidth(), 0, 0);
        chapterLeftOut.setDuration(GlobalConfig.ANIMATION_DURATION);
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
//				chapter.setVisibility(View.INVISIBLE);
				layout.removeView(chapter);
			}
		});
        chapterLeftIn = new TranslateAnimation(-display.getWidth(), 0,0,0);
        chapterLeftIn.setDuration(GlobalConfig.ANIMATION_DURATION);
        
	}
	
	/**
	 * Show chapter view
	 * @param scrollto if scroll in
	 */
	public void showChapters(boolean scrollto) {
		savehistory();
		layout.addView(chapter);
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
		if (!chapter.isShown()) {
			layout.addView(chapter);
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

	public boolean isShowContent() {
		if (layout.getChildCount() > 2) {
			return false;
		}
		return true;
	}

	public void zoomInFont() {
		content.zoomInFont();
	}
	
	public void zoomOutFont() {
		content.zoomOutFont();
	}

}
