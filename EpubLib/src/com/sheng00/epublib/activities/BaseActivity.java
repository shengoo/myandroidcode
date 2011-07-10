/**
 * 
 */
package com.sheng00.epublib.activities;

import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.guohead.sdk.GuoheAdManager;
import com.sheng00.epublib.R;
import com.sheng00.epublib.Controllers.BookViewController;
import com.sheng00.epublib.Managers.PrefsManager;
import com.sheng00.epublib.Managers.XmlManager;
import com.sheng00.epublib.Managers.ZipManager;
import com.sheng00.epublib.Models.Book;

/**
 * @author ShengQing on 2011-7-4
 *
 */
public class BaseActivity extends Activity {
	/** Called when the activity is first created. */
	private Book book;
	private BookViewController mBookViewController;
	private PrefsManager prefsManager;
	private XmlManager xmlManager;
	private ZipManager zipManager;
	private RelativeLayout layout;
	private ImageView splashView;
//	private Menu mMenu;
	private static Context CONTEXT;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// keep screen on
		CONTEXT = this;
		// add screen on flag
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
		String bookroot = this.getFilesDir().getAbsolutePath() + "/contents/";
		System.out.println(this.getString(R.string.book_root_path));
		prefsManager.prefsSetString(this.getString(R.string.book_root_path),
				bookroot);
		xmlManager = new XmlManager(this);
		zipManager = new ZipManager(this);
		ZipInputStream zis = new ZipInputStream(getResources().openRawResource(
				R.raw.book));
		zipManager.extractZipFile(zis, bookroot);
		xmlManager.parseXml();
		book = xmlManager.getBook();
		mBookViewController = new BookViewController(this, book);
		layout = mBookViewController.getLayout();

		setContentView(layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Hold on to this
//		mMenu = menu;

		// Inflate the currently selected menu XML resource.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		return true;
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		if (mBookViewController.isShowContent()) {
			menu.findItem(R.id.zoomin).setEnabled(true);
			menu.findItem(R.id.zoomout).setEnabled(true);
		} else {
			menu.findItem(R.id.zoomin).setEnabled(false);
			menu.findItem(R.id.zoomout).setEnabled(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.exitapp:
			exitApp();
			break;
		case R.id.zoomin:
			mBookViewController.zoomInFont();
			break;
		case R.id.zoomout:
			mBookViewController.zoomOutFont();
			break;
		// Generic catch all for all the other menu resources
		default:
			// Don't toast text when a submenu is clicked
			// if (!item.hasSubMenu()) {
			// Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
			// return true;
			// }
			break;
		}

		return false;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

		}
		// mBookViewController.showCurrentPage();
	}

	public void exitApp() {
		new AlertDialog.Builder(this)

		.setIcon(android.R.drawable.ic_dialog_alert).setTitle(
				android.R.string.dialog_alert_title).setMessage(
				R.string.quit_desc).setNegativeButton(android.R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).setPositiveButton(android.R.string.yes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						GuoheAdManager.finish(CONTEXT);
						finish();
						System.exit(0);
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
