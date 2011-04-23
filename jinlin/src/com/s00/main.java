package com.s00;

import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.s00.Controllers.BookViewController;
import com.s00.Managers.PrefsManager;
import com.s00.Managers.XmlManager;
import com.s00.Managers.ZipManager;
import com.s00.Models.Book;

public class main extends Activity {
    /** Called when the activity is first created. */
	private Book book;
	private BookViewController mBookViewController;
	private PrefsManager prefsManager;
	private XmlManager xmlManager;
	private ZipManager zipManager;
	private RelativeLayout layout;
	private static Context CONTEXT;

	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		// keep screen on
        CONTEXT = this;
        //add screen on flag
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//		
		prefsManager = new PrefsManager(this);
		String bookroot=this.getFilesDir().getAbsolutePath() + "/books/uuid/";
		prefsManager.prefsSetString(this.getString(R.string.book_root_path), bookroot);
        xmlManager = new XmlManager(this);
        zipManager = new ZipManager(this);
        ZipInputStream zis = new ZipInputStream(getResources().openRawResource(R.raw.book));
        zipManager.extractZipFile(zis, bookroot);
        xmlManager.parseXml();
        book = xmlManager.getBook();
        mBookViewController = new BookViewController(this,book);
//
		layout = mBookViewController.getLayout();
		
		setContentView(layout);
    }
    
    
    

    
    
    @Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

		}
//		mBookViewController.showCurrentPage();
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
	

	public static Context getCONTEXT() {
		return CONTEXT;
	}

	public BookViewController getmBookViewController() {
		return mBookViewController;
	}
	
		
}