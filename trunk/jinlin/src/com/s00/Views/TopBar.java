package com.s00.Views;

import com.s00.R;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;


public class TopBar  extends RelativeLayout  {

	private Context mContext;
	
	private ImageButton homeButton;

	
	
	public TopBar(Context context) {
		super(context);
		mContext = context;
//		setBackgroundColor(Color.TRANSPARENT);
		setBackgroundResource(R.drawable.top_bg);
		
		homeButton = new ImageButton(mContext);
		homeButton.setId(123);//if not set id, info button cannot find homebutton
		homeButton.setBackgroundColor(Color.TRANSPARENT);
		homeButton.setImageResource(R.drawable.menue);
		homeButton.setScaleType(ScaleType.FIT_CENTER);
		LayoutParams leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
		homeButton.setLayoutParams(leftParams);
		addView(homeButton);
		
		
	}

	public void setHomeButtonOnClickListener(OnClickListener listener) {
		homeButton.setOnClickListener(listener);
	}
	
	
	
}
