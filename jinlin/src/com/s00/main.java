package com.s00;

import java.util.zip.ZipInputStream;

import com.s00.Controllers.*;
import com.s00.Listeners.*;
import com.s00.Managers.*;
import com.s00.Models.*;
import com.s00.Views.*;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

public class main extends Activity {
    /** Called when the activity is first created. */
	private Book book;
	private PGBookViewController mBookViewController;
	private PrefsManager prefsManager;
	private XmlManager xmlManager;
	private ZipManager zipManager;
	private RelativeLayout layout;
	private static Context CONTEXT;

	private PGTopBarController topBarController;
	private TopBar topBar;
	private AlphaAnimation fadeOut;
	private AlphaAnimation fadeIn;
	private boolean BarsShows = false;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		// keep screen on
        CONTEXT = this;
        //add screen on flag
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		fadeOut = new AlphaAnimation(1.0f, 0.1f);
		fadeOut.setDuration(300);
		fadeIn = new AlphaAnimation(0.1f, 1.0f);
		fadeIn.setDuration(300);
		
		prefsManager = new PrefsManager(this);
		String bookroot=this.getFilesDir().getAbsolutePath() + "/books/uuid/";
		prefsManager.prefsSetString(this.getString(R.string.book_root_path), bookroot);
        xmlManager = new XmlManager(this);
        zipManager = new ZipManager(this);
        ZipInputStream zis = new ZipInputStream(getResources().openRawResource(R.raw.book));
        zipManager.extractZipFile(zis, bookroot);
        xmlManager.parseXml();
        book = xmlManager.getBook();
        mBookViewController = new PGBookViewController(this,book);

		layout = mBookViewController.getLayout();
		
		topBarController = new PGTopBarController(this);
		topBar = topBarController.getBar();
		LayoutParams topLayoutParams = new LayoutParams(
				LayoutParams.FILL_PARENT, 90);// 90 is the height of topbar
		topBar.setLayoutParams(topLayoutParams);
		topBar.setHomeButtonOnClickListener(new HomeButtonListener(
				mBookViewController));
		layout.addView(topBar);
		topBar.setVisibility(View.INVISIBLE);
		
		setContentView(layout);
		mBookViewController.showChapters(false);
    }
    
    public void handleChapterClick(NavPoint np) {
		int index = book.getFlatNavPoints().indexOf(np);
		mBookViewController.showPage(index);
	}
    
    
    public void hideBars() {
		if (BarsShows) {
			topBar.startAnimation(fadeOut);
//			bottomBar.startAnimation(fadeOut);
			topBar.setVisibility(View.INVISIBLE);
//			bottomBar.setVisibility(View.INVISIBLE);
			BarsShows = false;
		}
	}

	public void showBars() {
		if (!BarsShows) {
			topBar.setVisibility(View.VISIBLE);
//			bottomBar.setVisibility(View.VISIBLE);
			topBar.startAnimation(fadeIn);
//			bottomBar.startAnimation(fadeIn);
			BarsShows = true;
		}
	}
    
    
    @Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

		}
		mBookViewController.showCurrentPage();
	}


	
	public void exitApp() {
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// stop audio when press return key

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			mBookViewController.goBack();
			return true;

		} else {

			return super.onKeyDown(keyCode, event);

		}

	}
	
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		int avx = (v.getWidth()/2) / 2;
		int avy = (v.getHeight() * 2/3) / 2;
		// if click in available area , bars will show or hide
		if (event.getX() < v.getWidth() - avx && event.getX() > avx
				&& event.getY() > avy && event.getY() < v.getHeight() - avy) {
			if (BarsShows) {
				hideBars();
			} else {
				showBars();
			}
		}
		return true;
	}

	public static Context getCONTEXT() {
		return CONTEXT;
	}

	public PGBookViewController getmBookViewController() {
		return mBookViewController;
	}
	
		
}