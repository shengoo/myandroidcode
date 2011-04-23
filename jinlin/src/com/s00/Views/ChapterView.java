/**
 * 
 */
package com.s00.Views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.s00.Controllers.BookViewController;
import com.s00.Managers.PrefsManager;
import com.s00.Models.Book;
import com.s00.Models.NavPoint;

/**
 * @author Qing
 * @date 2011-4-22
 * 
 */
public class ChapterView extends ScrollView implements OnClickListener {

	private Context mContext;

	private BookViewController mBookViewController;
	private Book book;
	private PrefsManager prefsManager;


	private Button continuBtn;

	private LinearLayout layout;

	private Display display;
	private boolean hasHistory = false;

	/**
	 * @param context
	 */
	public ChapterView(Context context, Book bok, BookViewController controller) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		this.book = bok;
		mBookViewController = controller;
		display = ((Activity) context).getWindowManager().getDefaultDisplay();
		setBackgroundColor(Color.WHITE);

		prefsManager = new PrefsManager(mContext);
		hasHistory = prefsManager.hashistory();

		int btnWidth = display.getWidth() / 2;

		layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);

		continuBtn = new Button(context);
		continuBtn.setId(0);
		continuBtn.setText("¼ÌÐøÉÏ´ÎÔÄ¶Á");
		continuBtn.setOnClickListener(this);
		continuBtn.setEnabled(hasHistory);
		layout.addView(continuBtn,LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);

		for (int i = 0; i < book.getFlatNavPoints().size(); i++) {
			NavPoint np = book.getFlatNavPoints().get(i);
			Button b = new Button(context);
			b.setId(i + 1);
			b.setText(np.getLabel());
			b.setWidth(btnWidth);
			b.setOnClickListener(this);
			layout.addView(b,LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		}

		addView(layout);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ChapterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ChapterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Button button = (Button) arg0;
		int id = button.getId();
		if (id == 0) {
			mBookViewController.showLastRead();
		}
		else {
			continuBtn.setEnabled(true);
			mBookViewController.showPage(id);
		}
	}

}
