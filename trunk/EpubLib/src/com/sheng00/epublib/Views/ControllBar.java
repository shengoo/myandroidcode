/**
 * 
 */
package com.sheng00.epublib.Views;


import com.sheng00.epublib.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;


/**
 * @author ShengQing on 2011-4-23
 * 
 */
public class ControllBar extends TableLayout {

	private static final String tag = "ControllBar";
	private Button preButton;
	private TableRow row;
	private Button chapterButton;
	private Button nextButton;
	private Display display;

	/**
	 * @param context
	 */
	public ControllBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		display = ((Activity) context).getWindowManager().getDefaultDisplay();
		int btnWidth = display.getWidth() / 3;
		Log.i(tag, "width:" + btnWidth);
		row = new TableRow(context);
		preButton = new Button(context);
		preButton.setWidth(btnWidth);
		preButton.setText(R.string.pre_button_text);
		row.addView(preButton);

		chapterButton = new Button(context);
		chapterButton.setWidth(btnWidth);
		chapterButton.setText(R.string.chapter_button_text);
		row.addView(chapterButton);

		nextButton = new Button(context);
		nextButton.setWidth(btnWidth);
		nextButton.setText(R.string.next_button_text);
		row.addView(nextButton);

		addView(row, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ControllBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setButtonsClickListener(OnClickListener l) {
		preButton.setOnClickListener(l);
		chapterButton.setOnClickListener(l);
		nextButton.setOnClickListener(l);
	}

}
