package cn.qing.HelloAndroid;

import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;

public class TopBar extends RelativeLayout implements OnClickListener {

	Context mContext;
	Display display;
	
	ImageButton homeButton;
	ImageButton soundButton;
	boolean soundOn = true;
	
	
	public TopBar(Context context) {
		super(context);
		mContext = context;
//		setBackgroundColor(Color.TRANSPARENT);
		setBackgroundResource(R.drawable.top_bg);
		
		homeButton = new ImageButton(mContext);
		homeButton.setBackgroundColor(Color.TRANSPARENT);
		homeButton.setImageResource(R.drawable.menue);
		homeButton.setScaleType(ScaleType.FIT_CENTER);
		RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		homeButton.setLayoutParams(leftParams);
		addView(homeButton,leftParams);
		
		
		soundButton = new ImageButton(mContext);
		soundButton.setBackgroundColor(Color.TRANSPARENT);
		soundButton.setImageResource(R.drawable.sound);
		soundButton.setScaleType(ScaleType.FIT_END);
		RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		soundButton.setLayoutParams(rightParams);
		soundButton.setOnClickListener(this);
		addView(soundButton,rightParams);
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(soundOn){
			soundButton.setImageResource(R.drawable.sound_off);
		}
		else {
			soundButton.setImageResource(R.drawable.sound);
		}
		soundOn = !soundOn;
	}

}
