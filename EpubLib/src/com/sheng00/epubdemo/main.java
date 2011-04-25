/**
 * 
 */
package com.sheng00.epubdemo;

import java.util.zip.ZipInputStream;

import com.sheng00.epublib.R;
import com.sheng00.epublib.Controllers.BookViewController;
import com.sheng00.epublib.Managers.PrefsManager;
import com.sheng00.epublib.Managers.XmlManager;
import com.sheng00.epublib.Managers.ZipManager;
import com.sheng00.epublib.Models.Book;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @author ShengQing on 2011-4-24
 *
 */
public class main extends Activity {

	/** Called when the activity is first created. */
	private Book book;
	private BookViewController mBookViewController;
	private PrefsManager prefsManager;
	private XmlManager xmlManager;
	private ZipManager zipManager;
	private RelativeLayout layout;
	private ImageView splashView;
	private static Context CONTEXT;

	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		// keep screen on
        CONTEXT = this;
        //add screen on flag
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		splashView = new ImageView(this);
		splashView.setImageResource(R.drawable.cover);
		setContentView(splashView);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				initContents();
			}

		}, 100);
		
		
    }
    
    private void initContents() {
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
		new AlertDialog.Builder(this)

		// .setIcon(R.drawable.services)

				.setTitle(R.string.prompt)

				.setMessage(R.string.quit_desc)

				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}

						})

				.setPositiveButton(R.string.confirm,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int whichButton) {

								finish();

							}

						}).show();
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
